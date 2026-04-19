package com.example.bh4haptool.update

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

@Singleton
class ReleaseUpdateRepository @Inject constructor() {

    suspend fun checkForUpdate(currentVersionName: String): UpdateCheckResult {
        return withContext(Dispatchers.IO) {
            try {
                val release = fetchLatestRelease()
                    ?: return@withContext UpdateCheckResult.Error("暂时无法解析最新版本信息")

                if (ReleaseVersionComparator.isNewer(release.tagName, currentVersionName)) {
                    UpdateCheckResult.HasUpdate(release)
                } else {
                    UpdateCheckResult.UpToDate(release)
                }
            } catch (_: IOException) {
                UpdateCheckResult.Error("检查更新失败，请确认网络连接后重试")
            } catch (_: Exception) {
                UpdateCheckResult.Error("检查更新失败，请稍后再试")
            }
        }
    }

    fun openReleasePage(context: Context, release: ReleaseUpdateInfo): Boolean {
        val intent = Intent(Intent.ACTION_VIEW, release.primaryDownloadUrl.toUri())
        return try {
            context.startActivity(intent)
            true
        } catch (_: ActivityNotFoundException) {
            false
        }
    }

    internal fun parseRelease(jsonText: String): ReleaseUpdateInfo? {
        val json = JSONObject(jsonText)
        val tagName = json.optString("tag_name").trim()
        val htmlUrl = json.optString("html_url").trim()
        if (tagName.isBlank() || htmlUrl.isBlank()) {
            return null
        }

        val releaseName = json.optString("name").trim().ifBlank { tagName }
        val body = json.optString("body").orEmpty()
        val publishedAt = json.optString("published_at").orEmpty()
        val apkAsset = selectApkAsset(json.optJSONArray("assets"))

        return ReleaseUpdateInfo(
            tagName = tagName,
            releaseName = releaseName,
            body = body,
            htmlUrl = htmlUrl,
            publishedAt = publishedAt,
            apkAsset = apkAsset
        )
    }

    private fun fetchLatestRelease(): ReleaseUpdateInfo? {
        val connection = (URL(LATEST_RELEASE_URL).openConnection() as HttpURLConnection).apply {
            requestMethod = "GET"
            connectTimeout = NETWORK_TIMEOUT_MS
            readTimeout = NETWORK_TIMEOUT_MS
            setRequestProperty("Accept", "application/vnd.github+json")
            setRequestProperty("X-GitHub-Api-Version", "2022-11-28")
            setRequestProperty("User-Agent", USER_AGENT)
        }

        return connection.useAndDisconnect { http ->
            val responseCode = http.responseCode
            if (responseCode !in 200..299) {
                throw IOException("Unexpected response code: $responseCode")
            }

            val body = http.inputStream.bufferedReader().use { it.readText() }
            parseRelease(body)
        }
    }

    private fun selectApkAsset(assetsArray: JSONArray?): ReleaseAssetInfo? {
        if (assetsArray == null) {
            return null
        }

        val assets = buildList {
            for (index in 0 until assetsArray.length()) {
                val item = assetsArray.optJSONObject(index) ?: continue
                val name = item.optString("name").trim()
                val downloadUrl = item.optString("browser_download_url").trim()
                if (name.isBlank() || downloadUrl.isBlank()) {
                    continue
                }

                add(
                    ReleaseAssetInfo(
                        name = name,
                        downloadUrl = downloadUrl,
                        sizeBytes = item.optLong("size")
                    )
                )
            }
        }

        return assets
            .filter { it.name.endsWith(".apk", ignoreCase = true) }
            .sortedWith(
                compareByDescending<ReleaseAssetInfo> {
                    it.name.contains("BH4HAPtool", ignoreCase = true)
                }.thenBy { it.name }
            )
            .firstOrNull()
    }

    private inline fun <T> HttpURLConnection.useAndDisconnect(
        block: (HttpURLConnection) -> T
    ): T {
        return try {
            block(this)
        } finally {
            disconnect()
        }
    }

    private companion object {
        private const val USER_AGENT = "BH4HAPtool-Android"
        private const val NETWORK_TIMEOUT_MS = 10_000
        private const val LATEST_RELEASE_URL =
            "https://api.github.com/repos/Tony9193/BH4HAPtool/releases/latest"
    }
}

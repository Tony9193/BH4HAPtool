package com.example.bh4haptool.update

data class ReleaseAssetInfo(
    val name: String,
    val downloadUrl: String,
    val sizeBytes: Long
)

data class ReleaseUpdateInfo(
    val tagName: String,
    val releaseName: String,
    val body: String,
    val htmlUrl: String,
    val publishedAt: String,
    val apkAsset: ReleaseAssetInfo?
) {
    val displayVersion: String
        get() = tagName.removePrefix("v").removePrefix("V").ifBlank { tagName }

    val primaryDownloadUrl: String
        get() = apkAsset?.downloadUrl ?: htmlUrl
}

sealed interface UpdateCheckResult {
    data class HasUpdate(
        val release: ReleaseUpdateInfo
    ) : UpdateCheckResult

    data class UpToDate(
        val release: ReleaseUpdateInfo?
    ) : UpdateCheckResult

    data class Error(
        val message: String
    ) : UpdateCheckResult
}

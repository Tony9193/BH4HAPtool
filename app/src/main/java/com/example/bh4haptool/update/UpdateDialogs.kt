package com.example.bh4haptool.update

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.bh4haptool.R

@Composable
fun UpdateAvailableDialog(
    currentVersionName: String,
    release: ReleaseUpdateInfo,
    onUpdateNow: () -> Unit,
    onIgnore: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = stringResource(R.string.update_dialog_title, release.displayVersion))
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = stringResource(
                        R.string.update_dialog_version_summary,
                        currentVersionName,
                        release.displayVersion
                    ),
                    style = MaterialTheme.typography.bodyMedium
                )
                val publishedDate = release.publishedAt.substringBefore('T')
                if (publishedDate.isNotBlank()) {
                    Text(
                        text = stringResource(R.string.update_dialog_published_at, publishedDate),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                val bodyPreview = release.body
                    .lineSequence()
                    .map(String::trim)
                    .filter(String::isNotBlank)
                    .take(8)
                    .joinToString(separator = "\n")
                if (bodyPreview.isNotBlank()) {
                    Text(
                        text = bodyPreview,
                        style = MaterialTheme.typography.bodySmall
                    )
                } else {
                    Text(
                        text = stringResource(R.string.update_dialog_no_notes),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = onUpdateNow) {
                Text(
                    text = stringResource(
                        if (release.apkAsset != null) {
                            R.string.update_dialog_download_now
                        } else {
                            R.string.update_dialog_open_release
                        }
                    )
                )
            }
        },
        dismissButton = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(onClick = onIgnore) {
                    Text(text = stringResource(R.string.update_dialog_ignore))
                }
                OutlinedButton(onClick = onDismiss) {
                    Text(text = stringResource(R.string.update_dialog_later))
                }
            }
        }
    )
}

@Composable
fun UpdateMessageDialog(
    title: String,
    message: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        text = { Text(text = message) },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text(text = stringResource(R.string.update_dialog_acknowledge))
            }
        },
        dismissButton = {}
    )
}

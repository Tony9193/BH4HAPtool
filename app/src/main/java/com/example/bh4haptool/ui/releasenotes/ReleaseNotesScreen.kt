package com.example.bh4haptool.ui.releasenotes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bh4haptool.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReleaseNotesRoute(
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val notes = remember {
        listOf(
            ReleaseNoteEntry(
                version = "v1.6.0",
                date = "2026-04-19",
                content = context.resources.openRawResource(R.raw.release_notes_v160)
                    .bufferedReader()
                    .use { it.readText() }
            ),
            ReleaseNoteEntry(
                version = "v1.5.0",
                date = "2026-04-18",
                content = context.resources.openRawResource(R.raw.release_notes_v150)
                    .bufferedReader()
                    .use { it.readText() }
            ),
            ReleaseNoteEntry(
                version = "v1.4.0",
                date = "2026-04-18",
                content = context.resources.openRawResource(R.raw.release_notes_v140)
                    .bufferedReader()
                    .use { it.readText() }
            ),
            ReleaseNoteEntry(
                version = "v1.3.0",
                date = "2026-04-15",
                content = context.resources.openRawResource(R.raw.release_notes_v130)
                    .bufferedReader()
                    .use { it.readText() }
            ),
            ReleaseNoteEntry(
                version = "v1.2.0",
                date = "2026-04-15",
                content = context.resources.openRawResource(R.raw.release_notes_v120)
                    .bufferedReader()
                    .use { it.readText() }
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.release_notes_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.release_notes_back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.release_notes_desc),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            items(notes) { note ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = note.version,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = note.date,
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = note.content,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

private data class ReleaseNoteEntry(
    val version: String,
    val date: String,
    val content: String
)

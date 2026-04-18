package com.example.bh4haptool.ui.partymode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bh4haptool.R
import com.example.bh4haptool.tool.ToolRegistry

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartyModeRoute(
    onBack: () -> Unit,
    onOpenTool: (String) -> Unit
) {
    val templates = listOf(
        PartyTemplate(
            title = "聚餐破冰",
            description = "先分组，再决定谁先来，最后用幸运转盘挑选游戏或任务。",
            toolIds = listOf("frisbee_group", "turn_queue", "lucky_wheel")
        ),
        PartyTemplate(
            title = "小游戏主持",
            description = "现场记分、控制轮次，再用活动倒计时控场。",
            toolIds = listOf("scoreboard", "turn_queue", "event_countdown")
        ),
        PartyTemplate(
            title = "快速拍板",
            description = "先速决，再需要时补一个权重转盘做最后一轮定夺。",
            toolIds = listOf("quick_decide", "lucky_wheel")
        ),
        PartyTemplate(
            title = "聚餐结算",
            description = "现场记分后直接进入 AA 分账，自动处理垫付与转账清单。",
            toolIds = listOf("scoreboard", "aa_splitter")
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.party_mode_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.party_mode_back)
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
                Text(
                    text = stringResource(R.string.party_mode_desc),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            items(templates) { template ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = template.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = template.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        template.toolIds.forEachIndexed { index, toolId ->
                            val entry = ToolRegistry.entryById(toolId) ?: return@forEachIndexed
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "${index + 1}. ${stringResource(entry.titleRes)}")
                                TextButton(onClick = { onOpenTool(toolId) }) {
                                    Text(text = stringResource(R.string.party_mode_open_tool))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private data class PartyTemplate(
    val title: String,
    val description: String,
    val toolIds: List<String>
)

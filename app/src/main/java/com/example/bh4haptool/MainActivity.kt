package com.example.bh4haptool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.bh4haptool.core.toolkit.data.ToolboxPreferencesRepository
import com.example.bh4haptool.navigation.AppNavHost
import com.example.bh4haptool.update.ReleaseUpdateRepository
import com.example.bh4haptool.ui.theme.BH4HAPtoolTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var repository: ToolboxPreferencesRepository

    @Inject
    lateinit var updateRepository: ReleaseUpdateRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settings by repository.settingsFlow.collectAsState(initial = null)
            val dConfig = settings?.darkThemeConfig ?: com.example.bh4haptool.core.toolkit.data.DarkThemeConfig.FOLLOW_SYSTEM
            val aConfig = settings?.appThemeColor ?: com.example.bh4haptool.core.toolkit.data.AppThemeColor.DEFAULT

            BH4HAPtoolTheme(
                darkThemeConfig = dConfig,
                appThemeColor = aConfig
            ) {
                BH4HAPtoolApp(
                    modifier = Modifier.fillMaxSize(),
                    repository = repository,
                    updateRepository = updateRepository
                )
            }
        }
    }
}

@Composable
fun BH4HAPtoolApp(
    modifier: Modifier = Modifier,
    repository: ToolboxPreferencesRepository,
    updateRepository: ReleaseUpdateRepository
) {
    AppNavHost(
        modifier = modifier,
        repository = repository,
        updateRepository = updateRepository
    )
}

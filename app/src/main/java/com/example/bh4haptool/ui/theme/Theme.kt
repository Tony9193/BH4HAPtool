package com.example.bh4haptool.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

import com.example.bh4haptool.core.toolkit.data.AppThemeColor
import com.example.bh4haptool.core.toolkit.data.DarkThemeConfig

private val PurpleDarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val PurpleLightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

private val BlueDarkColorScheme = darkColorScheme(primary = Blue80, secondary = BlueGrey80, tertiary = LightBlue80)
private val BlueLightColorScheme = lightColorScheme(primary = Blue40, secondary = BlueGrey40, tertiary = LightBlue40)

private val GreenDarkColorScheme = darkColorScheme(primary = Green80, secondary = GreenGrey80, tertiary = Mint80)
private val GreenLightColorScheme = lightColorScheme(primary = Green40, secondary = GreenGrey40, tertiary = Mint40)

private val GrayDarkColorScheme = darkColorScheme(primary = Gray80, secondary = GrayGrey80, tertiary = Silver80)
private val GrayLightColorScheme = lightColorScheme(primary = Gray40, secondary = GrayGrey40, tertiary = Silver40)

private val RedDarkColorScheme = darkColorScheme(primary = Red80, secondary = RedGrey80, tertiary = PinkRed80)
private val RedLightColorScheme = lightColorScheme(primary = Red40, secondary = RedGrey40, tertiary = PinkRed40)

private val OrangeDarkColorScheme = darkColorScheme(primary = Orange80, secondary = OrangeGrey80, tertiary = Amber80)
private val OrangeLightColorScheme = lightColorScheme(primary = Orange40, secondary = OrangeGrey40, tertiary = Amber40)

private val TealDarkColorScheme = darkColorScheme(primary = Teal80, secondary = TealGrey80, tertiary = Cyan80)
private val TealLightColorScheme = lightColorScheme(primary = Teal40, secondary = TealGrey40, tertiary = Cyan40)

@Composable
fun BH4HAPtoolTheme(
    darkThemeConfig: DarkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
    appThemeColor: AppThemeColor = AppThemeColor.DEFAULT,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val isSystemDark = isSystemInDarkTheme()
    val darkTheme = when (darkThemeConfig) {
        DarkThemeConfig.FOLLOW_SYSTEM -> isSystemDark
        DarkThemeConfig.LIGHT -> false
        DarkThemeConfig.DARK -> true
    }

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && appThemeColor == AppThemeColor.DEFAULT -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> when (appThemeColor) {
            AppThemeColor.DEFAULT -> PurpleDarkColorScheme
            AppThemeColor.BLUE -> BlueDarkColorScheme
            AppThemeColor.GREEN -> GreenDarkColorScheme
            AppThemeColor.GRAY -> GrayDarkColorScheme
            AppThemeColor.RED -> RedDarkColorScheme
            AppThemeColor.ORANGE -> OrangeDarkColorScheme
            AppThemeColor.TEAL -> TealDarkColorScheme
        }
        else -> when (appThemeColor) {
            AppThemeColor.DEFAULT -> PurpleLightColorScheme
            AppThemeColor.BLUE -> BlueLightColorScheme
            AppThemeColor.GREEN -> GreenLightColorScheme
            AppThemeColor.GRAY -> GrayLightColorScheme
            AppThemeColor.RED -> RedLightColorScheme
            AppThemeColor.ORANGE -> OrangeLightColorScheme
            AppThemeColor.TEAL -> TealLightColorScheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
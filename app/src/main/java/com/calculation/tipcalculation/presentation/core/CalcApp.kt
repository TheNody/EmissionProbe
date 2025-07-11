package com.calculation.tipcalculation.presentation.core

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.calculation.tipcalculation.presentation.navigation.AppEntryPoint
import com.calculation.tipcalculation.presentation.theme.TipcalculationTheme

@Composable
fun CalcApp() {
    val isDarkTheme = isSystemInDarkTheme()

    val view = LocalView.current
    val context = LocalContext.current
    val activity = context as Activity

    SideEffect {
        val window = activity.window
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val insetsController = WindowInsetsControllerCompat(window, view)
        insetsController.isAppearanceLightStatusBars = !isDarkTheme
        insetsController.isAppearanceLightNavigationBars = !isDarkTheme

        window.setBackgroundDrawable(null)
    }

    TipcalculationTheme(darkTheme = isDarkTheme) {
        AppEntryPoint()
    }
}

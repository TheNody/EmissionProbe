package com.calculation.tipcalculation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.calculation.tipcalculation.db_Main.SettingsViewModel
import com.calculation.tipcalculation.ui.AdvancedSettingsScreen
import com.calculation.tipcalculation.ui.ExternalCalculationScreen
import com.calculation.tipcalculation.ui.ExternalFilterTipsScreen
import com.calculation.tipcalculation.ui.ExternalResultScreen
import com.calculation.tipcalculation.ui.HistoryScreen
import com.calculation.tipcalculation.ui.InternalCalculationScreen
import com.calculation.tipcalculation.ui.InternalFilterTipsScreen
import com.calculation.tipcalculation.ui.InternalResultScreen
import com.calculation.tipcalculation.ui.MainScreen
import com.calculation.tipcalculation.ui.MeasurementCountScreen
import com.calculation.tipcalculation.ui.SettingsScreen
import com.calculation.tipcalculation.viewmodel.CalculationViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val calculationViewModel: CalculationViewModel = viewModel()
    val settingsViewModel: SettingsViewModel = viewModel()

    Scaffold { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavHost(
                navController = navController,
                startDestination = NavigationDestination.MainScreenDestination.destination
            ) {
                composable(NavigationDestination.MainScreenDestination.destination) {
                    MainScreen(navController = navController)
                }
                composable(NavigationDestination.ExternalCalculationScreenDestination.destination) {
                    OverlayScreen {
                        ExternalCalculationScreen(
                            navController = navController,
                            calculationViewModel = calculationViewModel,
                            settingsViewModel = settingsViewModel
                        )
                    }
                }
                composable(NavigationDestination.InternalCalculationScreenDestination.destination) {
                    OverlayScreen {
                        InternalCalculationScreen(
                            navController = navController,
                            calculationViewModel = calculationViewModel,
                            settingsViewModel = settingsViewModel
                        )
                    }
                }
                composable(NavigationDestination.ExternalResultScreenDestination.destination) {
                    OverlayScreen {
                        ExternalResultScreen(settingsViewModel = settingsViewModel)
                    }
                }
                composable(NavigationDestination.InternalResultScreenDestination.destination) {
                    OverlayScreen {
                        InternalResultScreen(settingsViewModel = settingsViewModel)
                    }
                }
                composable(NavigationDestination.HistoryScreenDestination.destination) {
                    OverlayScreen {
                        HistoryScreen()
                    }
                }
                composable(NavigationDestination.SettingsScreenDestination.destination) {
                    OverlayScreen {
                        SettingsScreen(navController = navController)
                    }
                }
                composable(NavigationDestination.InternalFilterTipsScreenDestination.destination) {
                    OverlayScreen {
                        InternalFilterTipsScreen()
                    }
                }
                composable(NavigationDestination.ExternalFilterTipsScreenDestination.destination) {
                    OverlayScreen {
                        ExternalFilterTipsScreen()
                    }
                }
                composable(NavigationDestination.MeasurementCountScreenDestination.destination) {
                    OverlayScreen {
                        MeasurementCountScreen(settingsViewModel = settingsViewModel)
                    }
                }
                composable(NavigationDestination.AdvancedSettingsScreenDestination.destination) {
                    OverlayScreen {
                        AdvancedSettingsScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun OverlayScreen(content: @Composable () -> Unit) {
    val transitionState = remember { MutableTransitionState(false) }
    LaunchedEffect(transitionState) {
        transitionState.targetState = true
    }

    AnimatedVisibility(
        visibleState = transitionState,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(durationMillis = 700, easing = EaseOutCubic)
        ),
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(durationMillis = 700, easing = EaseOutCubic)
        )
    ) {
        content()
    }
}

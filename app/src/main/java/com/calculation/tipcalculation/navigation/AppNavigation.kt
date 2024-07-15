package com.calculation.tipcalculation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.calculation.tipcalculation.screen_comp.CustomBottomBar
import com.calculation.tipcalculation.ui.AdvancedSettingsScreen
import com.calculation.tipcalculation.ui.CalculationScreen
import com.calculation.tipcalculation.ui.ExternalFilterTipsScreen
import com.calculation.tipcalculation.ui.HistoryScreen
import com.calculation.tipcalculation.ui.InternalFilterTipsScreen
import com.calculation.tipcalculation.ui.MeasurementCountScreen
import com.calculation.tipcalculation.ui.ResultScreen
import com.calculation.tipcalculation.ui.SettingsScreen
import com.calculation.tipcalculation.viewmodel.CalculationViewModel
import com.calculation.tipcalculation.viewmodel.StateViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    var selectedTab by remember { mutableIntStateOf(0) }
    val calculationViewModel: CalculationViewModel = viewModel()
    val stateViewModel: StateViewModel = viewModel()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    navBackStackEntry?.destination?.route?.let { route ->
        selectedTab = when (route) {
            NavigationDestination.CalculationsScreenDestination.destination -> 0
            "resultScreen/0" -> 1
            "resultScreen/1" -> 1
            NavigationDestination.HistoryScreenDestination.destination -> 2
            NavigationDestination.SettingsScreenDestination.destination -> 3
            else -> selectedTab
        }
    }

    Scaffold(
        bottomBar = {
            CustomBottomBar(
                selectedTab = selectedTab,
                onTabSelected = { tabIndex ->
                    selectedTab = tabIndex
                    when (tabIndex) {
                        0 -> navController.navigate(NavigationDestination.CalculationsScreenDestination.destination) {
                            launchSingleTop = true
                        }
                        1 -> navController.navigate("resultScreen/0") {
                            launchSingleTop = true
                        }
                        2 -> navController.navigate(NavigationDestination.HistoryScreenDestination.destination) {
                            launchSingleTop = true
                        }
                        3 -> navController.navigate(NavigationDestination.SettingsScreenDestination.destination) {
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavHost(
                navController = navController,
                startDestination = NavigationDestination.CalculationsScreenDestination.destination
            ) {
                composable(NavigationDestination.CalculationsScreenDestination.destination) {
                    CalculationScreen(navController = navController, calculationViewModel = calculationViewModel, stateViewModel = stateViewModel)
                }
                composable("resultScreen/{index}") { backStackEntry ->
                    val index = backStackEntry.arguments?.getString("index")?.toInt() ?: 0
                    ResultScreen(viewModel = stateViewModel, tabIndex = index)
                }
                composable(NavigationDestination.HistoryScreenDestination.destination) {
                    HistoryScreen()
                }
                composable(NavigationDestination.SettingsScreenDestination.destination) {
                    SettingsScreen(navController = navController)
                }
                composable(NavigationDestination.InternalFilterTipsScreenDestination.destination) {
                    InternalFilterTipsScreen()
                }
                composable(NavigationDestination.ExternalFilterTipsScreenDestination.destination) {
                    ExternalFilterTipsScreen()
                }
                composable(NavigationDestination.MeasurementCountScreenDestination.destination) {
                    MeasurementCountScreen(stateViewModel = stateViewModel)
                }
                composable(NavigationDestination.AdvancedSettingsScreenDestination.destination) {
                    AdvancedSettingsScreen()
                }
            }
        }
    }
}

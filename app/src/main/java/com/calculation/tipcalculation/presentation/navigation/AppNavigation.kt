package com.calculation.tipcalculation.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.calculation.tipcalculation.presentation.ui.calculator_screen.CalculatorScreen
import com.calculation.tipcalculation.presentation.ui.external_screen.ExternalCalcScreen
import com.calculation.tipcalculation.presentation.ui.external_screen.external_calc.ExternalFilterCalcScreen
import com.calculation.tipcalculation.presentation.ui.external_screen.external_result.ExternalResultScreen
import com.calculation.tipcalculation.presentation.ui.history_screen.HistoryScreen
import com.calculation.tipcalculation.presentation.ui.internal_screen.InternalCalcScreen
import com.calculation.tipcalculation.presentation.ui.internal_screen.internal_calc.InternalFilterCalcScreen
import com.calculation.tipcalculation.presentation.ui.internal_screen.internal_result.InternalResultScreen
import com.calculation.tipcalculation.presentation.ui.main_screen.MainScreen
import com.calculation.tipcalculation.presentation.ui.screens.*
import com.calculation.tipcalculation.presentation.ui.settings_screen.SettingsScreen
import com.calculation.tipcalculation.presentation.ui.speed_screen.SpeedCountScreen
import com.calculation.tipcalculation.utils.navigateSingleTopTo

@Composable
fun AppEntryPoint() {
    val navController = rememberNavController()
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    LaunchedEffect(navBackStackEntry) {
        when (navBackStackEntry?.destination?.route) {
            Screen.Main.route -> selectedTabIndex = 0
            Screen.History.route -> selectedTabIndex = 1
            Screen.Measurement.route -> selectedTabIndex = 2
            Screen.Settings.route -> selectedTabIndex = 3
        }
    }

    AppNavigation(
        navController = navController,
        selectedTabIndex = selectedTabIndex,
        onTabSelected = { index ->
            selectedTabIndex = index
            when (index) {
                0 -> navController.navigateSingleTopTo(Screen.Main.route)
                1 -> navController.navigateSingleTopTo(Screen.History.route)
                2 -> navController.navigateSingleTopTo(Screen.Measurement.route)
                3 -> navController.navigateSingleTopTo(Screen.Settings.route)
            }
        }
    )
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    fun defaultEnter() = slideInHorizontally(
        initialOffsetX = { 1000 },
        animationSpec = tween(300)
    )

    fun defaultExit() = slideOutHorizontally(
        targetOffsetX = { -1000 },
        animationSpec = tween(300)
    )

    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(Screen.Main.route, enterTransition = { defaultEnter() }, exitTransition = { defaultExit() }) {
            MainScreen(
                navController = navController,
                selectedIndex = selectedTabIndex,
                onTabSelected = onTabSelected
            )
        }
        composable(Screen.ExternalCalc.route, enterTransition = { defaultEnter() }, exitTransition = { defaultExit() }) {
            ExternalCalcScreen(navController = navController)
        }
        composable(Screen.InternalCalc.route, enterTransition = { defaultEnter() }, exitTransition = { defaultExit() }) {
            InternalCalcScreen(navController = navController)
        }
        composable(Screen.ExternalResult.route, enterTransition = { defaultEnter() }, exitTransition = { defaultExit() }) {
            ExternalResultScreen(navController = navController)
        }
        composable(Screen.InternalResult.route, enterTransition = { defaultEnter() }, exitTransition = { defaultExit() }) {
            InternalResultScreen(navController = navController)
        }
        composable(Screen.History.route, enterTransition = { defaultEnter() }, exitTransition = { defaultExit() }) {
            HistoryScreen(
                navController = navController,
                selectedIndex = selectedTabIndex,
                onTabSelected = onTabSelected
            )
        }
        composable(Screen.Settings.route, enterTransition = { defaultEnter() }, exitTransition = { defaultExit() }) {
            SettingsScreen(
                navController = navController,
                selectedIndex = selectedTabIndex,
                onTabSelected = onTabSelected
            )
        }
        composable(Screen.Measurement.route, enterTransition = { defaultEnter() }, exitTransition = { defaultExit() }) {
            MeasurementScreen()
        }
        composable(Screen.AdvancedSettings.route, enterTransition = { defaultEnter() }, exitTransition = { defaultExit() }) {
            AdvancedSettingsScreen()
        }
        composable(Screen.Calculator.route, enterTransition = { defaultEnter() }, exitTransition = { defaultExit() }) {
            CalculatorScreen()
        }
        composable(Screen.SpeedCount.route, enterTransition = { defaultEnter() }, exitTransition = { defaultExit() }) {
            SpeedCountScreen(navController = navController)
        }
        composable(Screen.InternalFilterCalc.route, enterTransition = { defaultEnter() }, exitTransition = { defaultExit() }) {
            InternalFilterCalcScreen(navController = navController)
        }
        composable(Screen.ExternalFilterCalc.route, enterTransition = { defaultEnter() }, exitTransition = { defaultExit() }) {
            ExternalFilterCalcScreen(navController = navController)
        }
    }
}
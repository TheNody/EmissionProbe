package com.calculation.tipcalculation.presentation.navigation

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object ExternalCalc : Screen("external_calculation")
    object InternalCalc : Screen("internal_calculation")
    object ExternalResult : Screen("external_result")
    object InternalResult : Screen("internal_result")
    object History : Screen("history")
    object Settings : Screen("settings")
    object Measurement : Screen("measurement")
    object AdvancedSettings : Screen("advanced_settings")
    object Calculator : Screen("calculator")
    object SpeedCount : Screen("speed_count")
    object InternalFilterCalc : Screen("internal_filter_calc")
    object ExternalFilterCalc : Screen("external_filter_calc")
}

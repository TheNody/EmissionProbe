package com.calculation.tipcalculation.presentation.navigation

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object ExternalCalc : Screen("external_calculation")
    object InternalCalc : Screen("internal_calculation")
    object ExternalResult : Screen("external_result")
    object InternalResult : Screen("internal_result")
    object History : Screen("history")
    object Settings : Screen("settings")
    object Calculator : Screen("calculator")
    object SpeedCount : Screen("speed_count")
    object InternalFilterCalc : Screen("internal_filter_calc")
    object ExternalFilterCalc : Screen("external_filter_calc")
    object UsedDocuments : Screen("used_documents")
    object GOST17 : Screen("gost17")
    object SectionTypeSelection : Screen("section_type_selection")
    object RectangularSection : Screen("rectangular_section")
    object RoundSection : Screen("round_section")
}
package com.calculation.tipcalculation.navigation

import com.calculation.tipcalculation.utils.ADVANCED_SETTINS_SCREEN
import com.calculation.tipcalculation.utils.CALCULATIONS_SCREEN
import com.calculation.tipcalculation.utils.EXTERNAL_FILTER_SCREEN
import com.calculation.tipcalculation.utils.HISTORY_SCREEN
import com.calculation.tipcalculation.utils.INTERNAL_FILTER_SCREEN
import com.calculation.tipcalculation.utils.MEASUREMENT_COUNT_SCREEN
import com.calculation.tipcalculation.utils.RESULT_SCREEN
import com.calculation.tipcalculation.utils.SETTINGS_SCREEN

sealed class NavigationDestination(val destination: String) {
    data object CalculationsScreenDestination : NavigationDestination(CALCULATIONS_SCREEN)
    data object LastCalculationScreenDestination : NavigationDestination(RESULT_SCREEN)
    data object HistoryScreenDestination : NavigationDestination(HISTORY_SCREEN)
    data object SettingsScreenDestination : NavigationDestination(SETTINGS_SCREEN)
    data object AdvancedSettingsScreenDestination : NavigationDestination(ADVANCED_SETTINS_SCREEN)
    data object MeasurementCountScreenDestination : NavigationDestination(MEASUREMENT_COUNT_SCREEN)
    data object ExternalFilterTipsScreenDestination : NavigationDestination(EXTERNAL_FILTER_SCREEN)
    data object InternalFilterTipsScreenDestination : NavigationDestination(INTERNAL_FILTER_SCREEN)
}

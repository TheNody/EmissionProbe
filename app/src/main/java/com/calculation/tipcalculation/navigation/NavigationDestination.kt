package com.calculation.tipcalculation.navigation

import com.calculation.tipcalculation.utils.ADVANCED_SETTINS_SCREEN
import com.calculation.tipcalculation.utils.EXTERNAL_CALCULATION_SCREEN
import com.calculation.tipcalculation.utils.EXTERNAL_FILTER_SCREEN
import com.calculation.tipcalculation.utils.EXTERNAL_RESULT_SCREEN
import com.calculation.tipcalculation.utils.HISTORY_SCREEN
import com.calculation.tipcalculation.utils.INTERNAL_CALCULATION_SCREEN
import com.calculation.tipcalculation.utils.INTERNAL_FILTER_SCREEN
import com.calculation.tipcalculation.utils.INTERNAL_RESULT_SCREEN
import com.calculation.tipcalculation.utils.MAIN_SCREEN
import com.calculation.tipcalculation.utils.MEASUREMENT_COUNT_SCREEN
import com.calculation.tipcalculation.utils.SETTINGS_SCREEN

sealed class NavigationDestination(val destination: String) {
    data object ExternalCalculationScreenDestination : NavigationDestination(EXTERNAL_CALCULATION_SCREEN)
    data object InternalCalculationScreenDestination : NavigationDestination(INTERNAL_CALCULATION_SCREEN)
    data object HistoryScreenDestination : NavigationDestination(HISTORY_SCREEN)
    data object SettingsScreenDestination : NavigationDestination(SETTINGS_SCREEN)
    data object AdvancedSettingsScreenDestination : NavigationDestination(ADVANCED_SETTINS_SCREEN)
    data object MeasurementCountScreenDestination : NavigationDestination(MEASUREMENT_COUNT_SCREEN)
    data object ExternalFilterTipsScreenDestination : NavigationDestination(EXTERNAL_FILTER_SCREEN)
    data object InternalFilterTipsScreenDestination : NavigationDestination(INTERNAL_FILTER_SCREEN)
    data object MainScreenDestination : NavigationDestination(MAIN_SCREEN)
    data object InternalResultScreenDestination : NavigationDestination(INTERNAL_RESULT_SCREEN)
    data object ExternalResultScreenDestination : NavigationDestination(EXTERNAL_RESULT_SCREEN)
}

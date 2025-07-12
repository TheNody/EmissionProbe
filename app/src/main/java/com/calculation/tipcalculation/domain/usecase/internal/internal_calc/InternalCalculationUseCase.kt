package com.calculation.tipcalculation.domain.usecase.internal_calc

import com.calculation.tipcalculation.domain.usecase.common.CalculationCommon
import javax.inject.Inject

class InternalCalculationUseCase @Inject constructor() {
    fun calculateVpForSelectedTip(
        selectedDiameter: Double,
        speeds: List<Double>,
        patm: Double,
        plsr: Double,
        tsr: Double,
        tasp: Double,
        preom: Double
    ): Double {
        val srznach = CalculationCommon.calculateAverage(speeds)
        val patmPa = CalculationCommon.convertPatmToPa(patm)

        return CalculationCommon.calculateVp(
            diameter = selectedDiameter,
            patmValue = patmPa,
            plsrValue = plsr,
            tsrValue = tsr,
            taspValue = tasp,
            preomValue = preom,
            srznach = srznach
        )
    }
}
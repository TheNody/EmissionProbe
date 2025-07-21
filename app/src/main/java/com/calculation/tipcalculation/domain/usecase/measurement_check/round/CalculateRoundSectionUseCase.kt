package com.calculation.tipcalculation.domain.usecase.measurement_check.round

import com.calculation.tipcalculation.utils.GOSTMeasurementTable
import com.calculation.tipcalculation.utils.GostKiTable
import com.calculation.tipcalculation.domain.model.RoundSectionResult

class CalculateRoundSectionUseCase {

    operator fun invoke(d: Double, l: Double): RoundSectionResult? {
        val de = d / Math.PI
        val lOverDe = l / de

        val rule = GOSTMeasurementTable.findRule(d, lOverDe) ?: return null

        val adjustedRule = if (lOverDe < 4.0) {
            rule.copy(
                totalPoints = rule.totalPoints * 2,
                diameterPoints = rule.diameterPoints * 2
            )
        } else {
            rule
        }

        val ki = GostKiTable.getByTotalPoints(adjustedRule.totalPoints)

        return RoundSectionResult(
            d = d,
            de = de,
            lOverDe = lOverDe,
            rule = adjustedRule,
            ki = ki
        )
    }
}

package com.calculation.tipcalculation.domain.usecase.measurement_check.round

import com.calculation.tipcalculation.utils.GOSTMeasurementTable
import com.calculation.tipcalculation.utils.GostKiTable
import com.calculation.tipcalculation.domain.model.RoundSectionResult
import kotlin.math.PI

class CalculateRoundSectionUseCase {

    operator fun invoke(d: Double, l: Double): RoundSectionResult? {
        val de = d / PI
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

        val lz = if (lOverDe < 8.0) {
            0.35 * l - 0.4
        } else {
            3.0 * de
        }

        return RoundSectionResult(
            d = d,
            de = de,
            lOverDe = lOverDe,
            rule = adjustedRule,
            ki = ki,
            lz = lz
        )
    }
}
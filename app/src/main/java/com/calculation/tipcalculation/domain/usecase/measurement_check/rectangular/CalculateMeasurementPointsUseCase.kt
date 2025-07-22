package com.calculation.tipcalculation.domain.usecase.measurement_check.rectangular

import com.calculation.tipcalculation.domain.model.MeasurementInput
import com.calculation.tipcalculation.domain.model.MeasurementResult
import com.calculation.tipcalculation.utils.AspectRatioCategory
import com.calculation.tipcalculation.utils.GostKiTable
import com.calculation.tipcalculation.utils.RectangularMeasurementRules

class CalculateMeasurementPointsUseCase {
    operator fun invoke(input: MeasurementInput): MeasurementResult {
        val (a, b, l) = input

        val de = 2 * a * b / (a + b)

        val rawLOverDe = l / de
        val adjustedLOverDe = if (rawLOverDe < 8.0) 4.0 else rawLOverDe

        val aspectRatio = maxOf(a, b) / minOf(a, b)
        val aspectCategory = when {
            aspectRatio <= 1.6 -> AspectRatioCategory.FROM_1_TO_1_6
            aspectRatio <= 2.5 -> AspectRatioCategory.FROM_1_6_TO_2_5
            else -> AspectRatioCategory.ABOVE_2_5
        }

        val rule = RectangularMeasurementRules.rules.firstOrNull {
            de in it.dMin..it.dMax &&
                    adjustedLOverDe in it.lOverDMin..it.lOverDMax &&
                    it.aspectCategory == aspectCategory
        }

        val ki = rule?.let { GostKiTable.getByTotalPoints(it.totalPoints) }

        val lz = if (rawLOverDe < 8.0) {
            0.35 * l - 0.4
        } else {
            3.0 * de
        }

        return MeasurementResult(
            de = de,
            lOverDe = rawLOverDe,
            rule = rule,
            ki = ki,
            lz = lz
        )
    }
}

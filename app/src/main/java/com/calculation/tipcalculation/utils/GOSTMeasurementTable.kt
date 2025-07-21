package com.calculation.tipcalculation.utils

data class MeasurementRule(
    val dMin: Double,
    val dMax: Double,
    val lOverDeMin: Double,
    val lOverDeMax: Double,
    val totalPoints: Int,
    val diameterPoints: Int
)

object GOSTMeasurementTable {
    private val rules = listOf(

        // До 200 мм
        MeasurementRule(dMin = 0.0, dMax = 200.0, lOverDeMin = 5.5, lOverDeMax = Double.MAX_VALUE, totalPoints = 1, diameterPoints = 1),

        // 200–500 мм включ.
        MeasurementRule(dMin = 200.01, dMax = 500.0, lOverDeMin = 5.5, lOverDeMax = Double.MAX_VALUE, totalPoints = 1, diameterPoints = 1),
        MeasurementRule(dMin = 200.01, dMax = 500.0, lOverDeMin = 4.0, lOverDeMax = 5.5, totalPoints = 2, diameterPoints = 2),

        // Св. 500 до 900 мм включ.
        MeasurementRule(dMin = 500.01, dMax = 900.0, lOverDeMin = 5.5, lOverDeMax = Double.MAX_VALUE, totalPoints = 4, diameterPoints = 2),
        MeasurementRule(dMin = 500.01, dMax = 900.0, lOverDeMin = 4.0, lOverDeMax = 5.5, totalPoints = 8, diameterPoints = 4),
        MeasurementRule(dMin = 500.01, dMax = 900.0, lOverDeMin = 2.5, lOverDeMax = 4.0, totalPoints = 12, diameterPoints = 6),

        // Св. 900 до 1400 включ.
        MeasurementRule(dMin = 900.01, dMax = 1400.0, lOverDeMin = 5.5, lOverDeMax = Double.MAX_VALUE, totalPoints = 8, diameterPoints = 4),
        MeasurementRule(dMin = 900.01, dMax = 1400.0, lOverDeMin = 4.0, lOverDeMax = 5.5, totalPoints = 12, diameterPoints = 6),
        MeasurementRule(dMin = 900.01, dMax = 1400.0, lOverDeMin = 2.5, lOverDeMax = 4.0, totalPoints = 16, diameterPoints = 8),
        MeasurementRule(dMin = 900.01, dMax = 1400.0, lOverDeMin = 0.0, lOverDeMax = 2.5, totalPoints = 20, diameterPoints = 10),

        // Св. 1400 до 2000 включ.
        MeasurementRule(dMin = 1400.01, dMax = 2000.0, lOverDeMin = 5.5, lOverDeMax = Double.MAX_VALUE, totalPoints = 12, diameterPoints = 6),
        MeasurementRule(dMin = 1400.01, dMax = 2000.0, lOverDeMin = 4.0, lOverDeMax = 5.5, totalPoints = 16, diameterPoints = 8),

        // Св. 2000 до 2700 включ.
        MeasurementRule(dMin = 2000.01, dMax = 2700.0, lOverDeMin = 5.5, lOverDeMax = Double.MAX_VALUE, totalPoints = 16, diameterPoints = 8),
        MeasurementRule(dMin = 2000.01, dMax = 2700.0, lOverDeMin = 4.0, lOverDeMax = 5.5, totalPoints = 20, diameterPoints = 10),
        MeasurementRule(dMin = 2000.01, dMax = 2700.0, lOverDeMin = 2.5, lOverDeMax = 4.0, totalPoints = 24, diameterPoints = 12),
        MeasurementRule(dMin = 2000.01, dMax = 2700.0, lOverDeMin = 0.0, lOverDeMax = 2.5, totalPoints = 28, diameterPoints = 14),

        // Св. 2700 до 3500 включ.
        MeasurementRule(dMin = 2700.01, dMax = 3500.0, lOverDeMin = 5.5, lOverDeMax = Double.MAX_VALUE, totalPoints = 20, diameterPoints = 10),
        MeasurementRule(dMin = 2700.01, dMax = 3500.0, lOverDeMin = 4.0, lOverDeMax = 5.5, totalPoints = 24, diameterPoints = 12),
        MeasurementRule(dMin = 2700.01, dMax = 3500.0, lOverDeMin = 2.5, lOverDeMax = 4.0, totalPoints = 28, diameterPoints = 14),
        MeasurementRule(dMin = 2700.01, dMax = 3500.0, lOverDeMin = 0.0, lOverDeMax = 2.5, totalPoints = 32, diameterPoints = 16),

        // Св. 3500
        MeasurementRule(dMin = 3500.01, dMax = Double.MAX_VALUE, lOverDeMin = 5.5, lOverDeMax = Double.MAX_VALUE, totalPoints = 24, diameterPoints = 12),
        MeasurementRule(dMin = 3500.01, dMax = Double.MAX_VALUE, lOverDeMin = 4.0, lOverDeMax = 5.5, totalPoints = 28, diameterPoints = 14),
        MeasurementRule(dMin = 3500.01, dMax = Double.MAX_VALUE, lOverDeMin = 2.5, lOverDeMax = 4.0, totalPoints = 32, diameterPoints = 16),
        MeasurementRule(dMin = 3500.01, dMax = Double.MAX_VALUE, lOverDeMin = 0.0, lOverDeMax = 2.5, totalPoints = 36, diameterPoints = 18)
    )

    fun findRule(d: Double, lOverDe: Double): MeasurementRule? {
        return rules.firstOrNull {
            d in it.dMin..it.dMax &&
                    lOverDe >= it.lOverDeMin &&
                    lOverDe < it.lOverDeMax
        }
    }
}

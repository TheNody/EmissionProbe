package com.calculation.tipcalculation.utils

data class MeasurementPointRule(
    val dMin: Double,
    val dMax: Double,
    val lOverDMin: Double,
    val lOverDMax: Double,
    val aspectCategory: AspectRatioCategory,
    val na: Int,
    val nb: Int
) {
    val totalPoints: Int get() = na * nb
}

enum class AspectRatioCategory(val label: String) {
    FROM_1_TO_1_6("1–1.6"),
    FROM_1_6_TO_2_5("1.6–2.5"),
    ABOVE_2_5(">2.5")
}

object RectangularMeasurementRules {
    val rules = listOf(
        // До 200 мм
        MeasurementPointRule(0.0, 200.0, 5.5, Double.MAX_VALUE, AspectRatioCategory.FROM_1_TO_1_6, 1, 1),
        MeasurementPointRule(0.0, 200.0, 5.5, Double.MAX_VALUE, AspectRatioCategory.FROM_1_6_TO_2_5, 1, 2),
        MeasurementPointRule(0.0, 200.0, 5.5, Double.MAX_VALUE, AspectRatioCategory.ABOVE_2_5, 1, 3),

        MeasurementPointRule(0.0, 200.0, 4.0, 5.5, AspectRatioCategory.FROM_1_TO_1_6, 2, 2),
        MeasurementPointRule(0.0, 200.0, 4.0, 5.5, AspectRatioCategory.FROM_1_6_TO_2_5, 2, 2),
        MeasurementPointRule(0.0, 200.0, 4.0, 5.5, AspectRatioCategory.ABOVE_2_5, 2, 3),

        // От 200 до 500 мм включительно
        MeasurementPointRule(200.0, 500.0, 5.5, Double.MAX_VALUE, AspectRatioCategory.FROM_1_TO_1_6, 1, 1),
        MeasurementPointRule(200.0, 500.0, 5.5, Double.MAX_VALUE, AspectRatioCategory.FROM_1_6_TO_2_5, 1, 2),
        MeasurementPointRule(200.0, 500.0, 5.5, Double.MAX_VALUE, AspectRatioCategory.ABOVE_2_5, 1, 3),

        MeasurementPointRule(200.0, 500.0, 4.0, 5.5, AspectRatioCategory.FROM_1_TO_1_6, 2, 2),
        MeasurementPointRule(200.0, 500.0, 4.0, 5.5, AspectRatioCategory.FROM_1_6_TO_2_5, 2, 2),
        MeasurementPointRule(200.0, 500.0, 4.0, 5.5, AspectRatioCategory.ABOVE_2_5, 2, 3),

        MeasurementPointRule(200.0, 500.0, 2.5, 4.0, AspectRatioCategory.FROM_1_TO_1_6, 2, 4),
        MeasurementPointRule(200.0, 500.0, 2.5, 4.0, AspectRatioCategory.FROM_1_6_TO_2_5, 2, 4),
        MeasurementPointRule(200.0, 500.0, 2.5, 4.0, AspectRatioCategory.ABOVE_2_5, 2, 5),

        //Св. 500 до 900 включ.
        MeasurementPointRule(500.0, 900.0, 5.5, Double.MAX_VALUE, AspectRatioCategory.FROM_1_TO_1_6, 2, 2),
        MeasurementPointRule(500.0, 900.0, 5.5, Double.MAX_VALUE, AspectRatioCategory.FROM_1_6_TO_2_5, 2, 2),
        MeasurementPointRule(500.0, 900.0, 5.5, Double.MAX_VALUE, AspectRatioCategory.ABOVE_2_5, 2, 3),

        MeasurementPointRule(500.0, 900.0, 4.0, 5.5, AspectRatioCategory.FROM_1_TO_1_6, 2, 4),
        MeasurementPointRule(500.0, 900.0, 4.0, 5.5, AspectRatioCategory.FROM_1_6_TO_2_5, 2, 4),
        MeasurementPointRule(500.0, 900.0, 4.0, 5.5, AspectRatioCategory.ABOVE_2_5, 2, 5),

        MeasurementPointRule(500.0, 900.0, 2.5, 4.0, AspectRatioCategory.FROM_1_TO_1_6, 3, 4),
        MeasurementPointRule(500.0, 900.0, 2.5, 4.0, AspectRatioCategory.FROM_1_6_TO_2_5, 3, 5),
        MeasurementPointRule(500.0, 900.0, 2.5, 4.0, AspectRatioCategory.ABOVE_2_5, 3, 5),

        // Св. 900 до 1400 включ.
        MeasurementPointRule(900.0, 1400.0, 5.5, Double.MAX_VALUE, AspectRatioCategory.FROM_1_TO_1_6, 2, 4),
        MeasurementPointRule(900.0, 1400.0, 5.5, Double.MAX_VALUE, AspectRatioCategory.FROM_1_6_TO_2_5, 2, 4),
        MeasurementPointRule(900.0, 1400.0, 5.5, Double.MAX_VALUE, AspectRatioCategory.ABOVE_2_5, 2, 5),

        MeasurementPointRule(900.0, 1400.0, 4.0, 5.5, AspectRatioCategory.FROM_1_TO_1_6, 3, 4),
        MeasurementPointRule(900.0, 1400.0, 4.0, 5.5, AspectRatioCategory.FROM_1_6_TO_2_5, 3, 5),
        MeasurementPointRule(900.0, 1400.0, 4.0, 5.5, AspectRatioCategory.ABOVE_2_5, 3, 5),

        MeasurementPointRule(900.0, 1400.0, 2.5, 4.0, AspectRatioCategory.FROM_1_TO_1_6, 4, 4),
        MeasurementPointRule(900.0, 1400.0, 2.5, 4.0, AspectRatioCategory.FROM_1_6_TO_2_5, 3, 6),
        MeasurementPointRule(900.0, 1400.0, 2.5, 4.0, AspectRatioCategory.ABOVE_2_5, 3, 6),

        MeasurementPointRule(900.0, 1400.0, 0.0, 2.5, AspectRatioCategory.FROM_1_TO_1_6, 4, 6),
        MeasurementPointRule(900.0, 1400.0, 0.0, 2.5, AspectRatioCategory.FROM_1_6_TO_2_5, 3, 8),
        MeasurementPointRule(900.0, 1400.0, 0.0, 2.5, AspectRatioCategory.ABOVE_2_5, 3, 8),

        // Св. 1400 до 2000 включ.
        MeasurementPointRule(1400.0, 2000.0, 5.5, Double.MAX_VALUE, AspectRatioCategory.FROM_1_TO_1_6, 3, 4),
        MeasurementPointRule(1400.0, 2000.0, 5.5, Double.MAX_VALUE, AspectRatioCategory.FROM_1_6_TO_2_5, 3, 5),
        MeasurementPointRule(1400.0, 2000.0, 5.5, Double.MAX_VALUE, AspectRatioCategory.ABOVE_2_5, 3, 5),

        MeasurementPointRule(1400.0, 2000.0, 4.0, 5.5, AspectRatioCategory.FROM_1_TO_1_6, 4, 4),
        MeasurementPointRule(1400.0, 2000.0, 4.0, 5.5, AspectRatioCategory.FROM_1_6_TO_2_5, 3, 6),
        MeasurementPointRule(1400.0, 2000.0, 4.0, 5.5, AspectRatioCategory.ABOVE_2_5, 3, 6),

        MeasurementPointRule(1400.0, 2000.0, 2.5, 4.0, AspectRatioCategory.FROM_1_TO_1_6, 4, 5),
        MeasurementPointRule(1400.0, 2000.0, 2.5, 4.0, AspectRatioCategory.FROM_1_6_TO_2_5, 4, 5),
        MeasurementPointRule(1400.0, 2000.0, 2.5, 4.0, AspectRatioCategory.ABOVE_2_5, 3, 7),

        MeasurementPointRule(1400.0, 2000.0, 0.0, 2.5, AspectRatioCategory.FROM_1_TO_1_6, 4, 7),
        MeasurementPointRule(1400.0, 2000.0, 0.0, 2.5, AspectRatioCategory.FROM_1_6_TO_2_5, 4, 7),
        MeasurementPointRule(1400.0, 2000.0, 0.0, 2.5, AspectRatioCategory.ABOVE_2_5, 3, 10),

        // Св. 2000 до 2700 включ.
        MeasurementPointRule(2000.0, 2700.0, 5.5, Double.MAX_VALUE, AspectRatioCategory.FROM_1_TO_1_6, 4, 4),
        MeasurementPointRule(2000.0, 2700.0, 5.5, Double.MAX_VALUE, AspectRatioCategory.FROM_1_6_TO_2_5, 3, 6),
        MeasurementPointRule(2000.0, 2700.0, 5.5, Double.MAX_VALUE, AspectRatioCategory.ABOVE_2_5, 3, 6),

        MeasurementPointRule(2000.0, 2700.0, 4.0, 5.5, AspectRatioCategory.FROM_1_TO_1_6, 4, 5),
        MeasurementPointRule(2000.0, 2700.0, 4.0, 5.5, AspectRatioCategory.FROM_1_6_TO_2_5, 4, 5),
        MeasurementPointRule(2000.0, 2700.0, 4.0, 5.5, AspectRatioCategory.ABOVE_2_5, 3, 7),

        MeasurementPointRule(2000.0, 2700.0, 2.5, 4.0, AspectRatioCategory.FROM_1_TO_1_6, 4, 6),
        MeasurementPointRule(2000.0, 2700.0, 2.5, 4.0, AspectRatioCategory.FROM_1_6_TO_2_5, 4, 6),
        MeasurementPointRule(2000.0, 2700.0, 2.5, 4.0, AspectRatioCategory.ABOVE_2_5, 3, 8),

        MeasurementPointRule(2000.0, 2700.0, 0.0, 2.5, AspectRatioCategory.FROM_1_TO_1_6, 4, 8),
        MeasurementPointRule(2000.0, 2700.0, 0.0, 2.5, AspectRatioCategory.FROM_1_6_TO_2_5, 4, 8),
        MeasurementPointRule(2000.0, 2700.0, 0.0, 2.5, AspectRatioCategory.ABOVE_2_5, 4, 11),

        // Св. 2700 до 3500 включ.
        MeasurementPointRule(2700.0, 3500.0, 5.5, Double.MAX_VALUE, AspectRatioCategory.FROM_1_TO_1_6, 4, 5),
        MeasurementPointRule(2700.0, 3500.0, 5.5, Double.MAX_VALUE, AspectRatioCategory.FROM_1_6_TO_2_5, 4, 5),
        MeasurementPointRule(2700.0, 3500.0, 5.5, Double.MAX_VALUE, AspectRatioCategory.ABOVE_2_5, 3, 7),

        MeasurementPointRule(2700.0, 3500.0, 4.0, 5.5, AspectRatioCategory.FROM_1_TO_1_6, 4, 6),
        MeasurementPointRule(2700.0, 3500.0, 4.0, 5.5, AspectRatioCategory.FROM_1_6_TO_2_5, 4, 6),
        MeasurementPointRule(2700.0, 3500.0, 4.0, 5.5, AspectRatioCategory.ABOVE_2_5, 3, 8),

        MeasurementPointRule(2700.0, 3500.0, 2.5, 4.0, AspectRatioCategory.FROM_1_TO_1_6, 4, 7),
        MeasurementPointRule(2700.0, 3500.0, 2.5, 4.0, AspectRatioCategory.FROM_1_6_TO_2_5, 4, 7),
        MeasurementPointRule(2700.0, 3500.0, 2.5, 4.0, AspectRatioCategory.ABOVE_2_5, 4, 7),

        MeasurementPointRule(2700.0, 3500.0, 0.0, 2.5, AspectRatioCategory.FROM_1_TO_1_6, 4, 10),
        MeasurementPointRule(2700.0, 3500.0, 0.0, 2.5, AspectRatioCategory.FROM_1_6_TO_2_5, 4, 10),
        MeasurementPointRule(2700.0, 3500.0, 0.0, 2.5, AspectRatioCategory.ABOVE_2_5, 4, 10),

        // Св. 3500
        MeasurementPointRule(3500.0, Double.MAX_VALUE, 5.5, Double.MAX_VALUE, AspectRatioCategory.FROM_1_TO_1_6, 4, 6),
        MeasurementPointRule(3500.0, Double.MAX_VALUE, 5.5, Double.MAX_VALUE, AspectRatioCategory.FROM_1_6_TO_2_5, 4, 6),
        MeasurementPointRule(3500.0, Double.MAX_VALUE, 5.5, Double.MAX_VALUE, AspectRatioCategory.ABOVE_2_5, 3, 8),

        MeasurementPointRule(3500.0, Double.MAX_VALUE, 4.0, 5.5, AspectRatioCategory.FROM_1_TO_1_6, 4, 7),
        MeasurementPointRule(3500.0, Double.MAX_VALUE, 4.0, 5.5, AspectRatioCategory.FROM_1_6_TO_2_5, 4, 7),
        MeasurementPointRule(3500.0, Double.MAX_VALUE, 4.0, 5.5, AspectRatioCategory.ABOVE_2_5, 4, 7),

        MeasurementPointRule(3500.0, Double.MAX_VALUE, 2.5, 4.0, AspectRatioCategory.FROM_1_TO_1_6, 4, 8),
        MeasurementPointRule(3500.0, Double.MAX_VALUE, 2.5, 4.0, AspectRatioCategory.FROM_1_6_TO_2_5, 4, 8),
        MeasurementPointRule(3500.0, Double.MAX_VALUE, 2.5, 4.0, AspectRatioCategory.ABOVE_2_5, 4, 8),

        MeasurementPointRule(3500.0, Double.MAX_VALUE, 0.0, 2.5, AspectRatioCategory.FROM_1_TO_1_6, 4, 11),
        MeasurementPointRule(3500.0, Double.MAX_VALUE, 0.0, 2.5, AspectRatioCategory.FROM_1_6_TO_2_5, 4, 11),
        MeasurementPointRule(3500.0, Double.MAX_VALUE, 0.0, 2.5, AspectRatioCategory.ABOVE_2_5, 4, 11)
    )
}


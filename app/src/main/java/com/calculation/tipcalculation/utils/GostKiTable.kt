package com.calculation.tipcalculation.utils

data class KiCoefficients(
    val totalPoints: Int,
    val perLine: Int,
    val kValues: List<Double>
)

object GostKiTable {
    val all: List<KiCoefficients> = listOf(

        KiCoefficients(
            totalPoints = 36,
            perLine = 18,
            kValues = listOf(
                1.41, 4.36, 7.51, 10.91, 14.64, 18.82, 23.65, 29.59, 38.21,
                61.79, 70.41, 76.35, 81.18, 85.36, 89.09, 92.49, 95.64, 98.59
            )
        ),
        KiCoefficients(
            totalPoints = 32,
            perLine = 16,
            kValues = listOf(
                1.59, 4.93, 8.54, 12.50, 16.93, 22.05, 28.35, 37.50, 62.50,
                71.65, 77.95, 83.07, 87.50, 91.46, 95.07, 98.41
            )
        ),
        KiCoefficients(
            totalPoints = 28,
            perLine = 14,
            kValues = listOf(
                1.82, 5.68, 9.91, 14.65, 20.12, 26.85, 36.64, 63.36, 73.15,
                79.88, 85.35, 90.09, 94.32, 98.18
            )
        ),
        KiCoefficients(
            totalPoints = 24,
            perLine = 12,
            kValues = listOf(
                2.13, 6.70, 11.81, 17.72, 25.00, 35.57, 64.43, 75.00,
                82.28, 88.19, 93.30, 97.87
            )
        ),
        KiCoefficients(
            totalPoints = 20,
            perLine = 10,
            kValues = listOf(
                2.57, 8.17, 14.65, 22.61, 34.19, 65.81, 77.39,
                85.35, 91.83, 91.43
            )
        ),
        KiCoefficients(
            totalPoints = 16,
            perLine = 8,
            kValues = listOf(
                3.23, 10.47, 19.38, 32.32, 67.68, 80.62, 89.53, 96.77
            )
        ),
        KiCoefficients(
            totalPoints = 12,
            perLine = 6,
            kValues = listOf(
                4.36, 14.65, 29.59, 70.41, 85.35, 95.64
            )
        ),
        KiCoefficients(
            totalPoints = 8,
            perLine = 4,
            kValues = listOf(
                6.70, 25.00, 75.00, 93.30
            )
        ),
        KiCoefficients(
            totalPoints = 4,
            perLine = 2,
            kValues = listOf(
                14.65, 95.35
            )
        )
    )

    fun getByTotalPoints(n: Int): KiCoefficients? = all.firstOrNull { it.totalPoints == n }
}

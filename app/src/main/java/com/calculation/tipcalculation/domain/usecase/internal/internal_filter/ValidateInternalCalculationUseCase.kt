package com.calculation.tipcalculation.domain.usecase.internal_filter

import com.calculation.tipcalculation.domain.model.ValidationResult
import com.calculation.tipcalculation.domain.usecase.speed_count.GetSpeedUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ValidateInternalCalculationUseCase(
    private val getSpeedUseCase: GetSpeedUseCase,
    private val getFilterTipsSyncUseCase: GetFilterTipsSyncUseCase
) {
    suspend operator fun invoke(): ValidationResult = withContext(Dispatchers.IO) {
        val speeds = getSpeedUseCase()
        val tips = getFilterTipsSyncUseCase()

        when {
            speeds == null -> ValidationResult.MissingSpeeds
            tips.isEmpty() -> ValidationResult.MissingTips
            else -> ValidationResult.Valid
        }
    }
}
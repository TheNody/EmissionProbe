package com.calculation.tipcalculation.domain.usecase.external.external_filter

import com.calculation.tipcalculation.domain.model.ValidationResult
import com.calculation.tipcalculation.domain.usecase.speed_count.GetSpeedUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ValidateExternalCalculationUseCase(
    private val getSpeedUseCase: GetSpeedUseCase,
    private val getExternalTipsSyncUseCase: GetExternalTipsSyncUseCase
) {
    suspend operator fun invoke(): ValidationResult = withContext(Dispatchers.IO) {
        val speeds = getSpeedUseCase()
        val tips = getExternalTipsSyncUseCase()

        when {
            speeds == null -> ValidationResult.MissingSpeeds
            tips.isEmpty() -> ValidationResult.MissingTips
            else -> ValidationResult.Valid
        }
    }
}
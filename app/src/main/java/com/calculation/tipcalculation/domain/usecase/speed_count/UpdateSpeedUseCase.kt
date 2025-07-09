package com.calculation.tipcalculation.domain.usecase.speed_count

import com.calculation.tipcalculation.domain.model.SpeedCount
import com.calculation.tipcalculation.domain.repository.SpeedCountRepository

class UpdateSpeedUseCase(
    private val repository: SpeedCountRepository
) {
    suspend operator fun invoke(speed: SpeedCount) {
        repository.update(speed)
    }
}
package com.calculation.tipcalculation.domain.usecase.speed_count

import com.calculation.tipcalculation.domain.repository.SpeedCountRepository

class DeleteSpeedUseCase(private val repository: SpeedCountRepository) {
    suspend operator fun invoke() {
        repository.deleteAll()
    }
}

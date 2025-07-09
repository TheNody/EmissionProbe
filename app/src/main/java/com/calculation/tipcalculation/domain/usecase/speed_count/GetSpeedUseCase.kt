package com.calculation.tipcalculation.domain.usecase.speed_count

import com.calculation.tipcalculation.domain.model.SpeedCount
import com.calculation.tipcalculation.domain.repository.SpeedCountRepository

class GetSpeedUseCase(private val repository: SpeedCountRepository) {
    suspend operator fun invoke(): SpeedCount? {
        return repository.get()
    }
}

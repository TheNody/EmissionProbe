package com.calculation.tipcalculation.domain.usecase.internal_filter

import com.calculation.tipcalculation.domain.repository.FilterTipRepository

class GetFilterTipsSyncUseCase(
    private val repository: FilterTipRepository
) {
    operator fun invoke() = repository.getAllSync()
}

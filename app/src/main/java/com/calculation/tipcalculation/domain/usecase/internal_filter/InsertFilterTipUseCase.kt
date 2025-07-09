package com.calculation.tipcalculation.domain.usecase.internal_filter

import com.calculation.tipcalculation.domain.model.FilterTip
import com.calculation.tipcalculation.domain.repository.FilterTipRepository

class InsertFilterTipUseCase(
    private val repository: FilterTipRepository
) {
    suspend operator fun invoke(tip: FilterTip) = repository.insert(tip)
}

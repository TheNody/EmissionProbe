package com.calculation.tipcalculation.domain.usecase.external.external_filter

import com.calculation.tipcalculation.domain.repository.ExternalFilterTipRepository

class GetExternalTipsUseCase(
    private val repository: ExternalFilterTipRepository
) {
    operator fun invoke() = repository.getAll()
}

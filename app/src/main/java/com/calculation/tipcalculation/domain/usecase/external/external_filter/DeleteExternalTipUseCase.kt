package com.calculation.tipcalculation.domain.usecase.external.external_filter

import com.calculation.tipcalculation.domain.model.ExternalFilterTip
import com.calculation.tipcalculation.domain.repository.ExternalFilterTipRepository

class DeleteExternalTipUseCase(
    private val repository: ExternalFilterTipRepository
) {
    suspend operator fun invoke(tip: ExternalFilterTip) {
        repository.delete(tip)
    }
}

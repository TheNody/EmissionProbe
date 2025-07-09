package com.calculation.tipcalculation.domain.usecase.report_data

import com.calculation.tipcalculation.domain.repository.ReportDataRepository

class GetAllReportsUseCase(private val repository: ReportDataRepository) {
    suspend operator fun invoke() = repository.getAll()
}

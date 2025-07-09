package com.calculation.tipcalculation.domain.usecase.report_data

import com.calculation.tipcalculation.domain.model.ReportData
import com.calculation.tipcalculation.domain.repository.ReportDataRepository

class DeleteReportUseCase(private val repository: ReportDataRepository) {
    suspend operator fun invoke(data: ReportData) {
        repository.delete(data)
    }
}

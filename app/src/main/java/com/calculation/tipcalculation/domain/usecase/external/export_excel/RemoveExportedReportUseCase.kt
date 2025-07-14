package com.calculation.tipcalculation.domain.usecase.external.export_excel

import com.calculation.tipcalculation.domain.repository.ExportedReportRepository

class RemoveExportedReportUseCase(private val repo: ExportedReportRepository) {
    operator fun invoke(timestamp: String) = repo.removeTimestamp(timestamp)
}
package com.calculation.tipcalculation.domain.usecase.external.export_excel

import com.calculation.tipcalculation.domain.repository.ExportedReportRepository

class GetExportedReportsUseCase(private val repo: ExportedReportRepository) {
    operator fun invoke(): Set<String> = repo.getSavedTimestamps()
}
package com.calculation.tipcalculation.domain.usecase.external.export_excel

import android.content.Context
import com.calculation.tipcalculation.domain.repository.ExportedReportRepository

class InvalidateExportedReportsUseCase(private val repo: ExportedReportRepository) {
    operator fun invoke(context: Context) = repo.invalidateDeletedReports(context)
}
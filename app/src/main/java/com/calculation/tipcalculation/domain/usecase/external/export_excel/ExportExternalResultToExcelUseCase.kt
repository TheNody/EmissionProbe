package com.calculation.tipcalculation.domain.usecase.external.export_excel

import android.content.Context
import com.calculation.tipcalculation.domain.model.ExternalResultHistory

class ExportExternalResultToExcelUseCase(
    private val exporter: ExternalExcelExporter
) {
    operator fun invoke(context: Context, data: ExternalResultHistory) {
        exporter.export(context, data)
    }
}
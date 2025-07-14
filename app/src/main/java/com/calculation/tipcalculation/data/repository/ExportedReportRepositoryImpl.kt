package com.calculation.tipcalculation.data.repository

import android.content.Context
import com.calculation.tipcalculation.data.local.ExportedReportsStore
import com.calculation.tipcalculation.domain.repository.ExportedReportRepository

class ExportedReportRepositoryImpl(
    private val store: ExportedReportsStore
) : ExportedReportRepository {
    override fun getSavedTimestamps(): Set<String> = store.getTimestamps()
    override fun addTimestamp(timestamp: String) = store.addTimestamp(timestamp)
    override fun removeTimestamp(timestamp: String) = store.removeTimestamp(timestamp)
    override fun invalidateDeletedReports(context: Context) = store.invalidateDeletedReports(context)
}

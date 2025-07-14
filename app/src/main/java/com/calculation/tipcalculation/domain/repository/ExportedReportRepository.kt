package com.calculation.tipcalculation.domain.repository

import android.content.Context

interface ExportedReportRepository {
    fun getSavedTimestamps(): Set<String>
    fun addTimestamp(timestamp: String)
    fun removeTimestamp(timestamp: String)
    fun invalidateDeletedReports(context: Context)
}
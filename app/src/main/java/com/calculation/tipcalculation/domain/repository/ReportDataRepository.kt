package com.calculation.tipcalculation.domain.repository

import com.calculation.tipcalculation.domain.model.ReportData

interface ReportDataRepository {
    suspend fun insert(data: ReportData)
    suspend fun getAll(): List<ReportData>
    suspend fun find(data: ReportData): ReportData?
    suspend fun delete(data: ReportData)
}

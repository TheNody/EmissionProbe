package com.calculation.tipcalculation.data.repository

import com.calculation.tipcalculation.data.local.dao.ReportDataDao
import com.calculation.tipcalculation.data.local.entity.ReportDataEntity
import com.calculation.tipcalculation.domain.model.ReportData
import com.calculation.tipcalculation.domain.repository.ReportDataRepository

class ReportDataRepositoryImpl(
    private val dao: ReportDataDao
) : ReportDataRepository {

    override suspend fun insert(data: ReportData) {
        dao.insert(data.toEntity())
    }

    override suspend fun getAll(): List<ReportData> {
        return dao.getAllReportData().map { it.toDomain() }
    }

    override suspend fun find(data: ReportData): ReportData? {
        return dao.findReportData(
            data.patm,
            data.tsr,
            data.tasp,
            data.plsr,
            data.measurementCount,
            data.averageSpeed,
            data.calculatedTip,
            data.firstSuitableTip
        )?.toDomain()
    }

    override suspend fun delete(data: ReportData) {
        dao.delete(data.toEntity())
    }

    private fun ReportDataEntity.toDomain(): ReportData =
        ReportData(id, title, patm, tsr, tasp, plsr, measurementCount, averageSpeed, calculatedTip, firstSuitableTip, sko)

    private fun ReportData.toEntity(): ReportDataEntity =
        ReportDataEntity(id, title, patm, tsr, tasp, plsr, measurementCount, averageSpeed, calculatedTip, firstSuitableTip, sko)
}

package com.calculation.tipcalculation.db_Main.historyData

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReportDataRepository(private val reportDataDao: ReportDataDao) {

    suspend fun insert(reportData: ReportDataEntity) {
        withContext(Dispatchers.IO) {
            reportDataDao.insert(reportData)
        }
    }

    suspend fun getAllReportData(): List<ReportDataEntity> {
        return withContext(Dispatchers.IO) {
            reportDataDao.getAllReportData()
        }
    }

    suspend fun findReportData(reportData: ReportDataEntity): ReportDataEntity? {
        return withContext(Dispatchers.IO) {
            reportDataDao.findReportData(
                reportData.patm,
                reportData.tsr,
                reportData.tasp,
                reportData.plsr,
                reportData.measurementCount,
                reportData.averageSpeed,
                reportData.calculatedTip,
                reportData.firstSuitableTip
            )
        }
    }

    suspend fun delete(reportData: ReportDataEntity) {
        withContext(Dispatchers.IO) {
            reportDataDao.delete(reportData)
        }
    }
}
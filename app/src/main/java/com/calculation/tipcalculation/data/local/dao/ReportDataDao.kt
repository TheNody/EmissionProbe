package com.calculation.tipcalculation.data.local.dao

import androidx.room.*
import com.calculation.tipcalculation.data.local.entity.ReportDataEntity

@Dao
interface ReportDataDao {
    @Insert
    suspend fun insert(reportData: ReportDataEntity)

    @Query("""
        SELECT * FROM report_data 
        WHERE patm = :patm AND tsr = :tsr AND tasp = :tasp AND plsr = :plsr 
        AND measurementCount = :measurementCount 
        AND averageSpeed = :averageSpeed 
        AND calculatedTip = :calculatedTip 
        AND firstSuitableTip = :firstSuitableTip 
        LIMIT 1
    """)
    suspend fun findReportData(
        patm: Double,
        tsr: Double,
        tasp: Double,
        plsr: Double,
        measurementCount: Int,
        averageSpeed: Double,
        calculatedTip: Double,
        firstSuitableTip: Double
    ): ReportDataEntity?

    @Query("SELECT * FROM report_data")
    suspend fun getAllReportData(): List<ReportDataEntity>

    @Delete
    suspend fun delete(reportData: ReportDataEntity)
}

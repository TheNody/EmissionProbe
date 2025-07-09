package com.calculation.tipcalculation.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "report_data")
data class ReportDataEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val patm: Double,
    val tsr: Double,
    val tasp: Double,
    val plsr: Double,
    val measurementCount: Int,
    val averageSpeed: Double,
    val calculatedTip: Double,
    val firstSuitableTip: Double,
    val sko: Double
)

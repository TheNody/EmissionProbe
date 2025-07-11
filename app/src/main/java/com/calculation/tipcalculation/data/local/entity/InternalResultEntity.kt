package com.calculation.tipcalculation.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "internal_result_history")
data class InternalResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val patm: Double,
    val tsr: Double,
    val tasp: Double,
    val plsr: Double,
    val preom: Double,
    val speeds: String,
    val averageSpeed: Double,
    val averageTip: Double,
    val selectedTip: Double,
    val vp: Double,
    val timestamp: String
)

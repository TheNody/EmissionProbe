package com.calculation.tipcalculation.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "external_result_history")
data class ExternalResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val patm: Double,
    val plsr: Double,
    val preom: Double,
    val tsr: Double,
    val tasp: Double,
    val speeds: String,
    val srznach: Double,
    val sigma: Double,
    val sko: Double,
    val average: Double,
    val calculatedTip: Double,
    val tipSize: Double,
    val aspUsl: Double,
    val result: Double,
    val aspUsl1: Double,
    val duslov1: Double,
    val vibrNak: Double,
    val dreal: Double,
    val vsp2: Double,
    val closestDiameter: Double,
    val firstSuitableDiameter: Double,
    val selectedVp: Double,
    val checkedDiameters: String,
    val timestamp: String
)

package com.calculation.tipcalculation.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rectangular_result_history")
data class RectangularResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val a: Double,
    val b: Double,
    val l: Double,
    val de: Double,
    val lOverDe: Double,
    val lz: Double,
    val totalPoints: Int,
    val na: Int,
    val nb: Int,
    val aspectRatioCategory: String,
    val kiValues: String?,
    val timestamp: String
)

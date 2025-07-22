package com.calculation.tipcalculation.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "round_result_history")
data class RoundResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val d: Double,
    val de: Double,
    val l: Double,
    val lOverDe: Double,
    val lz: Double,
    val totalPoints: Int,
    val diameterPoints: Int,
    val kiValues: String?,
    val timestamp: String
)

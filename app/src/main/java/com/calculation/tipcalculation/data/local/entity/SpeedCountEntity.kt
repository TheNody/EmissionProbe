package com.calculation.tipcalculation.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "speed_table")
data class SpeedCountEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val speedCount: Int
)

package com.calculation.tipcalculation.db_Main.speedCount

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "speed_table")
data class SpeedCount(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val speedCount: Int
)

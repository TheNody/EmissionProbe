package com.calculation.tipcalculation.db_Main.measurementCount

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "measurement_count")
data class MeasurementCount(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val value: Int
)

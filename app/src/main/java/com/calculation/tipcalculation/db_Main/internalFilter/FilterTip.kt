package com.calculation.tipcalculation.db_Main.internalFilter

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "filter_tips")
data class FilterTip(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val value: Double
)

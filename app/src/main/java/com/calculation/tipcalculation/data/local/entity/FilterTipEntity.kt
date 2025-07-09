package com.calculation.tipcalculation.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "filter_tips")
data class FilterTipEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val value: Double
)

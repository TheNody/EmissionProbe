package com.calculation.tipcalculation.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "external_filter_tips")
data class ExternalFilterTipEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val value: Double
)
package com.calculation.tipcalculation.db_Main.externalFilter

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "external_filter_tips")
data class ExternalFilterTip(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val value: Double
)

package com.calculation.tipcalculation.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.calculation.tipcalculation.data.local.dao.*
import com.calculation.tipcalculation.data.local.entity.*

@Database(
    entities = [
        FilterTipEntity::class,
        ExternalFilterTipEntity::class,
        SpeedCountEntity::class,
        ReportDataEntity::class,
        InternalResultEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun filterTipDao(): FilterTipDao
    abstract fun externalFilterTipDao(): ExternalFilterTipDao
    abstract fun speedDao(): SpeedDao
    abstract fun reportDataDao(): ReportDataDao
    abstract fun internalResultDao(): InternalResultDao
}
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
        InternalResultEntity::class,
        ExternalResultEntity::class,
        RoundResultEntity::class,
        RectangularResultEntity::class
    ],
    version = 6,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun filterTipDao(): FilterTipDao
    abstract fun externalFilterTipDao(): ExternalFilterTipDao
    abstract fun speedDao(): SpeedDao
    abstract fun internalResultDao(): InternalResultDao
    abstract fun externalResultDao(): ExternalResultDao
    abstract fun roundResultDao(): RoundResultDao
    abstract fun rectangularResultDao(): RectangularResultDao

}
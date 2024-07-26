package com.calculation.tipcalculation.db_Main

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.calculation.tipcalculation.db_Main.externalFilter.ExternalFilterTip
import com.calculation.tipcalculation.db_Main.externalFilter.ExternalFilterTipDao
import com.calculation.tipcalculation.db_Main.historyData.ReportDataDao
import com.calculation.tipcalculation.db_Main.historyData.ReportDataEntity
import com.calculation.tipcalculation.db_Main.internalFilter.FilterTip
import com.calculation.tipcalculation.db_Main.internalFilter.FilterTipDao
import com.calculation.tipcalculation.db_Main.speedCount.SpeedCount
import com.calculation.tipcalculation.db_Main.speedCount.SpeedDao

@Database(entities = [FilterTip::class, ExternalFilterTip::class, SpeedCount::class, ReportDataEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun filterTipDao(): FilterTipDao
    abstract fun externalFilterTipDao(): ExternalFilterTipDao
    abstract fun speedDao(): SpeedDao
    abstract fun reportDataDao(): ReportDataDao


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
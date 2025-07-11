package com.calculation.tipcalculation.data.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("""
            CREATE TABLE IF NOT EXISTS internal_result_history (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                patm REAL NOT NULL,
                tsr REAL NOT NULL,
                tasp REAL NOT NULL,
                plsr REAL NOT NULL,
                preom REAL NOT NULL,
                speeds TEXT NOT NULL,
                averageSpeed REAL NOT NULL,
                averageTip REAL NOT NULL,
                selectedTip REAL NOT NULL,
                vp REAL NOT NULL,
                timestamp TEXT NOT NULL
            )
        """.trimIndent())
    }
}

package com.calculation.tipcalculation.db_Main.speedCount

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SpeedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(speedEntity: SpeedCount)

    @Query("SELECT * FROM speed_table LIMIT 1")
    suspend fun getSpeed(): SpeedCount?

    @Query("DELETE FROM speed_table")
    suspend fun deleteAll()
}
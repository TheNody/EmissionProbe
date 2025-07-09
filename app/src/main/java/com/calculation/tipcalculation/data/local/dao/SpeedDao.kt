package com.calculation.tipcalculation.data.local.dao

import androidx.room.*
import com.calculation.tipcalculation.data.local.entity.SpeedCountEntity

@Dao
interface SpeedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(speedEntity: SpeedCountEntity)

    @Update
    suspend fun update(speedEntity: SpeedCountEntity)

    @Query("SELECT * FROM speed_table LIMIT 1")
    suspend fun getSpeed(): SpeedCountEntity?

    @Query("DELETE FROM speed_table")
    suspend fun deleteAll()
}

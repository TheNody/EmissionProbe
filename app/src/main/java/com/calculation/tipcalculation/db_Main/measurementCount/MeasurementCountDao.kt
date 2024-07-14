package com.calculation.tipcalculation.db_Main.measurementCount

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MeasurementCountDao {
    @Query("SELECT * FROM measurement_count LIMIT 1")
    fun getMeasurementCount(): Flow<MeasurementCount?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(measurementCount: MeasurementCount)

    @Delete
    suspend fun delete(measurementCount: MeasurementCount)
}
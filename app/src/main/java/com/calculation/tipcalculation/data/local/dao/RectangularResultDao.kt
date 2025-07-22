package com.calculation.tipcalculation.data.local.dao

import androidx.room.*
import com.calculation.tipcalculation.data.local.entity.RectangularResultEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RectangularResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(result: RectangularResultEntity)

    @Query("SELECT * FROM rectangular_result_history ORDER BY timestamp DESC")
    fun getAllFlow(): Flow<List<RectangularResultEntity>>

    @Delete
    suspend fun delete(result: RectangularResultEntity)

    @Query("DELETE FROM rectangular_result_history")
    suspend fun deleteAll()
}

package com.calculation.tipcalculation.data.local.dao

import androidx.room.*
import com.calculation.tipcalculation.data.local.entity.RoundResultEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RoundResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(result: RoundResultEntity)

    @Query("SELECT * FROM round_result_history ORDER BY timestamp DESC")
    fun getAllFlow(): Flow<List<RoundResultEntity>>

    @Delete
    suspend fun delete(result: RoundResultEntity)

    @Query("DELETE FROM round_result_history")
    suspend fun deleteAll()
}

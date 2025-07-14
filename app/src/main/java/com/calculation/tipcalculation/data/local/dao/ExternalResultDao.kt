package com.calculation.tipcalculation.data.local.dao

import androidx.room.*
import com.calculation.tipcalculation.data.local.entity.ExternalResultEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExternalResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(result: ExternalResultEntity)

    @Query("SELECT * FROM external_result_history ORDER BY timestamp DESC")
    fun getAllFlow(): Flow<List<ExternalResultEntity>>

    @Delete
    suspend fun delete(result: ExternalResultEntity)

    @Query("DELETE FROM external_result_history")
    suspend fun deleteAll()
}
package com.calculation.tipcalculation.data.local.dao

import androidx.room.*
import com.calculation.tipcalculation.data.local.entity.InternalResultEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InternalResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(result: InternalResultEntity)

    @Query("SELECT * FROM internal_result_history ORDER BY timestamp DESC")
    fun getAllFlow(): Flow<List<InternalResultEntity>>

    @Delete
    suspend fun delete(result: InternalResultEntity)

    @Query("DELETE FROM internal_result_history")
    suspend fun deleteAll()
}
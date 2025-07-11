package com.calculation.tipcalculation.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.calculation.tipcalculation.data.local.entity.ExternalFilterTipEntity

@Dao
interface ExternalFilterTipDao {
    @Query("SELECT * FROM external_filter_tips")
    suspend fun getAllSync(): List<ExternalFilterTipEntity>

    @Query("SELECT * FROM external_filter_tips")
    fun getAll(): LiveData<List<ExternalFilterTipEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(externalFilterTip: ExternalFilterTipEntity)

    @Delete
    suspend fun delete(externalFilterTip: ExternalFilterTipEntity)
}

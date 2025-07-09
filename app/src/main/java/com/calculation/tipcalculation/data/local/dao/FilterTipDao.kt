package com.calculation.tipcalculation.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.calculation.tipcalculation.data.local.entity.FilterTipEntity

@Dao
interface FilterTipDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(filterTip: FilterTipEntity)

    @Delete
    suspend fun delete(filterTip: FilterTipEntity)

    @Query("SELECT * FROM filter_tips")
    fun getAll(): LiveData<List<FilterTipEntity>>

    @Query("SELECT * FROM filter_tips")
    fun getAllSync(): List<FilterTipEntity>
}

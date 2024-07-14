package com.calculation.tipcalculation.db_Main.internalFilter

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FilterTipDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(filterTip: FilterTip)

    @Delete
    suspend fun delete(filterTip: FilterTip)

    @Query("SELECT * FROM filter_tips")
    fun getAll(): LiveData<List<FilterTip>>

    @Query("SELECT * FROM filter_tips")
    fun getAllSync(): List<FilterTip>
}
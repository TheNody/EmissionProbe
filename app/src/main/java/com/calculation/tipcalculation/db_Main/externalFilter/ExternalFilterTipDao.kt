package com.calculation.tipcalculation.db_Main.externalFilter

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ExternalFilterTipDao {
    @Query("SELECT * FROM external_filter_tips")
    fun getAll(): LiveData<List<ExternalFilterTip>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(externalFilterTip: ExternalFilterTip)

    @Delete
    suspend fun delete(externalFilterTip: ExternalFilterTip)
}
package com.calculation.tipcalculation.domain.repository

import androidx.lifecycle.LiveData
import com.calculation.tipcalculation.domain.model.FilterTip

interface FilterTipRepository {
    fun getAll(): LiveData<List<FilterTip>>
    fun getAllSync(): List<FilterTip>
    suspend fun insert(tip: FilterTip)
    suspend fun delete(tip: FilterTip)
}

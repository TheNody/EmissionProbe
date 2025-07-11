package com.calculation.tipcalculation.domain.repository

import androidx.lifecycle.LiveData
import com.calculation.tipcalculation.domain.model.ExternalFilterTip

interface ExternalFilterTipRepository {
    fun getAll(): LiveData<List<ExternalFilterTip>>
    suspend fun getAllSync(): List<ExternalFilterTip>
    suspend fun insert(tip: ExternalFilterTip)
    suspend fun delete(tip: ExternalFilterTip)
}

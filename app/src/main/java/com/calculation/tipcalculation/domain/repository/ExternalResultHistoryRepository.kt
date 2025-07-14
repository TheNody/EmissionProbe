package com.calculation.tipcalculation.domain.repository

import com.calculation.tipcalculation.domain.model.ExternalResultHistory
import kotlinx.coroutines.flow.Flow

interface ExternalResultHistoryRepository {
    suspend fun insert(result: ExternalResultHistory)
    fun getAllFlow(): Flow<List<ExternalResultHistory>>
    suspend fun delete(result: ExternalResultHistory)
    suspend fun deleteAll()
}
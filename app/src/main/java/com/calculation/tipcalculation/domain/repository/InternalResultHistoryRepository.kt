package com.calculation.tipcalculation.domain.repository

import com.calculation.tipcalculation.domain.model.InternalResultHistory
import kotlinx.coroutines.flow.Flow

interface InternalResultHistoryRepository {
    suspend fun insert(result: InternalResultHistory)
    fun getAllFlow(): Flow<List<InternalResultHistory>>
    suspend fun delete(result: InternalResultHistory)
    suspend fun deleteAll()
}
package com.calculation.tipcalculation.domain.repository

import com.calculation.tipcalculation.domain.model.RoundResultHistory
import kotlinx.coroutines.flow.Flow

interface RoundResultHistoryRepository {
    suspend fun insert(result: RoundResultHistory)
    fun getAllFlow(): Flow<List<RoundResultHistory>>
    suspend fun delete(result: RoundResultHistory)
    suspend fun deleteAll()
}

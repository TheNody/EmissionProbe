package com.calculation.tipcalculation.domain.repository

import com.calculation.tipcalculation.domain.model.InternalResultHistory

interface InternalResultHistoryRepository {
    suspend fun insert(result: InternalResultHistory)
    suspend fun getAll(): List<InternalResultHistory>
    suspend fun delete(result: InternalResultHistory)
    suspend fun deleteAll()
}

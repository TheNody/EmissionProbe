package com.calculation.tipcalculation.domain.repository

import com.calculation.tipcalculation.domain.model.ExternalResultHistory

interface ExternalResultHistoryRepository {
    suspend fun insert(result: ExternalResultHistory)
    suspend fun getAll(): List<ExternalResultHistory>
    suspend fun delete(result: ExternalResultHistory)
    suspend fun deleteAll()
}

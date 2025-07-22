package com.calculation.tipcalculation.domain.repository

import com.calculation.tipcalculation.domain.model.RectangularResultHistory
import kotlinx.coroutines.flow.Flow

interface RectangularResultHistoryRepository {
    suspend fun insert(result: RectangularResultHistory)
    fun getAllFlow(): Flow<List<RectangularResultHistory>>
    suspend fun delete(result: RectangularResultHistory)
    suspend fun deleteAll()
}

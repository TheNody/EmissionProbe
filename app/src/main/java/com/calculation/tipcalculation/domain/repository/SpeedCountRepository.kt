package com.calculation.tipcalculation.domain.repository

import com.calculation.tipcalculation.domain.model.SpeedCount

interface SpeedCountRepository {
    suspend fun insert(speed: SpeedCount)
    suspend fun get(): SpeedCount?
    suspend fun deleteAll()
    suspend fun update(speed: SpeedCount)
}
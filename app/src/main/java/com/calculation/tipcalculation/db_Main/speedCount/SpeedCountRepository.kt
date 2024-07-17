package com.calculation.tipcalculation.db_Main.speedCount

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SpeedCountRepository(private val speedDao: SpeedDao) {

    val measurementCount: Flow<SpeedCount?> = flow {
        emit(speedDao.getSpeed())
    }

    suspend fun insert(speedEntity: SpeedCount) {
        speedDao.insert(speedEntity)
    }

    suspend fun deleteAll() {
        speedDao.deleteAll()
    }

    suspend fun getSpeed(): SpeedCount? {
        return speedDao.getSpeed()
    }
}
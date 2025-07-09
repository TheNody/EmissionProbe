package com.calculation.tipcalculation.data.repository

import com.calculation.tipcalculation.data.local.dao.SpeedDao
import com.calculation.tipcalculation.data.local.entity.SpeedCountEntity
import com.calculation.tipcalculation.domain.model.SpeedCount
import com.calculation.tipcalculation.domain.repository.SpeedCountRepository

class SpeedCountRepositoryImpl(
    private val speedDao: SpeedDao
) : SpeedCountRepository {

    override suspend fun insert(speed: SpeedCount) {
        speedDao.insert(SpeedCountEntity(speed.id, speed.speedCount))
    }

    override suspend fun get(): SpeedCount? {
        return speedDao.getSpeed()?.let {
            SpeedCount(it.id, it.speedCount)
        }
    }

    override suspend fun deleteAll() {
        speedDao.deleteAll()
    }

    override suspend fun update(speed: SpeedCount) {
        speedDao.update(SpeedCountEntity(speed.id, speed.speedCount))
    }
}

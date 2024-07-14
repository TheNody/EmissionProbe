package com.calculation.tipcalculation.db_Main.measurementCount

import kotlinx.coroutines.flow.Flow

class MeasurementCountRepository(private val measurementCountDao: MeasurementCountDao) {

    val measurementCount: Flow<MeasurementCount?> = measurementCountDao.getMeasurementCount()

    suspend fun insert(measurementCount: MeasurementCount) {
        measurementCountDao.insert(measurementCount)
    }

    suspend fun delete(measurementCount: MeasurementCount) {
        measurementCountDao.delete(measurementCount)
    }
}
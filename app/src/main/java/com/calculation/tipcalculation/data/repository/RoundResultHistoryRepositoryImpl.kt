package com.calculation.tipcalculation.data.repository

import com.calculation.tipcalculation.data.local.dao.RoundResultDao
import com.calculation.tipcalculation.data.local.entity.RoundResultEntity
import com.calculation.tipcalculation.domain.model.RoundResultHistory
import com.calculation.tipcalculation.domain.repository.RoundResultHistoryRepository
import com.calculation.tipcalculation.utils.GOSTMeasurementTable
import com.calculation.tipcalculation.utils.MeasurementRule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoundResultHistoryRepositoryImpl(
    private val dao: RoundResultDao
) : RoundResultHistoryRepository {

    override suspend fun insert(result: RoundResultHistory) {
        dao.insert(result.toEntity())
    }

    override fun getAllFlow(): Flow<List<RoundResultHistory>> {
        return dao.getAllFlow().map { list ->
            list.mapNotNull { it.toDomain() }
        }
    }

    override suspend fun delete(result: RoundResultHistory) {
        dao.delete(result.toEntity())
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }

    private fun RoundResultHistory.toEntity(): RoundResultEntity {
        return RoundResultEntity(
            id = id,
            d = d,
            de = de,
            l = l,
            lOverDe = lOverDe,
            lz = lz,
            totalPoints = rule.totalPoints,
            diameterPoints = rule.diameterPoints,
            kiValues = ki?.joinToString(","),
            timestamp = timestamp
        )
    }

    private fun RoundResultEntity.toDomain(): RoundResultHistory? {
        val rule: MeasurementRule = GOSTMeasurementTable.rules.find {
            it.totalPoints == totalPoints && it.diameterPoints == diameterPoints
        } ?: return null

        return RoundResultHistory(
            id = id,
            d = d,
            de = de,
            l = l,
            lOverDe = lOverDe,
            lz = lz,
            rule = rule,
            ki = kiValues?.split(",")?.mapNotNull { it.toDoubleOrNull() },
            timestamp = timestamp
        )
    }
}
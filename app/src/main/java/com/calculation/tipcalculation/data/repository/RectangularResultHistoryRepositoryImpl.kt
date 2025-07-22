package com.calculation.tipcalculation.data.repository

import com.calculation.tipcalculation.data.local.dao.RectangularResultDao
import com.calculation.tipcalculation.data.local.entity.RectangularResultEntity
import com.calculation.tipcalculation.domain.model.RectangularResultHistory
import com.calculation.tipcalculation.domain.repository.RectangularResultHistoryRepository
import com.calculation.tipcalculation.utils.RectangularMeasurementRules
import com.calculation.tipcalculation.utils.AspectRatioCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RectangularResultHistoryRepositoryImpl(
    private val dao: RectangularResultDao
) : RectangularResultHistoryRepository {

    override suspend fun insert(result: RectangularResultHistory) {
        dao.insert(result.toEntity())
    }

    override fun getAllFlow(): Flow<List<RectangularResultHistory>> {
        return dao.getAllFlow().map { list ->
            list.mapNotNull { it.toDomain() }
        }
    }

    override suspend fun delete(result: RectangularResultHistory) {
        dao.delete(result.toEntity())
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }

    private fun RectangularResultHistory.toEntity(): RectangularResultEntity {
        return RectangularResultEntity(
            id = id,
            a = a,
            b = b,
            l = l,
            de = de,
            lOverDe = lOverDe,
            lz = lz,
            totalPoints = rule.totalPoints,
            na = rule.na,
            nb = rule.nb,
            aspectRatioCategory = rule.aspectCategory.name,
            kiValues = ki?.joinToString(","),
            timestamp = timestamp
        )
    }

    private fun RectangularResultEntity.toDomain(): RectangularResultHistory? {
        val category = AspectRatioCategory.entries.find { it.name == aspectRatioCategory } ?: return null

        val rule = RectangularMeasurementRules.rules.find {
            it.totalPoints == totalPoints && it.na == na && it.nb == nb && it.aspectCategory == category
        } ?: return null

        return RectangularResultHistory(
            id = id,
            a = a,
            b = b,
            l = l,
            de = de,
            lOverDe = lOverDe,
            lz = lz,
            rule = rule,
            ki = kiValues?.split(",")?.mapNotNull { it.toDoubleOrNull() },
            timestamp = timestamp
        )
    }
}

package com.calculation.tipcalculation.data.repository

import com.calculation.tipcalculation.data.local.dao.InternalResultDao
import com.calculation.tipcalculation.data.local.entity.InternalResultEntity
import com.calculation.tipcalculation.domain.model.InternalResultHistory
import com.calculation.tipcalculation.domain.repository.InternalResultHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class InternalResultHistoryRepositoryImpl(
    private val dao: InternalResultDao
) : InternalResultHistoryRepository {

    override suspend fun insert(result: InternalResultHistory) {
        dao.insert(result.toEntity())
    }

    override fun getAllFlow(): Flow<List<InternalResultHistory>> {
        return dao.getAllFlow().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun delete(result: InternalResultHistory) {
        dao.delete(result.toEntity())
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }

    private fun InternalResultHistory.toEntity(): InternalResultEntity {
        return InternalResultEntity(
            id = id,
            patm = patm,
            tsr = tsr,
            tasp = tasp,
            plsr = plsr,
            preom = preom,
            speeds = speeds.joinToString(";"),
            averageSpeed = averageSpeed,
            averageTip = averageTip,
            selectedTip = selectedTip,
            vp = vp,
            timestamp = timestamp
        )
    }

    private fun InternalResultEntity.toDomain(): InternalResultHistory {
        return InternalResultHistory(
            id = id,
            patm = patm,
            tsr = tsr,
            tasp = tasp,
            plsr = plsr,
            preom = preom,
            speeds = speeds.split(";").mapNotNull { it.toDoubleOrNull() },
            averageSpeed = averageSpeed,
            averageTip = averageTip,
            selectedTip = selectedTip,
            vp = vp,
            timestamp = timestamp
        )
    }
}
package com.calculation.tipcalculation.data.repository

import com.calculation.tipcalculation.data.local.dao.ExternalResultDao
import com.calculation.tipcalculation.data.local.entity.ExternalResultEntity
import com.calculation.tipcalculation.domain.model.ExternalResultHistory
import com.calculation.tipcalculation.domain.repository.ExternalResultHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExternalResultHistoryRepositoryImpl(
    private val dao: ExternalResultDao
) : ExternalResultHistoryRepository {

    override suspend fun insert(result: ExternalResultHistory) {
        dao.insert(result.toEntity())
    }

    override fun getAllFlow(): Flow<List<ExternalResultHistory>> {
        return dao.getAllFlow().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun delete(result: ExternalResultHistory) {
        dao.delete(result.toEntity())
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }

    private fun ExternalResultHistory.toEntity(): ExternalResultEntity {
        return ExternalResultEntity(
            id = id,
            patm = patm,
            plsr = plsr,
            preom = preom,
            tsr = tsr,
            tasp = tasp,
            speeds = speeds.joinToString(";"),
            srznach = srznach,
            sigma = sigma,
            sko = sko,
            average = average,
            calculatedTip = calculatedTip,
            tipSize = tipSize,
            aspUsl = aspUsl,
            result = result,
            aspUsl1 = aspUsl1,
            duslov1 = duslov1,
            vibrNak = vibrNak,
            dreal = dreal,
            vsp2 = vsp2,
            closestDiameter = closestDiameter,
            firstSuitableDiameter = firstSuitableDiameter,
            selectedVp = selectedVp,
            checkedDiameters = checkedDiametersList.joinToString(";") { "${it.first},${it.second}" },
            timestamp = timestamp
        )
    }

    private fun ExternalResultEntity.toDomain(): ExternalResultHistory {
        return ExternalResultHistory(
            id = id,
            patm = patm,
            plsr = plsr,
            preom = preom,
            tsr = tsr,
            tasp = tasp,
            speeds = speeds.split(";").mapNotNull { it.toDoubleOrNull() },
            srznach = srznach,
            sigma = sigma,
            sko = sko,
            average = average,
            calculatedTip = calculatedTip,
            tipSize = tipSize,
            aspUsl = aspUsl,
            result = result,
            aspUsl1 = aspUsl1,
            duslov1 = duslov1,
            vibrNak = vibrNak,
            dreal = dreal,
            vsp2 = vsp2,
            closestDiameter = closestDiameter,
            firstSuitableDiameter = firstSuitableDiameter,
            selectedVp = selectedVp,
            checkedDiametersList = checkedDiameters
                .split(";")
                .mapNotNull {
                    val parts = it.split(",")
                    if (parts.size == 2) {
                        val first = parts[0].toDoubleOrNull()
                        val second = parts[1].toDoubleOrNull()
                        if (first != null && second != null) first to second else null
                    } else null
                },
            timestamp = timestamp
        )
    }
}
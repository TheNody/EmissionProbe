package com.calculation.tipcalculation.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.calculation.tipcalculation.data.local.dao.ExternalFilterTipDao
import com.calculation.tipcalculation.data.local.entity.ExternalFilterTipEntity
import com.calculation.tipcalculation.domain.model.ExternalFilterTip
import com.calculation.tipcalculation.domain.repository.ExternalFilterTipRepository

class ExternalFilterTipRepositoryImpl(
    private val dao: ExternalFilterTipDao
) : ExternalFilterTipRepository {

    override fun getAll(): LiveData<List<ExternalFilterTip>> {
        return dao.getAll().map { list ->
            list.map { ExternalFilterTip(it.id, it.value) }
        }
    }

    override suspend fun insert(tip: ExternalFilterTip) {
        dao.insert(ExternalFilterTipEntity(tip.id, tip.value))
    }

    override suspend fun delete(tip: ExternalFilterTip) {
        dao.delete(ExternalFilterTipEntity(tip.id, tip.value))
    }
}

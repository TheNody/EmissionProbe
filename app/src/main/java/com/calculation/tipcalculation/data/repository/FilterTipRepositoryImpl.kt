package com.calculation.tipcalculation.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.calculation.tipcalculation.data.local.dao.FilterTipDao
import com.calculation.tipcalculation.data.local.entity.FilterTipEntity
import com.calculation.tipcalculation.domain.model.FilterTip
import com.calculation.tipcalculation.domain.repository.FilterTipRepository

class FilterTipRepositoryImpl(
    private val dao: FilterTipDao
) : FilterTipRepository {

    override fun getAll(): LiveData<List<FilterTip>> {
        return dao.getAll().map { list ->
            list.map { FilterTip(it.id, it.value) }
        }
    }

    override fun getAllSync(): List<FilterTip> {
        return dao.getAllSync().map { FilterTip(it.id, it.value) }
    }

    override suspend fun insert(tip: FilterTip) {
        dao.insert(FilterTipEntity(tip.id, tip.value))
    }

    override suspend fun delete(tip: FilterTip) {
        dao.delete(FilterTipEntity(tip.id, tip.value))
    }
}

package com.calculation.tipcalculation.db_Main.internalFilter

import androidx.lifecycle.LiveData

class FilterTipRepository(private val filterTipDao: FilterTipDao) {
    val allFilterTips: LiveData<List<FilterTip>> = filterTipDao.getAll()

    suspend fun insert(filterTip: FilterTip) {
        filterTipDao.insert(filterTip)
    }

    suspend fun delete(filterTip: FilterTip) {
        filterTipDao.delete(filterTip)
    }

    fun getAllSync(): List<FilterTip> {
        return filterTipDao.getAllSync()
    }
}
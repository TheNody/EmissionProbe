package com.calculation.tipcalculation.db_Main.externalFilter

import androidx.lifecycle.LiveData

class ExternalFilterTipRepository(private val externalFilterTipDao: ExternalFilterTipDao) {
    val allExternalFilterTips: LiveData<List<ExternalFilterTip>> = externalFilterTipDao.getAll()

    suspend fun insert(externalFilterTip: ExternalFilterTip) {
        externalFilterTipDao.insert(externalFilterTip)
    }

    suspend fun delete(externalFilterTip: ExternalFilterTip) {
        externalFilterTipDao.delete(externalFilterTip)
    }
}
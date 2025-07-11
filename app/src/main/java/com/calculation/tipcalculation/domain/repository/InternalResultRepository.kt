package com.calculation.tipcalculation.domain.repository

import com.calculation.tipcalculation.domain.model.InternalResultData
import kotlinx.coroutines.flow.StateFlow

interface InternalResultRepository {
    fun setResult(data: InternalResultData)
    fun getResult(): StateFlow<InternalResultData?>
    fun clear()
}
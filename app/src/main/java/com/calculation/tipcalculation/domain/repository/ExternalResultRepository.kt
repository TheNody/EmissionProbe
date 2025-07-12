package com.calculation.tipcalculation.domain.repository

import com.calculation.tipcalculation.domain.model.ExternalResultData
import kotlinx.coroutines.flow.StateFlow

interface ExternalResultRepository {
    fun setResult(data: ExternalResultData)
    fun getResult(): StateFlow<ExternalResultData?>
    fun clear()
}

package com.calculation.tipcalculation.domain.repository

import com.calculation.tipcalculation.domain.model.RoundInputData
import kotlinx.coroutines.flow.StateFlow

interface RoundInputRepository {
    fun setInput(data: RoundInputData)
    fun getInput(): StateFlow<RoundInputData?>
    fun clear()
}

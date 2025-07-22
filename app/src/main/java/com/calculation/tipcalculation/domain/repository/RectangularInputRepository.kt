package com.calculation.tipcalculation.domain.repository

import com.calculation.tipcalculation.domain.model.RectangularInputData
import kotlinx.coroutines.flow.StateFlow

interface RectangularInputRepository {
    fun setInput(data: RectangularInputData)
    fun getInput(): StateFlow<RectangularInputData?>
    fun clear()
}

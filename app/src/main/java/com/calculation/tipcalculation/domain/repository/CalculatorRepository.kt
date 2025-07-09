package com.calculation.tipcalculation.domain.repository

import com.calculation.tipcalculation.domain.model.CalculatorResult

interface CalculatorRepository {
    fun evaluate(expression: String): CalculatorResult
}

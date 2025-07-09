package com.calculation.tipcalculation.domain.usecase.calculator

import com.calculation.tipcalculation.domain.repository.CalculatorRepository
import javax.inject.Inject

class EvaluateExpressionUseCase @Inject constructor(
    private val repository: CalculatorRepository
) {
    operator fun invoke(expression: String) = repository.evaluate(expression)
}
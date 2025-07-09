package com.calculation.tipcalculation.data.repository

import com.calculation.tipcalculation.domain.model.CalculatorError
import com.calculation.tipcalculation.domain.model.CalculatorResult
import com.calculation.tipcalculation.domain.repository.CalculatorRepository
import org.mariuszgromada.math.mxparser.Expression
import javax.inject.Inject

class CalculatorRepositoryImpl @Inject constructor() : CalculatorRepository {

    override fun evaluate(expression: String): CalculatorResult {
        return try {
            val cleanExpr = expression
                .replace("×", "*")
                .replace("÷", "/")
                .replace(",", ".")

            val e = Expression(cleanExpr)
            val value = e.calculate()

            if (!e.checkSyntax()) {
                return CalculatorResult(error = CalculatorError.Syntax(e.errorMessage))
            }

            CalculatorResult(result = value.toString())
        } catch (e: Exception) {
            CalculatorResult(error = CalculatorError.Unknown(e.message ?: "Ошибка вычисления"))
        }
    }
}
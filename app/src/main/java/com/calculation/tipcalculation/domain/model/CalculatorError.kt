package com.calculation.tipcalculation.domain.model

sealed class CalculatorError {
    object DivisionByZero : CalculatorError()
    object InvalidExpression : CalculatorError()
    object TooManyDigits : CalculatorError()
    object InvalidStart : CalculatorError()
    data class Syntax(val message: String) : CalculatorError()
    data class Unknown(val message: String) : CalculatorError()
}
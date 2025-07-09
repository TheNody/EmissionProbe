package com.calculation.tipcalculation.utils

import android.content.Context
import android.widget.Toast
import com.calculation.tipcalculation.domain.model.CalculatorError

object ErrorMessageMapper {
    fun map(error: CalculatorError): String {
        return when (error) {
            is CalculatorError.DivisionByZero -> "Нельзя делить на ноль"
            is CalculatorError.InvalidExpression -> "Некорректное выражение"
            is CalculatorError.TooManyDigits -> "Нельзя ввести больше 15 цифр"
            is CalculatorError.InvalidStart -> "Выражение не может начинаться с оператора"
            is CalculatorError.Syntax -> "Ошибка: ${error.message}"
            is CalculatorError.Unknown -> "Ошибка: ${error.message}"
        }
    }

    fun showToast(context: Context, error: CalculatorError) {
        Toast.makeText(context, map(error), Toast.LENGTH_SHORT).show()
    }
}
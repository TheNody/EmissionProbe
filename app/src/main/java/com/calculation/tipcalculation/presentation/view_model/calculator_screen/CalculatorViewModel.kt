package com.calculation.tipcalculation.presentation.view_model.calculator_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.calculation.tipcalculation.domain.model.CalculatorError
import com.calculation.tipcalculation.domain.usecase.calculator.EvaluateExpressionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val evaluateExpressionUseCase: EvaluateExpressionUseCase
) : ViewModel() {

    private val _expression = MutableStateFlow("")
    val expression: StateFlow<String> = _expression

    private val _result = MutableStateFlow("")
    val result: StateFlow<String> = _result

    private val _error = MutableStateFlow<CalculatorError?>(null)
    val error: StateFlow<CalculatorError?> = _error

    fun onKeyClick(key: String) {
        val operators: List<String> = listOf("+", "−", "×", "÷", "%")

        when (key) {
            "=" -> {
                if (_expression.value.isValidForEvaluation()) {
                    _expression.value = _result.value
                    _result.value = ""
                }
            }

            "C" -> clear()

            else -> {
                val current = _expression.value

                if (current.isEmpty() && key in operators) {
                    _error.value = CalculatorError.InvalidStart
                    return
                }

                if (key == ",") {
                    val lastNumber = current.takeLastWhile { it.isDigit() || it == ',' || it == '.' }
                    if (lastNumber.contains(',') || lastNumber.contains('.')) return
                }

                if (key.all { it.isDigit() }) {
                    val lastChar = current.lastOrNull()
                    val isAppendingToNumber = lastChar?.isDigit() == true || lastChar == ',' || lastChar == '.'

                    Log.d("CalculatorDebug", "Key: $key, Current: $current, LastChar: $lastChar, Appending: $isAppendingToNumber")


                    if (isAppendingToNumber) {
                        val lastNumber = current.extractLastNumber()

                        if (lastNumber.contains('E')) {
                        } else {
                            val digitCount = lastNumber.count { it.isDigit() }
                            if (digitCount >= 15) {
                                _error.value = CalculatorError.TooManyDigits
                                return
                            }
                        }
                    }
                }

                if (key in operators) {
                    if (current.isNotEmpty() && current.last().toString() in operators) {
                        if (current.last().toString() == key) return
                        _expression.value = current.dropLast(1) + key
                        return
                    }
                }

                _expression.value += key

                if (_expression.value.isValidForEvaluation()) {
                    evaluateCurrent()
                } else {
                    _result.value = ""
                }
            }
        }
    }

    fun backspace() {
        if (_expression.value.isNotEmpty()) {
            _expression.value = _expression.value.dropLast(1)
            if (_expression.value.isValidForEvaluation()) {
                evaluateCurrent()
            } else {
                _result.value = ""
            }
        }
    }

    private fun evaluateCurrent() {
        val expr = _expression.value

        val trimmed = if (expr.lastOrNull()?.toString() in listOf("+", "−", "×", "÷", "%")) {
            expr.dropLast(1)
        } else {
            expr
        }

        val result = evaluateExpressionUseCase(trimmed)

        if (result.error != null) {
            _error.value = result.error
            _result.value = ""
        } else {
            _result.value = result.result ?: ""
        }
    }

    fun clear() {
        _expression.value = ""
        _result.value = ""
    }

    fun consumeError() {
        _error.value = null
    }

    private fun String.isValidForEvaluation(): Boolean {
        return this.any { it.isDigit() }
    }

    private fun String.extractLastNumber(): String {
        val operators = listOf('+', '−', '×', '÷', '%')
        val index = this.indexOfLast { it in operators }
        return if (index == -1) this else this.substring(index + 1)
    }
}
package com.calculation.tipcalculation.presentation.ui.internal_screen.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class InputFieldState(
    val placeholder: String
) {
    var value by mutableStateOf("")
}

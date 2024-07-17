package com.calculation.tipcalculation.model

data class Field(
    val label: String,
    val value: String,
    val onValueChange: (String) -> Unit
)

package com.calculation.tipcalculation.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp

@Composable
fun InputFieldWithSpacer(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    focusRequester: FocusRequester,
    onDone: () -> Unit
) {
    InnerShadowBoxWithNegative(
        value = value,
        onValueChange = onValueChange,
        placeholder = placeholder,
        modifier = Modifier.focusRequester(focusRequester),
        onDone = onDone
    )
    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
fun InputFieldWithSpacerPositiveOnly(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    focusRequester: FocusRequester,
    onDone: () -> Unit
) {
    InnerShadowBox(
        value = value,
        onValueChange = onValueChange,
        placeholder = placeholder,
        modifier = Modifier.focusRequester(focusRequester),
        onDone = onDone
    )
    Spacer(modifier = Modifier.height(12.dp))
}
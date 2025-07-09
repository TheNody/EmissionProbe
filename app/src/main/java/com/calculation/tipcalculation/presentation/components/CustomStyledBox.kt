package com.calculation.tipcalculation.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val DarkFill = Color(0xFF242C3B)
val InnerShadowColor = Color(0xFF191E29)
val DropShadowColor = Color(0xFF2C3648)

@Composable
fun InnerShadowBox(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    height: Dp = 60.dp,
    cornerRadius: Dp = 8.dp,
    placeholder: String = ""
) {
    val shape = RoundedCornerShape(cornerRadius)

    val strokeBrush = Brush.linearGradient(
        colors = listOf(
            Color.White.copy(alpha = 0.2f),
            Color.Black.copy(alpha = 0.2f)
        ),
        start = Offset.Zero,
        end = Offset.Infinite
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .border(BorderStroke(0.5.dp, strokeBrush), shape = shape)
            .shadow(
                elevation = 10.dp,
                shape = shape,
                ambientColor = DropShadowColor,
                spotColor = DropShadowColor
            )
            .background(DarkFill, shape)
            .innerShadow(
                shape = shape,
                color = InnerShadowColor,
                offsetX = 4.dp,
                offsetY = 10.dp,
                blur = 30.dp
            )
            .padding(horizontal = 16.dp)
    ) {
        TextField(
            value = value,
            onValueChange = {
                if (it.all { char -> char.isDigit() }) {
                    onValueChange(it)
                }
            },
            modifier = Modifier.fillMaxSize(),
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color.LightGray,
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedPlaceholderColor = Color.LightGray,
                unfocusedPlaceholderColor = Color.LightGray
            )
        )
    }
}

@Composable
fun InnerShadowBoxWithButton(
    value: String,
    onValueChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onStartEditing: () -> Unit,
    isConfirmed: Boolean,
    modifier: Modifier = Modifier,
    height: Dp = 60.dp,
    cornerRadius: Dp = 12.dp,
    placeholder: String = ""
) {
    val shape = RoundedCornerShape(cornerRadius)

    val strokeBrush = Brush.linearGradient(
        colors = listOf(
            Color.White.copy(alpha = 0.2f),
            Color.Black.copy(alpha = 0.2f)
        ),
        start = Offset.Zero,
        end = Offset.Infinite
    )

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    var requestFocus by remember { mutableStateOf(false) }

    LaunchedEffect(requestFocus) {
        if (requestFocus) {
            focusRequester.requestFocus()
            requestFocus = false
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .border(BorderStroke(0.5.dp, strokeBrush), shape)
            .shadow(
                elevation = 10.dp,
                shape = shape,
                ambientColor = DropShadowColor,
                spotColor = DropShadowColor
            )
            .background(DarkFill, shape)
            .innerShadow(
                shape = shape,
                color = InnerShadowColor,
                offsetX = 4.dp,
                offsetY = 10.dp,
                blur = 30.dp
            )
            .padding(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = value,
                onValueChange = {
                    if (it.all(Char::isDigit)) {
                        onValueChange(it)
                    }
                },
                modifier = Modifier
                    .weight(0.7f)
                    .fillMaxHeight()
                    .focusRequester(focusRequester),
                placeholder = {
                    Text(
                        text = placeholder,
                        color = Color.LightGray,
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (value.isNotEmpty()) {
                            focusManager.clearFocus()
                            onConfirm()
                        }
                    }
                ),
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    cursorColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedPlaceholderColor = Color.LightGray,
                    unfocusedPlaceholderColor = Color.LightGray
                ),
                readOnly = isConfirmed
            )

            Spacer(modifier = Modifier.width(8.dp))

            GradientConfirmButton(
                isConfirmed = isConfirmed,
                onClick = {
                    if (isConfirmed) {
                        onStartEditing()
                        requestFocus = true
                    } else {
                        if (value.isNotEmpty()) {
                            focusManager.clearFocus()
                            onConfirm()
                        }
                    }
                },
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxHeight()
            )
        }
    }
}

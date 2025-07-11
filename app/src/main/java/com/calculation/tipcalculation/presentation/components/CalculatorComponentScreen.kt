package com.calculation.tipcalculation.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.calculation.tipcalculation.presentation.theme.Typography
import kotlinx.coroutines.delay

@Composable
fun CalculatorKeyButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val isEqual = text == "="
    val backgroundColor = if (isEqual) Color(0xFF4CAF50) else Color(0xFF353F54)
    val textColor = when (text) {
        "C" -> Color(0xFFE57373)
        else -> Color.White
    }

    Box(
        modifier = modifier
            .aspectRatio(1.2f)
            .drawBehind {
                drawRoundRect(
                    color = Color(0xFF10141C),
                    topLeft = Offset(0f, 20f),
                    size = size.copy(height = size.height - 20f),
                    cornerRadius = CornerRadius(5.dp.toPx(), 5.dp.toPx()),
                    alpha = 1f
                )

                drawRoundRect(
                    color = Color(0x802B3445),
                    topLeft = Offset(0f, -20f),
                    size = size,
                    cornerRadius = CornerRadius(5.dp.toPx(), 5.dp.toPx()),
                    alpha = 0.5f
                )

                drawRoundRect(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.2f),
                            Color.Black.copy(alpha = 0.2f)
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(size.width, size.height)
                    ),
                    cornerRadius = CornerRadius(5.dp.toPx()),
                    style = Stroke(width = 1.dp.toPx())
                )
            }
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(5.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            textAlign = TextAlign.Center,
            style = Typography.bodyLarge.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun CalculatorIconButton(
    iconResId: Int,
    tint: Color = Color(0xFFD8D8D8),
    background: Color = Color.Transparent,
    onClick: () -> Unit,
    onHold: (() -> Unit)? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = remember { mutableStateOf(false) }

    LaunchedEffect(isPressed.value) {
        if (isPressed.value && onHold != null) {
            delay(500)
            while (isPressed.value) {
                onHold()
                delay(100)
            }
        }
    }

    Box(
        modifier = Modifier
            .size(48.dp)
            .background(background, shape = RoundedCornerShape(8.dp))
            .indication(
                interactionSource = interactionSource,
                indication = ripple(
                    bounded = false,
                    radius = 20.dp,
                    color = Color.White.copy(alpha = 0.3f)
                )
            )
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed.value = true

                        val press = PressInteraction.Press(it)
                        interactionSource.emit(press)

                        onClick()

                        try {
                            awaitRelease()
                        } finally {
                            isPressed.value = false
                            interactionSource.emit(PressInteraction.Release(press))
                        }
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(24.dp)
        )
    }
}


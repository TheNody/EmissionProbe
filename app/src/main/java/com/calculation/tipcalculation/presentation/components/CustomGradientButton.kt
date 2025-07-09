package com.calculation.tipcalculation.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.calculation.tipcalculation.presentation.ui.theme.Typography
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CustomGradientButton(
    modifier: Modifier = Modifier,
    text: String = "Button",
    onClick: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    var isPressed by remember { mutableStateOf(false) }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collectLatest { interaction ->
            isPressed = interaction is PressInteraction.Press
        }
    }

    val gradientColors = if (isPressed) {
        listOf(Color(0xFF363E51), Color(0xFF191E26))
    } else {
        listOf(Color(0xFF353F54), Color(0xFF222834))
    }

    val shadowColor = if (isPressed) {
        Color(0x33667BA5)
    } else {
        Color(0x802B3445)
    }

    val borderAlpha = if (isPressed) 0.2f else 0.1f

    Box(
        modifier = modifier
            .then(
                if (isPressed) Modifier.graphicsLayer(alpha = 0.9f)
                else Modifier
            )
            .shadow(
                elevation = 20.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = shadowColor,
                spotColor = shadowColor
            )
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = gradientColors,
                    startY = 0f,
                    endY = Float.POSITIVE_INFINITY
                )
            )
            .drawBehind {
                drawRoundRect(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.White.copy(alpha = borderAlpha),
                            Color.Black.copy(alpha = 0.05f),
                            Color.Black.copy(alpha = borderAlpha)
                        ),
                        start = Offset.Zero,
                        end = Offset(size.width, size.height)
                    ),
                    cornerRadius = CornerRadius(16.dp.toPx())
                )
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 1.dp, vertical = 40.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = Typography.labelLarge.copy(fontSize = 20.sp),
            color = Color.White,
            textAlign = TextAlign.Center,
            softWrap = true,
            overflow = TextOverflow.Visible,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )
    }
}

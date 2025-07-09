package com.calculation.tipcalculation.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun CustomBackground() {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF242C3B))
    ) {
        val density = LocalDensity.current

        val widthPx = with(density) { maxWidth.toPx() }
        val heightPx = with(density) { maxHeight.toPx() }

        Canvas(modifier = Modifier.fillMaxSize()) {
            val path = Path().apply {
                moveTo(0f, heightPx)
                lineTo(widthPx * 0.8f, heightPx * 0.2f)
                lineTo(widthPx, heightPx * 0.3f)
                lineTo(widthPx, heightPx)
                close()
            }

            drawPath(
                path = path,
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF37B6E9),
                        Color(0xFF4B4CED)
                    ),
                    start = Offset(0f, heightPx),
                    end = Offset(widthPx, 0f)
                )
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp))
        )
    }
}

@Composable
fun CustomBackground2() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF242C3B))
    )
}

package com.calculation.tipcalculation.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun ShadowedCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = modifier
            .graphicsLayer {
                shadowElevation = 0f
                shape = RoundedCornerShape(10.dp)
                clip = false
            }
            .drawBehind {
                drawIntoCanvas { canvas ->
                    val paint = Paint().apply {
                        color = Color(0xFF38445A)
                        asFrameworkPaint().maskFilter =
                            android.graphics.BlurMaskFilter(10f, android.graphics.BlurMaskFilter.Blur.NORMAL)
                    }
                    canvas.drawRoundRect(
                        left = -4f,
                        top = -4f,
                        right = size.width,
                        bottom = size.height,
                        radiusX = 10.dp.toPx(),
                        radiusY = 10.dp.toPx(),
                        paint = paint
                    )
                }

                drawIntoCanvas { canvas ->
                    val paint = Paint().apply {
                        color = Color(0xFF252B39)
                        asFrameworkPaint().maskFilter =
                            android.graphics.BlurMaskFilter(10f, android.graphics.BlurMaskFilter.Blur.NORMAL)
                    }
                    canvas.drawRoundRect(
                        left = 4f,
                        top = 4f,
                        right = size.width + 4f,
                        bottom = size.height + 4f,
                        radiusX = 10.dp.toPx(),
                        radiusY = 10.dp.toPx(),
                        paint = paint
                    )
                }
            }
            .background(Color(0xFF323B4F), shape = RoundedCornerShape(10.dp))
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth(), content = content)
    }
}

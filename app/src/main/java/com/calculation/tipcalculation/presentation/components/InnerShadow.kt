package com.calculation.tipcalculation.presentation.components

import android.graphics.BlurMaskFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.innerShadow(
    shape: Shape,
    color: Color = Color.Black,
    blur: Dp = 4.dp,
    offsetX: Dp = 2.dp,
    offsetY: Dp = 2.dp,
    spread: Dp = 0.dp
): Modifier = this.then(
    Modifier.drawWithContent {
        drawContent()

        val shadowSize = Size(size.width + spread.toPx(), size.height + spread.toPx())
        val outline = shape.createOutline(shadowSize, layoutDirection, this)

        drawIntoCanvas { canvas ->
            val paint = Paint().apply {
                this.color = color
            }

            canvas.saveLayer(size.toRect(), paint)

            canvas.drawOutline(outline, paint)

            paint.asFrameworkPaint().apply {
                xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
                if (blur.toPx() > 0) {
                    maskFilter = BlurMaskFilter(blur.toPx(), BlurMaskFilter.Blur.NORMAL)
                }
            }

            paint.color = Color.Black

            canvas.translate(offsetX.toPx(), offsetY.toPx())
            canvas.drawOutline(outline, paint)

            canvas.restore()
        }
    }
)

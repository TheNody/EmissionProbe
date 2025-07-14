package com.calculation.tipcalculation.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.calculation.tipcalculation.presentation.theme.Typography

@Composable
fun SearchButton(
    icon: ImageVector? = null,
    @DrawableRes iconResId: Int? = null,
    contentDescription: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .graphicsLayer {
                shadowElevation = 20f
                shape = RoundedCornerShape(10.dp)
                clip = false
            }
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(10.dp),
                ambientColor = Color(0x802B3445),
                spotColor = Color(0x802B3445)
            )
            .clip(RoundedCornerShape(10.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF34C8E8),
                        Color(0xFF4E4AF2)
                    )
                )
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        when {
            icon != null -> {
                Icon(
                    imageVector = icon,
                    contentDescription = contentDescription,
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }

            iconResId != null -> {
                Icon(
                    painter = painterResource(id = iconResId),
                    contentDescription = contentDescription,
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}

@Composable
fun GradientConfirmButton(
    modifier: Modifier = Modifier,
    isConfirmed: Boolean,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(12.dp)

    val borderGradient = Brush.linearGradient(
        colors = listOf(Color(0xFF4E4AF2), Color(0xFF34C8E8))
    )

    val borderFadedGradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFF34C8E8).copy(alpha = 0.3f),
            Color(0xFF4E4AF2).copy(alpha = 0.3f)
        )
    )

    val bwGradientBrush = Brush.linearGradient(
        colors = listOf(
            Color.White.copy(alpha = 0.6f),
            Color.Black.copy(alpha = 0.6f)
        )
    )

    val colorfulBackground = Brush.linearGradient(
        colors = listOf(Color(0xFF4E4AF2), Color(0xFF34C8E8))
    )

    val backgroundBrush = if (isConfirmed) bwGradientBrush else colorfulBackground
    val borderBrush = if (isConfirmed) borderFadedGradient else borderGradient
    val buttonText = if (isConfirmed) "Изменить" else "Подвердить"

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(BorderStroke(1.dp, borderBrush), shape)
            .background(brush = backgroundBrush, shape = shape)
            .clip(shape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = buttonText,
            style = Typography.bodyLarge,
            color = Color.White
        )
    }
}

@Composable
fun GradientConfirmButton2(
    modifier: Modifier = Modifier,
    text: String = "Подтвердить",
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(12.dp)

    val borderGradient = Brush.linearGradient(
        colors = listOf(Color(0xFF4E4AF2), Color(0xFF34C8E8))
    )

    val colorfulBackground = Brush.linearGradient(
        colors = listOf(Color(0xFF4E4AF2), Color(0xFF34C8E8))
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(BorderStroke(1.dp, borderGradient), shape)
            .background(brush = colorfulBackground, shape = shape)
            .clip(shape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = Typography.bodyLarge.copy(fontSize = 22.sp),
            color = Color.White
        )
    }
}
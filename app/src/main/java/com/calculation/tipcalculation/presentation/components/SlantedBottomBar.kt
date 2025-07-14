package com.calculation.tipcalculation.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

sealed class BottomBarIcon {
    data class ResourceIcon(val resId: Int) : BottomBarIcon()
    data class PainterIcon(val painter: Painter) : BottomBarIcon()
}

@Composable
fun SlantedBottomBar(
    icons: List<BottomBarIcon>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    val baseBarHeight = 70.dp
    val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(baseBarHeight + bottomPadding)
            .drawBehind {
                val width = size.width
                val height = size.height

                val gradientBrush = Brush.linearGradient(
                    colors = listOf(Color(0xFF363E51), Color(0xFF181C24)),
                    start = Offset.Zero,
                    end = Offset(width, height)
                )

                val path = Path().apply {
                    moveTo(0f, height * 0.2f)
                    lineTo(width, 0f)
                    lineTo(width, height)
                    lineTo(0f, height)
                    close()
                }

                drawPath(path = path, brush = gradientBrush)
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            icons.forEachIndexed { index, icon ->
                val isSelected = index == selectedIndex
                SlantedBarItem(
                    icon = icon,
                    selected = isSelected,
                    onClick = { onItemSelected(index) }
                )
            }
        }
    }
}

@Composable
fun SlantedBarItem(
    icon: BottomBarIcon,
    selected: Boolean,
    onClick: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (selected) 1.15f else 1f,
        label = "scaleAnimation"
    )
    val interactionSource = remember { MutableInteractionSource() }

    if (selected) {
        SlantedButton(
            icon = icon,
            modifier = Modifier
                .scale(scale)
                .clickable(
                    indication = null,
                    interactionSource = interactionSource
                ) { onClick() }
        )
    } else {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clickable(
                    indication = null,
                    interactionSource = interactionSource
                ) { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = when (icon) {
                    is BottomBarIcon.PainterIcon -> icon.painter
                    is BottomBarIcon.ResourceIcon -> painterResource(id = icon.resId)
                },
                contentDescription = null,
                tint = Color.LightGray,
                modifier = Modifier
                    .scale(scale)
                    .size(24.dp)
            )
        }
    }
}

@Composable
fun SlantedButton(
    modifier: Modifier = Modifier,
    icon: BottomBarIcon,
    onClick: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .size(width = 60.dp, height = 40.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF34C8E8), Color(0xFF4E4AF2)),
                    start = Offset(0f, 0f),
                    end = Offset(100f, 100f)
                ),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = when (icon) {
                is BottomBarIcon.PainterIcon -> icon.painter
                is BottomBarIcon.ResourceIcon -> painterResource(id = icon.resId)
            },
            contentDescription = null,
            tint = Color.White
        )
    }
}
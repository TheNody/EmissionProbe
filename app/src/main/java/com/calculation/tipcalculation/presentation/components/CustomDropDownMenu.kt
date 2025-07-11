package com.calculation.tipcalculation.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.calculation.tipcalculation.presentation.ui.theme.Typography

@Composable
fun FilterTipDropDownMenu(
    tips: List<Double>,
    selectedTip: Double?,
    onTipSelected: (Double) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var boxSize by remember { mutableStateOf(Size.Zero) }
    val density = LocalDensity.current

    val surfaceColor = Color(0xFF2C2C2E)
    val textColor = Color.White
    val placeholderColor = Color.Gray

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopStart)
    ) {
        Box(
            modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    boxSize = coordinates.size.toSize()
                }
                .clip(RoundedCornerShape(12.dp))
                .background(surfaceColor)
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded = !expanded }
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectedTip?.toString() ?: "Выберите наконечник",
                        style = Typography.titleSmall.copy(
                            fontSize = 22.94.sp,
                            fontWeight = FontWeight.Medium,
                            color = selectedTip?.let { textColor } ?: placeholderColor,
                            letterSpacing = 0.03.sp,
                            lineHeight = 34.4.sp
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        tint = Color.White
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .width(with(density) { boxSize.width.toDp() })
                        .background(surfaceColor)
                ) {
                    tips.forEach { tip ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onTipSelected(tip)
                                    expanded = false
                                }
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                        ) {
                            Text(
                                text = tip.toString(),
                                style = Typography.titleSmall.copy(
                                    fontSize = 22.94.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.White,
                                    letterSpacing = 0.03.sp,
                                    lineHeight = 34.4.sp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

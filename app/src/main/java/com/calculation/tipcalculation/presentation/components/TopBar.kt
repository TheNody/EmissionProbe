package com.calculation.tipcalculation.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.calculation.tipcalculation.presentation.theme.Typography
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun MainTopBar(
    title: String = "ООО «Аналитика»",
    icon: ImageVector? = null,
    @DrawableRes iconResId: Int? = null,
    contentDescription: String = "Иконка",
    onIconClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = Typography.titleLarge.copy(
                fontSize = 24.sp,
                color = Color.White
            )
        )

        when {
            icon != null -> {
                SearchButton(
                    icon = icon,
                    contentDescription = contentDescription,
                    onClick = onIconClick
                )
            }
            iconResId != null -> {
                SearchButton(
                    iconResId = iconResId,
                    contentDescription = contentDescription,
                    onClick = onIconClick
                )
            }
        }
    }
}

@Composable
fun MainTopBarWithBackArrowAndCustomIcon(
    title: String,
    @DrawableRes iconResId: Int,
    onBackClick: () -> Unit,
    onCustomIconClick: () -> Unit,
    customIconDescription: String = "Настройки"
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SearchButton(
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Назад",
            onClick = onBackClick
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = title,
            style = Typography.titleLarge.copy(
                fontSize = 22.sp,
                color = Color.White
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        SearchButton(
            iconResId = iconResId,
            contentDescription = customIconDescription,
            onClick = onCustomIconClick
        )
    }
}

package com.calculation.tipcalculation.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.calculation.tipcalculation.presentation.ui.theme.Typography

@Composable
fun TopBarWithDescription(
    title: String,
    descriptionTitle: String,
    descriptionText: String,
    onBackClick: () -> Unit,
    iconResId: Int,
    modifier: Modifier = Modifier
) {
    var showDescription by remember { mutableStateOf(false) }

    AnimatedContent(
        targetState = showDescription,
        label = "TopBarWithDescriptionAnimation",
        modifier = modifier
    ) { expanded ->
        if (!expanded) {
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
                    contentDescription = "Описание",
                    onClick = { showDescription = true }
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(Color(0xFF1F1F2B), Color(0xFF232438))
                        )
                    )
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
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
                        contentDescription = "Скрыть описание",
                        onClick = { showDescription = false }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = descriptionTitle,
                    style = Typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = descriptionText,
                    style = Typography.bodyLarge
                )
            }
        }
    }
}
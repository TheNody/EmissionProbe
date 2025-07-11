package com.calculation.tipcalculation.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.calculation.tipcalculation.presentation.theme.Typography

@Composable
fun ResultText(title: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Text(
            text = title,
            style = Typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = value,
            style = Typography.titleLarge
        )
    }
}
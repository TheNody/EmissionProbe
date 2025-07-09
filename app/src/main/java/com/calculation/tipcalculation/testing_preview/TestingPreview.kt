package com.calculation.tipcalculation.testing_preview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.calculation.tipcalculation.presentation.components.CustomBackground
import com.calculation.tipcalculation.presentation.components.CustomGradientButton
import com.calculation.tipcalculation.presentation.components.GradientConfirmButton
import com.calculation.tipcalculation.presentation.components.InnerShadowBoxWithButton
import com.calculation.tipcalculation.presentation.components.SearchButton

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CustomBackgroundPreview() {
    MaterialTheme {
        CustomBackground()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CustomGradientButtonPreview() {
    Surface(color = Color(0xFF121212)) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            CustomGradientButton(
                text = "Внешняя фильтрация",
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .defaultMinSize(minHeight = 56.dp)
                    .wrapContentHeight(unbounded = true)
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InnerShadowPreview() {
    val state = remember { mutableStateOf("") }

    InnerShadowBoxWithButton(
        value = state.value,
        onValueChange = { state.value = it },
        onConfirm = { /* Preview stub */ },
        onStartEditing = { /* Preview stub */ },
        isConfirmed = false,
        modifier = Modifier.padding(16.dp),
        placeholder = "Только число"
    )
}

@Preview(showBackground = true)
@Composable
fun GradientConfirmButtonPreview() {
    var confirmed by remember { mutableStateOf(false) }

    GradientConfirmButton(
        isConfirmed = confirmed,
        onClick = { confirmed = !confirmed },
        modifier = Modifier.padding(16.dp)
    )
}


@Preview(showBackground = true)
@Composable
fun GradientIconButtonPreview() {
    SearchButton(
        icon = Icons.Default.Search,
        contentDescription = "Поиск",
        onClick = {}
    )
}

package com.calculation.tipcalculation.testing_preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.calculation.tipcalculation.presentation.components.BoxCard
import com.calculation.tipcalculation.presentation.components.CustomBackground
import com.calculation.tipcalculation.presentation.components.CustomGradientButton
import com.calculation.tipcalculation.presentation.components.FilterTipDropDownMenu
import com.calculation.tipcalculation.presentation.components.GradientConfirmButton
import com.calculation.tipcalculation.presentation.components.InnerShadowBoxWithButton
import com.calculation.tipcalculation.presentation.components.SearchButton
import com.calculation.tipcalculation.presentation.ui.theme.Typography

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

@Preview(showBackground = true, backgroundColor = 0xFF1E1E2E)
@Composable
fun BoxCardPreview() {
    BoxCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "PEUGEOT - LR01",
            style = Typography.titleLarge.copy(color = Color.White)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "The LR01 uses the same design as the most iconic bikes from PEUGEOT Cycles’ 130-year history...",
            style = Typography.bodyLarge.copy(color = Color.White)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FilterTipDropDownMenuPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF121212))
                .padding(16.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            var selectedTip by remember { mutableStateOf<Double?>(null) }

            FilterTipDropDownMenu(
                tips = listOf(1.2, 2.5, 3.0, 4.75),
                selectedTip = selectedTip,
                onTipSelected = { selectedTip = it }
            )
        }
    }
}
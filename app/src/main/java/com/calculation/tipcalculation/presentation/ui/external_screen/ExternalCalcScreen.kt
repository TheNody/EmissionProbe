package com.calculation.tipcalculation.presentation.ui.external_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.calculation.tipcalculation.R
import com.calculation.tipcalculation.presentation.components.CustomBackground
import com.calculation.tipcalculation.presentation.components.InnerShadowBox
import com.calculation.tipcalculation.presentation.components.TopBarWithDescription
import com.calculation.tipcalculation.presentation.components.ValuesListCard
import com.calculation.tipcalculation.presentation.ui.theme.Typography
import com.calculation.tipcalculation.presentation.view_model.external_screen.ExternalFilterViewModel

@Composable
fun ExternalCalcScreen(
    navController: NavController,
    viewModel: ExternalFilterViewModel = hiltViewModel()
) {
    var input by remember { mutableStateOf("") }
    val tips by viewModel.tips.observeAsState(emptyList())

    Surface(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            CustomBackground()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(horizontal = 24.dp, vertical = 10.dp)
            ) {
                Spacer(modifier = Modifier.height(70.dp))

                Text(
                    text = "Здесь будет экран расчёта внешней фильтрации",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }

            TopBarWithDescription(
                title = "Внешняя фильтрация",
                descriptionTitle = "Наконечники для внешней фильтрации",
                iconResId = R.drawable.external_transmission_svgrepo_com,
                onBackClick = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 24.dp)
                    .zIndex(1f)
            ) {
                Column {
                    InnerShadowBox(
                        value = input,
                        onValueChange = {
                            val sanitized = it.replace(',', '.')
                            if (
                                sanitized.count { c -> c == '.' } <= 1 &&
                                sanitized.all { c -> c.isDigit() || c == '.' } &&
                                !sanitized.startsWith(".")
                            ) {
                                input = sanitized
                            }
                        },
                        placeholder = "Введите размеры наконечников",
                        modifier = Modifier.fillMaxWidth(),
                        onDone = {
                            if (input.isNotBlank()) {
                                viewModel.insertTip(input)
                                input = ""
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    if (tips.isEmpty()) {
                        Text(
                            text = "Вы не ввели ни одного значения",
                            color = Color.LightGray,
                            style = Typography.headlineSmall,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp)
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    } else {
                        Spacer(modifier = Modifier.height(16.dp))

                        ValuesListCard(
                            values = tips.map { it.value.toString() },
                            onRemove = { valueStr ->
                                val tip = tips.find { it.value.toString() == valueStr }
                                tip?.let { viewModel.deleteTip(it) }
                            }
                        )
                    }
                }
            }
        }
    }
}

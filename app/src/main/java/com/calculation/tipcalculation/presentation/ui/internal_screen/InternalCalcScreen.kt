package com.calculation.tipcalculation.presentation.ui.internal_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.calculation.tipcalculation.R
import com.calculation.tipcalculation.domain.model.ValidationResult
import com.calculation.tipcalculation.presentation.components.CustomAlertDialog
import com.calculation.tipcalculation.presentation.components.CustomBackground
import com.calculation.tipcalculation.presentation.components.CustomGradientButton
import com.calculation.tipcalculation.presentation.components.ExpandableBoxCard
import com.calculation.tipcalculation.presentation.components.InnerShadowBox
import com.calculation.tipcalculation.presentation.components.ResultText
import com.calculation.tipcalculation.presentation.components.ShadowedCard
import com.calculation.tipcalculation.presentation.components.TopBarWithDescription
import com.calculation.tipcalculation.presentation.components.ValuesListCard
import com.calculation.tipcalculation.presentation.navigation.Screen
import com.calculation.tipcalculation.presentation.ui.theme.Typography
import com.calculation.tipcalculation.presentation.view_model.internal_screen.InternalFilterViewModel

@Composable
fun InternalCalcScreen(
    navController: NavController,
    viewModel: InternalFilterViewModel = hiltViewModel()
) {
    var input by remember { mutableStateOf("") }
    val tips by viewModel.tips.observeAsState(emptyList())
    val history = viewModel.history.value
    val scrollState = rememberScrollState()
    val validationResult by viewModel.validationResult

    Surface(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            CustomBackground()

            TopBarWithDescription(
                title = "Внутренняя фильтрация",
                descriptionTitle = "Наконечники для внутренней фильтрации",
                iconResId = R.drawable.internal_transmission_svgrepo_com,
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
                        onValueChange = { input = it },
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

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(horizontal = 24.dp, vertical = 10.dp)
            ) {
                Spacer(modifier = Modifier.height(70.dp))

                CustomGradientButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Новый расчёт",
                    onClick = {
                        viewModel.validateBeforeCalculation {
                            navController.navigate(Screen.InternalFilterCalc.route)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                ShadowedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                ) {
                    Text(
                        text = "Сохранённые вычисления",
                        style = Typography.titleMedium.copy(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.35.sp,
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF3CA4EB),
                                    Color(0xFF4286EE)
                                )
                            )
                        ),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    if (history.isEmpty()) {
                        Text(
                            text = "Нет сохранённых данных",
                            style = Typography.bodyLarge,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    } else {
                        history.forEach { item ->
                            ExpandableBoxCard(
                                dateText = item.timestamp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp)
                            ) {
                                Text(
                                    text = "Исходные данные",
                                    style = Typography.headlineSmall,
                                    color = Color(0xFF1A1B20)
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                ResultText("P атм", "${item.patm} мм рт. ст.")
                                ResultText("t среды", "${item.tsr} °C")
                                ResultText("t асп.", "${item.tasp} °C")
                                ResultText("P ср.", "${item.plsr} мм вод. ст.")
                                ResultText("P реом.", "${item.preom} мм рт. ст.")
                                ResultText("Скорости", item.speeds.joinToString("; ") { v -> "%.2f".format(v) })
                                ResultText("Выбранный диаметр", "%.4f".format(item.selectedTip))

                                Spacer(modifier = Modifier.height(24.dp))

                                Text(
                                    text = "Результат вычисления",
                                    style = Typography.headlineSmall,
                                    color = Color(0xFF1A1B20)
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                ResultText("Средняя скорость", "%.4f м/с".format(item.averageSpeed))
                                ResultText("Идеальный наконечник", "%.4f".format(item.averageTip))
                                ResultText("Vp выбранного наконечника", "%.4f м/с".format(item.vp))
                            }
                        }
                    }
                }
            }

            if (validationResult != null) {
                when (validationResult) {
                    is ValidationResult.MissingSpeeds -> {
                        CustomAlertDialog(
                            titleText = "Недостаточно данных",
                            bodyText = "Пожалуйста, введите количество скоростей.",
                            confirmButtonText = "Ввести",
                            cancelButtonText = "Отмена",
                            onDismissRequest = { viewModel.dismissValidationDialog() },
                            onConfirm = {
                                viewModel.dismissValidationDialog()
                                navController.navigate(Screen.SpeedCount.route)
                            },
                            onCancel = {
                                viewModel.dismissValidationDialog()
                            }
                        )
                    }

                    is ValidationResult.MissingTips -> {
                        CustomAlertDialog(
                            titleText = "Недостаточно данных",
                            bodyText = "Пожалуйста, введите хотя бы один наконечник.",
                            confirmButtonText = "Ввести",
                            cancelButtonText = "Отмена",
                            onDismissRequest = { viewModel.dismissValidationDialog() },
                            onConfirm = {
                                viewModel.dismissValidationDialog()
                            },
                            onCancel = {
                                viewModel.dismissValidationDialog()
                            }
                        )
                    }

                    else -> {}
                }
            }
        }
    }
}


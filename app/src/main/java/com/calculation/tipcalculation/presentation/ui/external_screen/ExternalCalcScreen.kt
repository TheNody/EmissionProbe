package com.calculation.tipcalculation.presentation.ui.external_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
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
import com.calculation.tipcalculation.presentation.theme.Typography
import com.calculation.tipcalculation.presentation.view_model.external_screen.ExternalFilterViewModel

@Composable
fun ExternalCalcScreen(
    navController: NavController,
    viewModel: ExternalFilterViewModel = hiltViewModel()
) {
    var input by remember { mutableStateOf("") }
    val tips by viewModel.tips.observeAsState(emptyList())
    val history by viewModel.history.collectAsState()
    val scrollState = rememberScrollState()
    val validationResult by viewModel.validationResult

    Surface(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            CustomBackground()

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
                    .pointerInput(Unit) {}
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
                            navController.navigate(Screen.ExternalFilterCalc.route)
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

                                ResultText("P атм", "%.2f мм рт. ст.".format(item.patm))
                                ResultText("P ср.", "%.2f мм вод. ст.".format(item.plsr))
                                ResultText("P реом.", "%.2f мм рт. ст.".format(item.preom))
                                ResultText("t среды", "%.2f °C".format(item.tsr))
                                ResultText("t асп.", "%.2f °C".format(item.tasp))
                                ResultText("Скорости", item.speeds.joinToString("; ") { "%.2f".format(it) })

                                Spacer(modifier = Modifier.height(24.dp))

                                Text(
                                    text = "Результат вычисления",
                                    style = Typography.headlineSmall,
                                    color = Color(0xFF1A1B20)
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                ResultText("Средняя скорость", "%.2f м/с".format(item.srznach))
                                ResultText("Σ отклонений", "%.2f".format(item.sigma))
                                ResultText("СКО", "%.3f".format(item.sko))
                                ResultText("Идеальный d", "%.2f".format(item.average))
                                ResultText("Рассчитанный наконечник", "%.2f".format(item.calculatedTip))
                                ResultText("Размер наконечника", "%.2f".format(item.tipSize))
                                ResultText("Vp выбранного", "%.2f".format(item.selectedVp))
                                ResultText("Vp усл.", "%.2f".format(item.aspUsl))
                                ResultText("P асп, мм вод. ст. ВП-20", "%.2f".format(item.result))
                                ResultText("Vp усл. 1", "%.2f".format(item.aspUsl1))
                                ResultText("d усл. 2", "%.2f".format(item.duslov1))
                                ResultText("Выбранный наконечник", "%.2f".format(item.vibrNak))
                                ResultText("d реальный", "%.2f".format(item.dreal))
                                ResultText("Vp усл. 2", "%.2f".format(item.vsp2))
                                ResultText("Ближайший к идеальному", "%.2f".format(item.closestDiameter))
                                ResultText("Первый подходящий", "%.2f".format(item.firstSuitableDiameter))

                                Spacer(modifier = Modifier.height(24.dp))

                                Text(
                                    text = "Проверенные диаметры и Vp",
                                    style = Typography.headlineSmall,
                                    color = Color(0xFF1A1B20)
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                item.checkedDiametersList.forEach { (d, vp) ->
                                    ResultText(
                                        "Диаметр: %.2f".format(d),
                                        "Vp: %.2f".format(vp)
                                    )
                                }
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


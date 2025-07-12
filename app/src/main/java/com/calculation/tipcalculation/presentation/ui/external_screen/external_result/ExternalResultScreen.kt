package com.calculation.tipcalculation.presentation.ui.external_screen.external_result

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.calculation.tipcalculation.R
import com.calculation.tipcalculation.presentation.components.*
import com.calculation.tipcalculation.presentation.navigation.Screen
import com.calculation.tipcalculation.presentation.theme.Typography
import com.calculation.tipcalculation.presentation.view_model.external_screen.external_result_screen.ExternalResultViewModel

@Composable
fun ExternalResultScreen(
    navController: NavController,
    viewModel: ExternalResultViewModel = hiltViewModel()
) {
    val result = viewModel.savedResult.collectAsState().value
    val showExitDialog = remember { mutableStateOf(false) }

    BackHandler(enabled = true) {
        showExitDialog.value = true
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            CustomBackground2()

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .statusBarsPadding(),
                contentPadding = PaddingValues(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    MainTopBarWithBackArrowAndCustomIcon(
                        title = "Результат",
                        iconResId = R.drawable.external_transmission_svgrepo_com,
                        onBackClick = { showExitDialog.value = true },
                        onCustomIconClick = {}
                    )
                }

                item {
                    result?.let {
                        BoxCard(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "Исходные данные",
                                style = Typography.headlineSmall,
                                color = Color(0xFF1A1B20)
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            ResultText("P атм", "%.2f мм рт. ст.".format(it.patm))
                            ResultText("P ср.", "%.2f мм вод. ст.".format(it.plsr))
                            ResultText("P реом.", "%.2f мм рт. ст.".format(it.preom))
                            ResultText("t среды", "%.2f °C".format(it.tsr))
                            ResultText("t асп.", "%.2f °C".format(it.tasp))
                            ResultText("Скорости", it.speeds.joinToString("; ") { v -> "%.2f".format(v) })

                            Spacer(modifier = Modifier.height(24.dp))

                            Text(
                                text = "Результат вычисления",
                                style = Typography.headlineSmall,
                                color = Color(0xFF1A1B20)
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            ResultText("Средняя скорость", "%.2f м/с".format(it.srznach))
                            ResultText("СКО", "%.3f".format(it.sko))
                            ResultText("Σ отклонений", "%.2f".format(it.sigma))
                            ResultText("Идеальный d", "%.2f".format(it.average))
                            ResultText("Рассчитанный наконечник", "%.2f".format(it.calculatedTip))
                            ResultText("Размер наконечника", "%.2f".format(it.tipSize))
                            ResultText("Vp выбранного", "%.2f".format(it.selectedVp))
                            ResultText("Vp усл.", "%.2f".format(it.aspUsl))
                            ResultText("P асп, мм вод. ст. ВП-20", "%.2f".format(it.result))
                            ResultText("Vp усл. 1", "%.2f".format(it.aspUsl1))
                            ResultText("d усл. 2", "%.2f".format(it.duslov1))
                            ResultText("Выбранный наконечник", "%.2f".format(it.vibrNak))
                            ResultText("d реальный", "%.2f".format(it.dreal))
                            ResultText("Vp усл. 2", "%.2f".format(it.vsp2))

                            Spacer(modifier = Modifier.height(24.dp))

                            Text(
                                text = "Выбор наконечника",
                                style = Typography.headlineSmall,
                                color = Color(0xFF1A1B20)
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            ResultText("Ближайший подходящий", "%.2f".format(it.firstSuitableDiameter))
                            ResultText("Ближайший к идеальному", "%.2f".format(it.closestDiameter))
                        }
                    } ?: BoxCard(modifier = Modifier.fillMaxWidth()) {
                        Text("Нет данных для отображения", style = Typography.titleLarge)
                    }
                }

                item {
                    result?.let {
                        BoxCard(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "Проверенные диаметры и Vp",
                                style = Typography.headlineSmall,
                                color = Color(0xFF1A1B20)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            it.checkedDiametersList.forEach { (d, vp) ->
                                ResultText(
                                    "Диаметр: %.2f".format(d),
                                    "Vp: %.2f".format(vp)
                                )
                            }
                        }
                    }
                }

                item {
                    GradientConfirmButton2(
                        modifier = Modifier
                            .fillMaxWidth()
                            .navigationBarsPadding(),
                        text = "Сохранить результат",
                        onClick = {
                            viewModel.saveResultToHistory()
                            navController.navigate(Screen.ExternalCalc.route) {
                                popUpTo(Screen.ExternalFilterCalc.route)
                                { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }

            if (showExitDialog.value) {
                CustomAlertDialog(
                    titleText = "Выйти без сохранения?",
                    bodyText = "Результат будет утерян. Вернуться к редактированию?",
                    confirmButtonText = "Выйти",
                    cancelButtonText = "Остаться",
                    onDismissRequest = { showExitDialog.value = false },
                    onConfirm = {
                        showExitDialog.value = false
                        navController.popBackStack()
                    },
                    onCancel = { showExitDialog.value = false }
                )
            }
        }
    }
}

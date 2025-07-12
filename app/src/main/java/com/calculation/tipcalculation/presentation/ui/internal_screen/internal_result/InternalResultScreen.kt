package com.calculation.tipcalculation.presentation.ui.internal_screen.internal_result

import androidx.activity.compose.BackHandler
import com.calculation.tipcalculation.presentation.components.ResultText
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.calculation.tipcalculation.R
import com.calculation.tipcalculation.presentation.components.*
import com.calculation.tipcalculation.presentation.navigation.Screen
import com.calculation.tipcalculation.presentation.theme.Typography
import com.calculation.tipcalculation.presentation.view_model.internal_screen.internal_result_screen.InternalResultViewModel

@Composable
fun InternalResultScreen(
    navController: NavController,
    viewModel: InternalResultViewModel = hiltViewModel()
) {
    val result = viewModel.result.collectAsState().value

    val showDialog = remember { mutableStateOf(false) }

    BackHandler(enabled = true) {
        showDialog.value = true
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
                        iconResId = R.drawable.internal_transmission_svgrepo_com,
                        onBackClick = { showDialog.value = true },
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
                            ResultText("P атм", "${it.patm} мм рт. ст.")
                            ResultText("t среды", "${it.tsr} °C")
                            ResultText("t асп.", "${it.tasp} °C")
                            ResultText("P ср.", "${it.plsr} мм вод. ст.")
                            ResultText("P реом.", "${it.preom} мм рт. ст.")
                            ResultText("Скорости", it.speeds.joinToString("; ") { v -> "%.2f".format(v) })
                            ResultText("Выбранный диаметр", "%.4f".format(it.selectedTip))

                            Spacer(modifier = Modifier.height(24.dp))

                            Text(
                                text = "Результат вычисления",
                                style = Typography.headlineSmall,
                                color = Color(0xFF1A1B20)
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                            ResultText("Средняя скорость", "%.4f м/с".format(it.averageSpeed))
                            ResultText("Идеальный наконечник", "%.4f".format(it.averageTip))
                            ResultText("Vp выбранного наконечника", "%.4f м/с".format(it.vp))
                        }
                    } ?: BoxCard(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Нет данных для отображения",
                            style = Typography.titleLarge
                        )
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
                            navController.navigate(Screen.InternalCalc.route) {
                                popUpTo(Screen.InternalFilterCalc.route) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }

            if (showDialog.value) {
                CustomAlertDialog(
                    titleText = "Выйти без сохранения?",
                    bodyText = "Результат не будет сохранён. Вы вернётесь к редактированию исходных данных.",
                    confirmButtonText = "Выйти",
                    cancelButtonText = "Остаться",
                    onDismissRequest = { showDialog.value = false },
                    onConfirm = {
                        showDialog.value = false
                        navController.popBackStack()
                    },
                    onCancel = { showDialog.value = false }
                )
            }
        }
    }
}
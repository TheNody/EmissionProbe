package com.calculation.tipcalculation.presentation.ui.measurement_check.round.result

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.calculation.tipcalculation.presentation.view_model.measurement_check.round.result.RoundResultViewModel

@Composable
fun RoundResultScreen(
    navController: NavController,
    viewModel: RoundResultViewModel = hiltViewModel()
) {
    val result by viewModel.result.collectAsState()
    val showDialog = remember { mutableStateOf(false) }

    BackHandler {
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
                        iconResId = R.drawable.ic_measurement,
                        onBackClick = { showDialog.value = true },
                        onCustomIconClick = {}
                    )
                }

                item {
                    result?.let {
                        BoxCard(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "Результаты расчета",
                                style = Typography.headlineSmall,
                                color = Color(0xFF1A1B20)
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            ResultText("Диаметр D", "%.2f мм".format(it.d))
                            ResultText("Эквивалентный диаметр De", "%.2f мм".format(it.de))
                            ResultText("L / De", "%.2f".format(it.lOverDe))
                            ResultText("Lz", "%.2f мм".format(it.lz))
                            ResultText(
                                "Количество точек",
                                "${it.rule.totalPoints} (по диаметру: ${it.rule.diameterPoints})"
                            )

                            it.ki?.let { kiList ->
                                Spacer(modifier = Modifier.height(16.dp))
                                Text("Коэффициенты Ki", style = Typography.titleMedium)
                                Text(kiList.joinToString(", ") { "%.2f".format(it) })
                            }
                        }
                    } ?: BoxCard(modifier = Modifier.fillMaxWidth()) {
                        Text("Нет данных для отображения", style = Typography.titleLarge)
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
                            navController.navigate(Screen.SectionTypeSelection.route) {
                                popUpTo(Screen.SectionTypeSelection.route) {
                                    inclusive = true
                                }
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

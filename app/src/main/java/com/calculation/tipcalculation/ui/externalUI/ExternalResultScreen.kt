//package com.calculation.tipcalculation.ui.externalUI
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import com.calculation.tipcalculation.db_Main.SettingsViewModel
//import com.calculation.tipcalculation.screen_comp.ExpandableTable
//import com.calculation.tipcalculation.screen_comp.ResultCard
//import com.calculation.tipcalculation.screen_comp.Table
//import com.calculation.tipcalculation.screen_comp.TableRow
//import com.calculation.tipcalculation.utils.HISTORY_SCREEN
//import com.calculation.tipcalculation.viewmodel.CalculationViewModel
//import java.util.Locale
//
//@Composable
//fun ExternalResultScreen(
//    settingsViewModel: SettingsViewModel,
//    navController: NavController,
//    calculationViewModel: CalculationViewModel
//) {
//    val calculationData = settingsViewModel.data
//
//    val existingReportData = settingsViewModel.reportData.value
//
//    val speeds = settingsViewModel.speeds.map { it.toDoubleOrNull() }
//
//    Column(modifier = Modifier.fillMaxSize()) {
//        LazyColumn(
//            modifier = Modifier.fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//
//            item {
//                ResultCard(
//                    title = "Vp",
//                    value = String.format(
//                        Locale.getDefault(),
//                        "%.2f",
//                        calculationData.selectedVp
//                    )
//                )
//            }
//            item {
//                ResultCard(
//                    title = "Ближайший рассчитанный наконечник",
//                    value = String.format(
//                        Locale.getDefault(),
//                        "%.2f",
//                        calculationData.firstSuitableDiameter
//                    )
//                )
//            }
//            item {
//                ExpandableTable(title = "Показать данные") {
//                    Table {
//                        TableRow(
//                            "Vср. (м/с)",
//                            "${String.format(Locale.getDefault(), "%.2f", calculationData.srznach)} ± " +
//                                    String.format(Locale.getDefault(), "%.2f", calculationData.sigma)
//                        )
//                        TableRow(
//                            "d идеальный",
//                            String.format(Locale.getDefault(), "%.2f", calculationData.average)
//                        )
//                        TableRow(
//                            "d реал",
//                            String.format(Locale.getDefault(), "%.2f", calculationData.tipSize)
//                        )
//                        TableRow(
//                            "P атм",
//                            String.format(Locale.getDefault(), "%.2f", calculationData.patm)
//                        )
//                        TableRow(
//                            "V aсп усл",
//                            String.format(Locale.getDefault(), "%.2f", calculationData.aspUsl)
//                        )
//                        TableRow(
//                            "P асп, мм вод.ст. ВП-20",
//                            String.format(Locale.getDefault(), "%.2f", calculationData.result)
//                        )
//                        TableRow(
//                            "V aсп усл1",
//                            String.format(Locale.getDefault(), "%.2f", calculationData.aspUsl1)
//                        )
//                        TableRow(
//                            "d усл2",
//                            String.format(Locale.getDefault(), "%.2f", calculationData.duslov1)
//                        )
//                        TableRow(
//                            "d реал",
//                            String.format(Locale.getDefault(), "%.2f", calculationData.dreal)
//                        )
//                        TableRow(
//                            "V aсп усл2",
//                            String.format(Locale.getDefault(), "%.2f", calculationData.vsp2)
//                        )
//                        TableRow(
//                            "Рассчитанный нак.",
//                            String.format(Locale.getDefault(), "%.2f", calculationData.calculatedTip)
//                        )
//                        TableRow(
//                            "СКО",
//                            String.format(Locale.getDefault(), "%.3f", calculationData.sko)
//                        )
//                    }
//                }
//            }
//            item {
//                ExpandableTable(title = "Проверенные диаметры и Vp") {
//                    Table {
//                        calculationData.checkedDiametersList.forEach { (diameter, vp) ->
//                            TableRow(
//                                title = "Диаметр: ${String.format(Locale.getDefault(), "%.2f", diameter)}",
//                                value = "Vp: ${String.format(Locale.getDefault(), "%.2f", vp)}"
//                            )
//                        }
//                    }
//                }
//            }
//
//            item {
//                Button(onClick = {
//                    if (existingReportData != null) {
//                        val finalReportData = calculationViewModel.prepareReportData(
//                            reportData = existingReportData,
//                            speeds = speeds
//                        )
//
//                        settingsViewModel.savePreparedReportData(finalReportData)
//
//                        navController.navigate(HISTORY_SCREEN)
//                    } else {
//                        android.util.Log.e("ExternalResultScreen", "Нет данных (reportData=null) для сохранения")
//                    }
//                }) {
//                    Text("Сохранить результаты")
//                }
//            }
//        }
//    }
//}

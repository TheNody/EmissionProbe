//package com.calculation.tipcalculation.ui.externalUI
//
//import android.util.Log
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.livedata.observeAsState
//import androidx.compose.runtime.mutableDoubleStateOf
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalFocusManager
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavController
//import com.calculation.tipcalculation.db_Main.SettingsViewModel
//import com.calculation.tipcalculation.model.Field
//import com.calculation.tipcalculation.model.ReportData
//import com.calculation.tipcalculation.screen_comp.CustomSpeedTextFieldItem
//import com.calculation.tipcalculation.screen_comp.CustomTextFieldItem
//import com.calculation.tipcalculation.utils.EXTERNAL_RESULT_SCREEN
//import com.calculation.tipcalculation.viewmodel.CalculationViewModel
//
//@Composable
//fun ExternalCalculationScreen(
//    navController: NavController,
//    calculationViewModel: CalculationViewModel,
//    settingsViewModel: SettingsViewModel = viewModel()
//) {
//    val focusManager = LocalFocusManager.current
//    val speeds = settingsViewModel.speeds
//
//    val filterTips by settingsViewModel.allExternalFilterTips.observeAsState(emptyList())
//
//    LaunchedEffect(filterTips) {
//        calculationViewModel.setFilterTips(filterTips)
//    }
//
//    val fields = listOf(
//        Field("Введите P атм. (мм. рт. ст.)", calculationViewModel.patm.value) { calculationViewModel.patm.value = it },
//        Field("Введите Р среды (мм.вод.ст.)", calculationViewModel.plsr.value) { calculationViewModel.plsr.value = it },
//        Field("Введите t среды (оС)", calculationViewModel.tsr.value) { calculationViewModel.tsr.value = it },
//        Field("Введите t асп (оС)", calculationViewModel.tasp.value) { calculationViewModel.tasp.value = it },
//        Field("Введите P реом (мм. рт. ст.)", calculationViewModel.preom.value) { calculationViewModel.preom.value = it }
//    )
//
//    Log.d("ExternalCalculationScreen", "Значения из базы данных для внешней фильтрации: ${filterTips.map { it.value }}")
//
//    Column(modifier = Modifier.fillMaxSize()) {
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(top = 8.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            items(fields) { field ->
//                CustomTextFieldItem(field, focusManager)
//            }
//
//            items(speeds.size) { index ->
//                CustomSpeedTextFieldItem(index, speeds, settingsViewModel, focusManager)
//            }
//
//            item {
//                Button(
//                    onClick = {
//                        Log.d("DEBUG", "Список speeds перед расчётом: ${speeds.map { it }}")
//                        calculationViewModel.calculationState.value.externalCalculationDone = true
//                        val reportData = ReportData(
//                            tsr = mutableDoubleStateOf(calculationViewModel.tsr.value.toDoubleOrNull() ?: 0.0),
//                            tasp = mutableDoubleStateOf(calculationViewModel.tasp.value.toDoubleOrNull() ?: 0.0),
//                            plsr = mutableDoubleStateOf(calculationViewModel.plsr.value.toDoubleOrNull() ?: 0.0),
//                            patm = calculationViewModel.patm.value.toDoubleOrNull() ?: 0.0
//                        )
//
//                        Log.d("ExternalCalculationScreen", "Переданные данные: tsr=${reportData.tsr.value}, tasp=${reportData.tasp.value}, plsr=${reportData.plsr.value}")
//
//                        val preparedData = calculationViewModel.prepareReportData(
//                            reportData,
//                            speeds.map { it.toDoubleOrNull() }
//                        )
//
//                        calculationViewModel.calculateResult(
//                            reportData,
//                            speeds.map { it.toDoubleOrNull() ?: 0.0 },
//                            calculationViewModel.patm.value.toDoubleOrNull(),
//                            calculationViewModel.preom.value.toDoubleOrNull(),
//                            settingsViewModel
//                        )
//
//                        settingsViewModel.updateReportData(preparedData)
//
//                        navController.navigate(EXTERNAL_RESULT_SCREEN)
//                    },
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Text("Вычислить")
//                }
//            }
//        }
//    }
//}
//
//

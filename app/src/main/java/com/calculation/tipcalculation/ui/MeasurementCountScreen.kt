//package com.calculation.tipcalculation.ui
//
//import android.util.Log
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.wrapContentHeight
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardActions
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.calculation.tipcalculation.db_Main.SettingsViewModel
//import com.calculation.tipcalculation.screen_comp.ValueItem
//
//@Composable
//fun MeasurementCountScreen(settingsViewModel: SettingsViewModel) {
//    var inputValue by remember { mutableStateOf(settingsViewModel.enteredSpeedCount) }
//    var isValueEntered by remember { mutableStateOf(settingsViewModel.enteredSpeedCount.isNotEmpty()) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        Text(
//            text = "Введите количество скоростей",
//            fontSize = 15.7.sp,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(bottom = 12.dp, top = 12.dp)
//        )
//
//        if (!isValueEntered) {
//            OutlinedTextField(
//                value = inputValue,
//                onValueChange = {
//                    if (it.all { char -> char.isDigit() }) {
//                        inputValue = it
//                    }
//                },
//                label = { Text("Введите значение") },
//                keyboardOptions = KeyboardOptions.Default.copy(
//                    keyboardType = KeyboardType.Number,
//                    imeAction = ImeAction.Done
//                ),
//                keyboardActions = KeyboardActions(
//                    onDone = {
//                        if (inputValue.isNotEmpty()) {
//                            val valueToAdd = inputValue.toIntOrNull()
//                            if (valueToAdd != null) {
//                                settingsViewModel.insertSpeedCount(valueToAdd)
//                                Log.d("MeasurementCountScreen", "Установка количества скоростей: $valueToAdd")
//                                isValueEntered = true
//                            } else {
//                                Log.e("MeasurementCountScreen", "Ошибка: введено неверное значение количества скоростей.")
//                            }
//                        } else {
//                            Log.e("MeasurementCountScreen", "Ошибка: пустое значение введено.")
//                        }
//                    }
//                ),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = 16.dp)
//            )
//        } else {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .wrapContentHeight()
//                    .clip(RoundedCornerShape(16.dp))
//                    .background(Color.Transparent)
//                    .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
//                    .padding(16.dp)
//            ) {
//                ValueItem(value = inputValue) {
//                    settingsViewModel.deleteAllSpeedCount()
//                    isValueEntered = false
//                    inputValue = ""
//                }
//            }
//        }
//    }
//}
//

package com.calculation.tipcalculation.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.calculation.tipcalculation.db_Main.SettingsViewModel
import com.calculation.tipcalculation.model.Field
import com.calculation.tipcalculation.screen_comp.CustomSpeedTextFieldItem
import com.calculation.tipcalculation.screen_comp.CustomTextFieldItem
import com.calculation.tipcalculation.utils.EXTERNAL_RESULT_SCREEN
import com.calculation.tipcalculation.viewmodel.CalculationViewModel

@Composable
fun ExternalCalculationScreen(
    navController: NavController,
    calculationViewModel: CalculationViewModel,
    settingsViewModel: SettingsViewModel = viewModel()
) {

    //val openAlertEmptyString = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val speeds = settingsViewModel.speeds

    val filterTips by settingsViewModel.allExternalFilterTips.observeAsState(emptyList())


    LaunchedEffect(filterTips) {
        calculationViewModel.setFilterTips(filterTips)
    }

    val fields = listOf(
        Field("Введите P атм. (мм. рт. ст.)", calculationViewModel.patm.value) { calculationViewModel.patm.value = it },
        Field("Введите Р среды (мм.вод.ст.)", calculationViewModel.plsr.value) { calculationViewModel.plsr.value = it },
        Field("Введите t среды (оС)", calculationViewModel.tsr.value) { calculationViewModel.tsr.value = it },
        Field("Введите t асп (оС)", calculationViewModel.tasp.value) { calculationViewModel.tasp.value = it },
        Field("Введите P реом (мм. рт. ст.)", calculationViewModel.preom.value) { calculationViewModel.preom.value = it }
    )

    Log.d("ExternalCalculationScreen", "Значения из базы данных для внешней фильтрации: ${filterTips.map { it.value }}")


//     if (openAlertEmptyString.value) {
//
//         DialogWithButton(onDismissRequest = {openAlertEmptyString.value = false},
//             onConfirmation = {} , 2)
//
//            }


    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(fields) { field ->
                CustomTextFieldItem(field, focusManager)
            }

            items(speeds.size) { index ->
                CustomSpeedTextFieldItem(index, speeds, settingsViewModel, focusManager)
            }

            item {
                Button(
                    onClick = {

                        calculationViewModel.calculationState.value.externalCalculationDone = true
//                        println(" my values == ${calculationViewModel.plsr.value}")
//                        println(" my pul value = ${calculationViewModel.patm.value}")
//                        if(calculationViewModel.checkAllEmptyValues()){
//                           // println(" empty Values !")
//                            openAlertEmptyString.value = true
//                        }else {

                           calculationViewModel.calculateResult(

                            calculationViewModel.patm.value.toDoubleOrNull(),
                            speeds.map { it.toDoubleOrNull() ?: 0.0 },
                            calculationViewModel.plsr.value.toDoubleOrNull(),
                            calculationViewModel.tsr.value.toDoubleOrNull(),
                            calculationViewModel.tasp.value.toDoubleOrNull(),
                            calculationViewModel.preom.value.toDoubleOrNull(),
                            settingsViewModel
                        )
                        navController.navigate(EXTERNAL_RESULT_SCREEN)



                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Вычислить")
                }
            }
        }
    }
}


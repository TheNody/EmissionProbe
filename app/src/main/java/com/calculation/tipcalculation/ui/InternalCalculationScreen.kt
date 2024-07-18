package com.calculation.tipcalculation.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.calculation.tipcalculation.db_Main.SettingsViewModel
import com.calculation.tipcalculation.model.Field
import com.calculation.tipcalculation.screen_comp.CustomSpeedTextFieldItem
import com.calculation.tipcalculation.screen_comp.CustomTextFieldItem
import com.calculation.tipcalculation.utils.INTERNAL_RESULT_SCREEN
import com.calculation.tipcalculation.viewmodel.CalculationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InternalCalculationScreen(
    navController: NavController,
    calculationViewModel: CalculationViewModel,
    settingsViewModel: SettingsViewModel = viewModel()
) {
    val focusManager = LocalFocusManager.current
    val speeds = settingsViewModel.speeds

    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(-1) }

    val filterTips by settingsViewModel.allFilterTips.observeAsState(emptyList())

    LaunchedEffect(filterTips) {
        calculationViewModel.setExternalFilterTips(filterTips)
    }

    Log.d("InternalCalculationScreen", "Значения из базы данных для внутренней фильтрации: ${filterTips.map { it.value }}")

    val fields = listOf(
        Field("Введите P атм. (мм. рт. ст.)", calculationViewModel.patm.value) { calculationViewModel.patm.value = it },
        Field("Введите Р среды (мм.вод.ст.)", calculationViewModel.plsr.value) { calculationViewModel.plsr.value = it },
        Field("Введите t среды (оС)", calculationViewModel.tsr.value) { calculationViewModel.tsr.value = it },
        Field("Введите t асп (оС)", calculationViewModel.tasp.value) { calculationViewModel.tasp.value = it },
        Field("Введите P реом (мм. рт. ст.)", calculationViewModel.preom.value) { calculationViewModel.preom.value = it }
    )

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
                Spacer(modifier = Modifier.height(16.dp))
                Text("Выберите наконечник для внутренней фильтрации", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                Log.d("InternalCalculationScreen", "Значения из базы данных: ${filterTips.map { it.value }}")
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.padding(16.dp),
                ) {
                    TextField(
                        value = if (selectedItemIndex >= 0) filterTips[selectedItemIndex].value.toString() else "Выберите наконечник",
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        filterTips.forEachIndexed { index, item ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = item.value.toString(),
                                        fontWeight = if (index == selectedItemIndex) FontWeight.Bold else null,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                },
                                onClick = {
                                    selectedItemIndex = index
                                    calculationViewModel.selectedInnerTip.value = item.value.toString()
                                    expanded = false
                                    calculationViewModel.isButtonVisible.value = true
                                    Log.d("InternalCalculationScreen", "Выбранный наконечник: ${calculationViewModel.selectedInnerTip.value}")
                                }
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                AnimatedVisibility(visible = calculationViewModel.isButtonVisible.value) {
                    Button(
                        onClick = {
                            val selectedDiameter = calculationViewModel.selectedInnerTip.value.toDoubleOrNull()
                            if (selectedDiameter != null) {
                                calculationViewModel.calculateInnerTipVp(
                                    selectedDiameter,
                                    calculationViewModel.patm.value.toDoubleOrNull(),
                                    calculationViewModel.plsr.value.toDoubleOrNull(),
                                    calculationViewModel.tsr.value.toDoubleOrNull(),
                                    calculationViewModel.tasp.value.toDoubleOrNull(),
                                    calculationViewModel.preom.value.toDoubleOrNull(),
                                    speeds.map { it.toDoubleOrNull() ?: 0.0 },
                                    settingsViewModel
                                )
                                navController.navigate(INTERNAL_RESULT_SCREEN)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Вычислить")
                    }
                }
            }
        }
    }
}
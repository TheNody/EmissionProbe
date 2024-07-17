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
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.calculation.tipcalculation.db_Main.SettingsViewModel
import com.calculation.tipcalculation.screen_comp.CustomOutlinedTextField
import com.calculation.tipcalculation.viewmodel.CalculationViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculationScreen(
    navController: NavController,
    calculationViewModel: CalculationViewModel,
    settingsViewModel: SettingsViewModel = viewModel()
) {
    val focusManager = LocalFocusManager.current
    val speeds = settingsViewModel.speeds
    val data = settingsViewModel.data

    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    val tabTitles = listOf("Внешняя фильтрация", "Внутренняя фильтрация")

    var isExpanded by rememberSaveable { mutableStateOf(true) }
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(-1) }

    val filterTips by settingsViewModel.allFilterTips.observeAsState(emptyList())
    val externalFilterTips by settingsViewModel.allExternalFilterTips.observeAsState(emptyList())

    LaunchedEffect(filterTips, externalFilterTips) {
        calculationViewModel.setFilterTips(externalFilterTips)
        calculationViewModel.setExternalFilterTips(filterTips)
    }

    Log.d("CalculationScreen", "Значения из базы данных для внутренней фильтрации: ${externalFilterTips.map { it.value }}")
    Log.d("CalculationScreen", "Значения из базы данных для внешней фильтрации: ${filterTips.map { it.value }}")

    Column(modifier = Modifier.fillMaxSize()) {
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            edgePadding = 16.dp
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                        data.isButtonVisible.value = selectedTabIndex == 0 || selectedItemIndex >= 0
                    },
                    text = { Text(title) }
                )
            }
        }

        if (selectedTabIndex == 0) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp)
                    .graphicsLayer {
                        alpha = 0.9f
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    CustomOutlinedTextField(
                        value = data.patm.value,
                        onValueChange = {
                            data.patm.value = it
                            Log.d("CalculationScreen", "Добавлено значение P атм: $it")
                        },
                        label = "Введите P атм. (мм. рт. ст.)",
                        imeAction = ImeAction.Next,
                        onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    CustomOutlinedTextField(
                        value = data.plsr.value,
                        onValueChange = {
                            data.plsr.value = it
                            Log.d("CalculationScreen", "Добавлено значение Р среды: $it")
                        },
                        label = "Введите Р среды (мм.вод.ст.)",
                        imeAction = ImeAction.Next,
                        onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    CustomOutlinedTextField(
                        value = data.tsr.value,
                        onValueChange = {
                            data.tsr.value = it
                            Log.d("CalculationScreen", "Добавлено значение t среды: $it")
                        },
                        label = "Введите t среды (оС)",
                        imeAction = ImeAction.Next,
                        onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    CustomOutlinedTextField(
                        value = data.tasp.value,
                        onValueChange = {
                            data.tasp.value = it
                            Log.d("CalculationScreen", "Добавлено значение t асп: $it")
                        },
                        label = "Введите t асп (оС)",
                        imeAction = ImeAction.Next,
                        onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    CustomOutlinedTextField(
                        value = data.preom.value,
                        onValueChange = {
                            data.preom.value = it
                            Log.d("CalculationScreen", "Добавлено значение P реом: $it")
                        },
                        label = "Введите P реом (мм. рт. ст.)",
                        imeAction = ImeAction.Next,
                        onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                speeds.forEachIndexed { index, speed ->
                    item {
                        CustomOutlinedTextField(
                            value = speed,
                            onValueChange = {
                                settingsViewModel.speeds[index] = it
                                Log.d("CalculationScreen", "Добавлено значение скорости V${index + 1}: $it")
                            },
                            label = "Введите V${index + 1} (м/с)",
                            imeAction = if (index == speeds.size - 1) ImeAction.Done else ImeAction.Next,
                            onImeAction = {
                                if (index == speeds.size - 1) {
                                    focusManager.clearFocus()
                                } else {
                                    focusManager.moveFocus(FocusDirection.Down)
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                item {
                    Button(
                        onClick = {
                            calculationViewModel.calculateResult(
                                data.patm.value.toDoubleOrNull(),
                                settingsViewModel.speeds.map { it.toDoubleOrNull() ?: 0.0 },
                                data.plsr.value.toDoubleOrNull(),
                                data.tsr.value.toDoubleOrNull(),
                                data.tasp.value.toDoubleOrNull(),
                                data.preom.value.toDoubleOrNull(),
                                settingsViewModel
                            )
                            navController.navigate("resultScreen/0")
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Вычислить")
                    }
                }
            }
        } else if (selectedTabIndex == 1) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                TextButton(
                    onClick = { isExpanded = !isExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (isExpanded) "Скрыть данные для вычисления" else "Показать данные для вычисления",
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                AnimatedVisibility(visible = isExpanded) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            CustomOutlinedTextField(
                                value = data.patm.value,
                                onValueChange = {
                                    data.patm.value = it
                                    Log.d("CalculationScreen", "Добавлено значение P атм: $it")
                                },
                                label = "Введите P атм. (мм. рт. ст.)",
                                imeAction = ImeAction.Next,
                                onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            CustomOutlinedTextField(
                                value = data.plsr.value,
                                onValueChange = {
                                    data.plsr.value = it
                                    Log.d("CalculationScreen", "Добавлено значение Р среды: $it")
                                },
                                label = "Введите Р среды (мм.вод.ст.)",
                                imeAction = ImeAction.Next,
                                onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            CustomOutlinedTextField(
                                value = data.tsr.value,
                                onValueChange = {
                                    data.tsr.value = it
                                    Log.d("CalculationScreen", "Добавлено значение t среды: $it")
                                },
                                label = "Введите t среды (оС)",
                                imeAction = ImeAction.Next,
                                onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            CustomOutlinedTextField(
                                value = data.tasp.value,
                                onValueChange = {
                                    data.tasp.value = it
                                    Log.d("CalculationScreen", "Добавлено значение t асп: $it")
                                },
                                label = "Введите t асп (оС)",
                                imeAction = ImeAction.Next,
                                onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            CustomOutlinedTextField(
                                value = data.preom.value,
                                onValueChange = {
                                    data.preom.value = it
                                    Log.d("CalculationScreen", "Добавлено значение P реом: $it")
                                },
                                label = "Введите P реом (мм. рт. ст.)",
                                imeAction = ImeAction.Next,
                                onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        speeds.forEachIndexed { index, speed ->
                            item {
                                CustomOutlinedTextField(
                                    value = speed,
                                    onValueChange = {
                                        settingsViewModel.speeds[index] = it
                                        Log.d("CalculationScreen", "Добавлено значение скорости V${index + 1}: $it")
                                    },
                                    label = "Введите V${index + 1} (м/с)",
                                    imeAction = if (index == speeds.size - 1) ImeAction.Done else ImeAction.Next,
                                    onImeAction = {
                                        if (index == speeds.size - 1) {
                                            focusManager.clearFocus()
                                        } else {
                                            focusManager.moveFocus(FocusDirection.Down)
                                        }
                                    }
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text("Выберите наконечник для внутренней фильтрации", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                Log.d("CalculationScreen", "Значения из базы данных: ${filterTips.map { it.value }}")
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
                                    data.selectedInnerTip.value = item.value.toString()
                                    expanded = false
                                    data.isButtonVisible.value = true
                                    Log.d("CalculationScreen", "Выбранный наконечник: ${data.selectedInnerTip.value}")
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                AnimatedVisibility(visible = data.isButtonVisible.value) {
                    Button(
                        onClick = {
                            val selectedDiameter = data.selectedInnerTip.value.toDoubleOrNull()
                            if (selectedDiameter != null) {
                                calculationViewModel.calculateResult(
                                    data.patm.value.toDoubleOrNull(),
                                    settingsViewModel.speeds.map { it.toDoubleOrNull() ?: 0.0 },
                                    data.plsr.value.toDoubleOrNull(),
                                    data.tsr.value.toDoubleOrNull(),
                                    data.tasp.value.toDoubleOrNull(),
                                    data.preom.value.toDoubleOrNull(),
                                    settingsViewModel
                                )
                                calculationViewModel.calculateInnerTipVp(
                                    selectedDiameter,
                                    data.patm.value.toDoubleOrNull(),
                                    data.plsr.value.toDoubleOrNull(),
                                    data.tsr.value.toDoubleOrNull(),
                                    data.tasp.value.toDoubleOrNull(),
                                    data.preom.value.toDoubleOrNull(),
                                    settingsViewModel
                                )
                                navController.navigate("resultScreen/1")
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





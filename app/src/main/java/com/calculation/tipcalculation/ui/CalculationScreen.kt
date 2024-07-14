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
import com.calculation.tipcalculation.viewmodel.StateViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculationScreen(
    navController: NavController,
    stateViewModel: StateViewModel,
    calculationViewModel: CalculationViewModel,
    settingsViewModel: SettingsViewModel = viewModel()
) {
    val focusManager = LocalFocusManager.current
    val speeds by stateViewModel.speeds.observeAsState(emptyList())
    val inputFields = speeds.mapIndexed { index, value ->
        Pair(value, "Введите V${index + 1} (м/с)")
    } + listOf(
        Pair(stateViewModel.patm, "Введите P атм. (мм. рт. ст.)"),
        Pair(stateViewModel.plsr, "Введите Р среды (мм.вод.ст.)"),
        Pair(stateViewModel.tsr, "Введите t среды (оС)"),
        Pair(stateViewModel.tasp, "Введите t асп (оС)"),
        Pair(stateViewModel.preom, "Введите P реом (мм. рт. ст.)")
    )

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
                        stateViewModel.isButtonVisible = selectedTabIndex == 0 || selectedItemIndex >= 0
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
                inputFields.forEachIndexed { index, field ->
                    item {
                        CustomOutlinedTextField(
                            value = field.first,
                            onValueChange = { newValue ->
                                when (index) {
                                    in speeds.indices -> stateViewModel.updateSpeed(index, newValue)
                                    speeds.size -> stateViewModel.patm = newValue
                                    speeds.size + 1 -> stateViewModel.plsr = newValue
                                    speeds.size + 2 -> stateViewModel.tsr = newValue
                                    speeds.size + 3 -> stateViewModel.tasp = newValue
                                    speeds.size + 4 -> stateViewModel.preom = newValue
                                }
                            },
                            label = field.second,
                            imeAction = if (index == inputFields.size - 1) ImeAction.Done else ImeAction.Next,
                            onImeAction = {
                                if (index == inputFields.size - 1) {
                                    focusManager.clearFocus()
                                } else {
                                    focusManager.moveFocus(FocusDirection.Down)
                                }
                            }
                        )
                    }
                }
                item {
                    Button(
                        onClick = {
                            calculationViewModel.calculateResult(
                                stateViewModel.patm.toDoubleOrNull(),
                                stateViewModel.speeds.value?.map { it.toDoubleOrNull() } ?: emptyList(),
                                stateViewModel.plsr.toDoubleOrNull(),
                                stateViewModel.tsr.toDoubleOrNull(),
                                stateViewModel.tasp.toDoubleOrNull(),
                                stateViewModel.preom.toDoubleOrNull(),
                                stateViewModel
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
                        inputFields.forEachIndexed { index, field ->
                            item {
                                CustomOutlinedTextField(
                                    value = field.first,
                                    onValueChange = { newValue ->
                                        when (index) {
                                            in 0 until speeds.size -> stateViewModel.updateSpeed(index, newValue)
                                            speeds.size -> stateViewModel.patm = newValue
                                            speeds.size + 1 -> stateViewModel.plsr = newValue
                                            speeds.size + 2 -> stateViewModel.tsr = newValue
                                            speeds.size + 3 -> stateViewModel.tasp = newValue
                                            speeds.size + 4 -> stateViewModel.preom = newValue
                                        }
                                    },
                                    label = field.second,
                                    imeAction = if (index == inputFields.size - 1) ImeAction.Done else ImeAction.Next,
                                    onImeAction = {
                                        if (index == inputFields.size - 1) {
                                            focusManager.clearFocus()
                                        } else {
                                            focusManager.moveFocus(FocusDirection.Down)
                                        }
                                    }
                                )
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
                                    stateViewModel.selectedInnerTip = item.value.toString()
                                    expanded = false
                                    stateViewModel.isButtonVisible = true
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                AnimatedVisibility(visible = stateViewModel.isButtonVisible) {
                    Button(
                        onClick = {
                            val selectedDiameter = stateViewModel.selectedInnerTip.toDoubleOrNull()
                            if (selectedDiameter != null) {
                                calculationViewModel.calculateResult(
                                    stateViewModel.patm.toDoubleOrNull(),
                                    stateViewModel.speeds.value?.map { it.toDoubleOrNull() } ?: emptyList(),
                                    stateViewModel.plsr.toDoubleOrNull(),
                                    stateViewModel.tsr.toDoubleOrNull(),
                                    stateViewModel.tasp.toDoubleOrNull(),
                                    stateViewModel.preom.toDoubleOrNull(),
                                    stateViewModel
                                )
                                calculationViewModel.calculateInnerTipVp(
                                    selectedDiameter,
                                    stateViewModel.patm.toDoubleOrNull(),
                                    stateViewModel.plsr.toDoubleOrNull(),
                                    stateViewModel.tsr.toDoubleOrNull(),
                                    stateViewModel.tasp.toDoubleOrNull(),
                                    stateViewModel.preom.toDoubleOrNull(),
                                    stateViewModel
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


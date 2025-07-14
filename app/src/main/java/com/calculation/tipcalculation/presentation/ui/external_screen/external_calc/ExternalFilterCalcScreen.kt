package com.calculation.tipcalculation.presentation.ui.external_screen.external_calc

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.calculation.tipcalculation.R
import com.calculation.tipcalculation.presentation.components.*
import com.calculation.tipcalculation.presentation.navigation.Screen
import com.calculation.tipcalculation.presentation.ui.external_screen.model.InputFieldState
import com.calculation.tipcalculation.presentation.view_model.external_screen.external_calc_screen.ExternalCalcViewModel

@Composable
fun ExternalFilterCalcScreen(
    navController: NavController,
    viewModel: ExternalCalcViewModel = hiltViewModel()
) {
    val savedResult = viewModel.savedResult.collectAsState().value
    val speedCount by viewModel.speedCount.collectAsState()
    val focusManager = LocalFocusManager.current
    val showExitDialog = remember { mutableStateOf(false) }

    BackHandler(enabled = true) {
        showExitDialog.value = true
    }

    val fields = remember {
        listOf(
            InputFieldState("Введите P атм. (мм. рт. ст.)").apply {
                value = savedResult?.patm?.toString() ?: ""
            },
            InputFieldState("Введите Р среды (мм.вод.ст.)").apply {
                value = savedResult?.plsr?.toString() ?: ""
            },
            InputFieldState("Введите t среды (°C)").apply {
                value = savedResult?.tsr?.toString() ?: ""
            },
            InputFieldState("Введите t асп. (°C)").apply {
                value = savedResult?.tasp?.toString() ?: ""
            },
            InputFieldState("Введите P реом. (мм. рт. ст.)").apply {
                value = savedResult?.preom?.toString() ?: ""
            }
        )
    }

    val speedValues = remember(speedCount) {
        val savedSpeeds = savedResult?.speeds?.map { it.toString() }
        mutableStateListOf(*List(speedCount) { index ->
            savedSpeeds?.getOrNull(index) ?: ""
        }.toTypedArray())
    }

    val allFieldsCount = fields.size + speedValues.size
    val focusRequesters = remember(allFieldsCount) {
        List(allFieldsCount) { FocusRequester() }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Box(modifier = Modifier.fillMaxSize()) {
            CustomBackground2()

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    MainTopBarWithBackArrowAndCustomIcon(
                        title = "Расчёт фильтрации",
                        iconResId = R.drawable.external_transmission_svgrepo_com,
                        onBackClick = { showExitDialog.value = true },
                        onCustomIconClick = {}
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }

                items(fields.size) { index ->
                    InputFieldWithSpacer(
                        value = fields[index].value,
                        onValueChange = { fields[index].value = it },
                        placeholder = fields[index].placeholder,
                        focusRequester = focusRequesters[index],
                        onDone = {
                            if (index + 1 < focusRequesters.size) {
                                focusRequesters[index + 1].requestFocus()
                            } else {
                                focusManager.clearFocus()
                            }
                        }
                    )
                }

                items(speedValues.size) { index ->
                    val globalIndex = fields.size + index
                    InputFieldWithSpacerPositiveOnly(
                        value = speedValues[index],
                        onValueChange = { speedValues[index] = it },
                        placeholder = "Введите V${index + 1} (м/с)",
                        focusRequester = focusRequesters[globalIndex],
                        onDone = {
                            if (globalIndex + 1 < focusRequesters.size) {
                                focusRequesters[globalIndex + 1].requestFocus()
                            } else {
                                focusManager.clearFocus()
                            }
                        }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    GradientConfirmButton2(
                        modifier = Modifier.navigationBarsPadding(),
                        onClick = {
                            viewModel.calculateExternalResult(
                                fieldValues = fields.map { it.value },
                                speeds = speedValues
                            )
                            navController.navigate(Screen.ExternalResult.route)
                        }
                    )
                }
            }

            if (showExitDialog.value) {
                CustomAlertDialog(
                    titleText = "Выйти без сохранения?",
                    bodyText = "Если вы покинете экран, введённые данные будут утеряны.",
                    confirmButtonText = "Выйти",
                    cancelButtonText = "Остаться",
                    onDismissRequest = { showExitDialog.value = false },
                    onConfirm = {
                        showExitDialog.value = false
                        viewModel.clearResult()
                        navController.popBackStack()
                    },
                    onCancel = { showExitDialog.value = false }
                )
            }
        }
    }
}

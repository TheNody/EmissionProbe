package com.calculation.tipcalculation.presentation.ui.internal_screen.internal_calc

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.calculation.tipcalculation.R
import com.calculation.tipcalculation.presentation.components.CustomAlertDialog
import com.calculation.tipcalculation.presentation.components.CustomBackground2
import com.calculation.tipcalculation.presentation.components.FilterTipDropDownMenu
import com.calculation.tipcalculation.presentation.components.GradientConfirmButton2
import com.calculation.tipcalculation.presentation.components.InputFieldWithSpacer
import com.calculation.tipcalculation.presentation.components.InputFieldWithSpacerPositiveOnly
import com.calculation.tipcalculation.presentation.components.MainTopBarWithBackArrowAndCustomIcon
import com.calculation.tipcalculation.presentation.navigation.Screen
import com.calculation.tipcalculation.presentation.ui.internal_screen.model.InputFieldState
import com.calculation.tipcalculation.presentation.view_model.internal_screen.internal_calc_screen.InternalCalcViewModel

@Composable
fun InternalFilterCalcScreen(
    navController: NavController,
    viewModel: InternalCalcViewModel = hiltViewModel()
) {
    val filterTips by viewModel.filterTips.collectAsState()
    val savedResult = viewModel.savedResult.collectAsState().value
    val speedCount by viewModel.speedCount.collectAsState()
    val focusManager = LocalFocusManager.current

    val showExitDialog = remember { mutableStateOf(false) }

    var selectedTip by remember {
        mutableStateOf(savedResult?.selectedTip)
    }

    BackHandler(enabled = true) {
        showExitDialog.value = true
    }

    val fields = remember {
        listOf(
            InputFieldState("Введите P атм. (Па)").apply {
                value = savedResult?.patm?.toString() ?: ""
            },
            InputFieldState("Введите t среды (°C)").apply {
                value = savedResult?.tsr?.toString() ?: ""
            },
            InputFieldState("Введите t асп. (°C)").apply {
                value = savedResult?.tasp?.toString() ?: ""
            },
            InputFieldState("Введите P ср. (Па)").apply {
                value = savedResult?.plsr?.toString() ?: ""
            },
            InputFieldState("Введите P реом. (Па)").apply {
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

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            CustomBackground2()

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(horizontal = 24.dp)
            ) {
                item {
                    MainTopBarWithBackArrowAndCustomIcon(
                        title = "Расчёт фильтрации",
                        iconResId = R.drawable.internal_transmission_svgrepo_com,
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
                    FilterTipDropDownMenu(
                        tips = filterTips.map { it.value },
                        selectedTip = selectedTip,
                        onTipSelected = { selectedTip = it }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    GradientConfirmButton2(
                        modifier = Modifier.navigationBarsPadding(),
                        onClick = {
                            viewModel.calculateInternalResult(
                                selectedTip = selectedTip,
                                fieldValues = fields.map { it.value },
                                speeds = speedValues
                            )
                            navController.navigate(Screen.InternalResult.route)
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
                    onCancel = {
                        showExitDialog.value = false
                    }
                )
            }
        }
    }
}
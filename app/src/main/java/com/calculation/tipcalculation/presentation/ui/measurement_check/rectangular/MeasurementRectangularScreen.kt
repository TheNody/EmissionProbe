package com.calculation.tipcalculation.presentation.ui.measurement_check.rectangular

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
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
import com.calculation.tipcalculation.presentation.view_model.measurement_check.rectangular.RectangularSectionViewModel

@Composable
fun MeasurementRectangularScreen(
    navController: NavController,
    viewModel: RectangularSectionViewModel = hiltViewModel()
) {
    val input by viewModel.input.collectAsState()
    val focusManager = LocalFocusManager.current
    val showExitDialog = remember { mutableStateOf(false) }

    val focusRequesters = remember { List(3) { FocusRequester() } }

    var a by remember { mutableStateOf(input?.a ?: "") }
    var b by remember { mutableStateOf(input?.b ?: "") }
    var l by remember { mutableStateOf(input?.l ?: "") }

    BackHandler {
        showExitDialog.value = true
    }

    Surface(modifier = Modifier.fillMaxSize()) {
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
                        title = "Прямоугольное сечение",
                        iconResId = R.drawable.ic_measurement,
                        onBackClick = { showExitDialog.value = true },
                        onCustomIconClick = {}
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }

                item {
                    InputFieldWithSpacer(
                        value = a,
                        onValueChange = { a = it },
                        placeholder = "Ширина A (мм)",
                        focusRequester = focusRequesters[0],
                        onDone = { focusRequesters[1].requestFocus() }
                    )
                }

                item {
                    InputFieldWithSpacer(
                        value = b,
                        onValueChange = { b = it },
                        placeholder = "Высота B (мм)",
                        focusRequester = focusRequesters[1],
                        onDone = { focusRequesters[2].requestFocus() }
                    )
                }

                item {
                    InputFieldWithSpacer(
                        value = l,
                        onValueChange = { l = it },
                        placeholder = "Длина L (мм)",
                        focusRequester = focusRequesters[2],
                        onDone = { focusManager.clearFocus() }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    GradientConfirmButton2(
                        modifier = Modifier.navigationBarsPadding(),
                        onClick = {
                            viewModel.calculate(a, b, l)
                            focusManager.clearFocus()
                            navController.navigate(Screen.RectangularResult.route)
                        }
                    )
                }
            }

            if (showExitDialog.value) {
                CustomAlertDialog(
                    titleText = "Выйти без расчёта?",
                    bodyText = "Если вы покинете экран, введённые данные будут утеряны.",
                    confirmButtonText = "Выйти",
                    cancelButtonText = "Остаться",
                    onDismissRequest = { showExitDialog.value = false },
                    onConfirm = {
                        showExitDialog.value = false
                        viewModel.clear()
                        navController.popBackStack()
                    },
                    onCancel = { showExitDialog.value = false }
                )
            }
        }
    }
}

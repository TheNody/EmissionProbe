package com.calculation.tipcalculation.presentation.ui.measurement_check.rectangular

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.calculation.tipcalculation.R
import com.calculation.tipcalculation.presentation.components.CustomBackground2
import com.calculation.tipcalculation.presentation.components.InputFieldWithSpacer
import com.calculation.tipcalculation.presentation.components.MainTopBarWithBackArrowAndCustomIcon
import com.calculation.tipcalculation.presentation.view_model.measurement_check.rectangular.MeasurementPointViewModel

@Composable
fun MeasurementScreen(
    navController: NavController,
    viewModel: MeasurementPointViewModel = hiltViewModel()
) {
    val result by viewModel.state.collectAsState()

    var a by remember { mutableStateOf("") }
    var b by remember { mutableStateOf("") }
    var l by remember { mutableStateOf("") }

    val aFocus = remember { FocusRequester() }
    val bFocus = remember { FocusRequester() }
    val lFocus = remember { FocusRequester() }

    Surface(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            CustomBackground2()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                MainTopBarWithBackArrowAndCustomIcon(
                    title = "Прямоугольное сечение",
                    iconResId = R.drawable.ic_measurement,
                    onBackClick = { navController.popBackStack() },
                    onCustomIconClick = { /* можно открыть справку или FAQ */ }
                )

                Spacer(modifier = Modifier.height(24.dp))

                InputFieldWithSpacer(
                    value = a,
                    onValueChange = { a = it },
                    placeholder = "Ширина A (мм)",
                    focusRequester = aFocus,
                    onDone = { bFocus.requestFocus() }
                )

                InputFieldWithSpacer(
                    value = b,
                    onValueChange = { b = it },
                    placeholder = "Высота B (мм)",
                    focusRequester = bFocus,
                    onDone = { lFocus.requestFocus() }
                )

                InputFieldWithSpacer(
                    value = l,
                    onValueChange = { l = it },
                    placeholder = "Длина L (мм)",
                    focusRequester = lFocus,
                    onDone = {
                        val aVal = a.toDoubleOrNull()
                        val bVal = b.toDoubleOrNull()
                        val lVal = l.toDoubleOrNull()
                        if (aVal != null && bVal != null && lVal != null) {
                            viewModel.calculate(aVal, bVal, lVal)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        val aVal = a.toDoubleOrNull()
                        val bVal = b.toDoubleOrNull()
                        val lVal = l.toDoubleOrNull()
                        if (aVal != null && bVal != null && lVal != null) {
                            viewModel.calculate(aVal, bVal, lVal)
                        }
                    }
                ) {
                    Text("Рассчитать")
                }

                Spacer(modifier = Modifier.height(24.dp))

                result?.let {
                    Text("De: %.2f мм".format(it.de))
                    Text("L/De: %.2f".format(it.lOverDe))
                    Text("Категория: ${it.rule?.aspectCategory?.label}")
                    Text("Количество точек: ${it.rule?.totalPoints} (${it.rule?.na}×${it.rule?.nb})")
                    Text("Коэффициенты Ki: ${it.ki?.kValues?.joinToString(", ")}")
                }

                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}
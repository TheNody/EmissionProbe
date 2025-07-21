package com.calculation.tipcalculation.presentation.ui.measurement_check.round

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.calculation.tipcalculation.presentation.components.CustomBackground
import com.calculation.tipcalculation.presentation.components.InputFieldWithSpacer
import com.calculation.tipcalculation.presentation.components.MainTopBarWithBackArrowAndCustomIcon
import com.calculation.tipcalculation.presentation.view_model.measurement_check.round.RoundSectionViewModel

@Composable
fun MeasurementRoundScreen(
    navController: NavController,
    viewModel: RoundSectionViewModel = hiltViewModel()
) {
    val result by viewModel.state.collectAsState()

    var d by remember { mutableStateOf("") }
    var l by remember { mutableStateOf("") }

    val dFocus = remember { FocusRequester() }
    val lFocus = remember { FocusRequester() }

    Surface(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            CustomBackground()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .statusBarsPadding()
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                MainTopBarWithBackArrowAndCustomIcon(
                    title = "Круглое сечение",
                    iconResId = R.drawable.ic_measurement,
                    onBackClick = { navController.popBackStack() },
                    onCustomIconClick = { /* можно открыть справку */ }
                )

                Spacer(modifier = Modifier.height(24.dp))

                InputFieldWithSpacer(
                    value = d,
                    onValueChange = { d = it },
                    placeholder = "Диаметр D (мм)",
                    focusRequester = dFocus,
                    onDone = { lFocus.requestFocus() }
                )

                InputFieldWithSpacer(
                    value = l,
                    onValueChange = { l = it },
                    placeholder = "Длина L (мм)",
                    focusRequester = lFocus,
                    onDone = {
                        val dVal = d.toDoubleOrNull()
                        val lVal = l.toDoubleOrNull()
                        if (dVal != null && lVal != null) {
                            viewModel.calculate(dVal, lVal)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    val dVal = d.toDoubleOrNull()
                    val lVal = l.toDoubleOrNull()
                    if (dVal != null && lVal != null) {
                        viewModel.calculate(dVal, lVal)
                    }
                }) {
                    Text("Рассчитать")
                }

                Spacer(modifier = Modifier.height(24.dp))

                result?.let {
                    Text("De: %.2f мм".format(it.de))
                    Text("L/De: %.2f".format(it.lOverDe))
                    Text("Количество точек: ${it.rule.totalPoints} (по диаметру: ${it.rule.diameterPoints})")
                    it.ki?.let { ki ->
                        Text("Коэффициенты Ki: ${ki.kValues.joinToString(", ")}")
                    }
                }
            }
        }
    }
}

package com.calculation.tipcalculation.presentation.ui.internal_screen.internal_calc

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.calculation.tipcalculation.R
import com.calculation.tipcalculation.presentation.components.CustomBackground2
import com.calculation.tipcalculation.presentation.components.InputFieldWithSpacer
import com.calculation.tipcalculation.presentation.components.MainTopBarWithBackArrowAndCustomIcon
import com.calculation.tipcalculation.presentation.ui.internal_screen.model.InputFieldState
import com.calculation.tipcalculation.presentation.view_model.internal_screen.internal_calc_screen.InternalCalcViewModel

@Composable
fun InternalFilterCalcScreen(
    navController: NavController,
    viewModel: InternalCalcViewModel = hiltViewModel()
) {
    val speedCount by viewModel.speedCount.collectAsState()
    val focusManager = LocalFocusManager.current

    val fields = remember {
        listOf(
            InputFieldState("Введите P атм. (мм рт. ст.)"),
            InputFieldState("Введите t среды (°C)"),
            InputFieldState("Введите t асп. (°C)"),
            InputFieldState("Введите P ср. (мм вод. ст.)"),
            InputFieldState("Введите P реом. (мм рт. ст.)")
        )
    }

    val speedValues = remember(speedCount) {
        mutableStateListOf(*Array(speedCount) { "" })
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
                    .padding(horizontal = 24.dp, vertical = 10.dp)
            ) {
                item {
                    MainTopBarWithBackArrowAndCustomIcon(
                        title = "Расчёт фильтрации",
                        iconResId = R.drawable.internal_transmission_svgrepo_com,
                        onBackClick = { navController.popBackStack() },
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
                    InputFieldWithSpacer(
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
            }
        }
    }
}
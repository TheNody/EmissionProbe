package com.calculation.tipcalculation.presentation.ui.speed_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.calculation.tipcalculation.R
import com.calculation.tipcalculation.presentation.components.CustomBackground2
import com.calculation.tipcalculation.presentation.components.InnerShadowBoxWithButton
import com.calculation.tipcalculation.presentation.components.MainTopBarWithBackArrowAndCustomIcon
import com.calculation.tipcalculation.presentation.navigation.Screen
import com.calculation.tipcalculation.presentation.view_model.speed_count.SpeedCountViewModel

@Composable
fun SpeedCountScreen(
    navController: NavController,
    viewModel: SpeedCountViewModel = hiltViewModel()
) {
    val countValue by viewModel.speedValue.collectAsState()
    val isConfirmed by viewModel.isConfirmed.collectAsState()

    Surface {
        Box(modifier = Modifier.fillMaxSize()) {
            CustomBackground2()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 10.dp)
                    .statusBarsPadding()
            ) {
                MainTopBarWithBackArrowAndCustomIcon(
                    title = "Количество измерений",
                    iconResId = R.drawable.speed_count,
                    onBackClick = {
                        navController.navigate(Screen.Settings.route) {
                            popUpTo(Screen.SpeedCount.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onCustomIconClick = { }
                )

                Spacer(modifier = Modifier.height(24.dp))

                InnerShadowBoxWithButton(
                    value = countValue,
                    onValueChange = { viewModel.updateInput(it) },
                    onConfirm = { viewModel.insertOrUpdateSpeedCount() },
                    onStartEditing = { viewModel.startEditing() },
                    isConfirmed = isConfirmed,
                    placeholder = "Введите количество измерений"
                )
            }
        }
    }
}


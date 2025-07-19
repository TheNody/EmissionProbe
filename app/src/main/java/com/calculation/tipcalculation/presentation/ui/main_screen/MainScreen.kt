package com.calculation.tipcalculation.presentation.ui.main_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.calculation.tipcalculation.presentation.components.AppScaffold
import com.calculation.tipcalculation.presentation.components.CustomGradientButton
import com.calculation.tipcalculation.presentation.components.MainTopBar
import com.calculation.tipcalculation.presentation.navigation.Screen
import com.calculation.tipcalculation.utils.navigateSingleTopTo

@Composable
fun MainScreen(
    navController: NavHostController,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    AppScaffold(
        navController = navController,
        selectedIndex = selectedIndex,
        onTabSelected = onTabSelected
    ) {
        MainTopBar(
            title = "ООО «Аналитика»",
            icon = Icons.Default.Search,
            contentDescription = "Поиск",
            onIconClick = { /* TODO */ }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CustomGradientButton(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                text = "Внешняя фильтрация",
                onClick = {
                    navController.navigateSingleTopTo(Screen.ExternalCalc.route)
                }
            )

            CustomGradientButton(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                text = "Внутренняя фильтрация",
                onClick = {
                    navController.navigateSingleTopTo(Screen.InternalCalc.route)
                }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        CustomGradientButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            text = "Проверка участка замера",
            onClick = {
                navController.navigateSingleTopTo(Screen.MeasurementCheck.route)
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        CustomGradientButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            text = "Калькулятор",
            onClick = {
                navController.navigateSingleTopTo(Screen.Calculator.route)
            }
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}
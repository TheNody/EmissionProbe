package com.calculation.tipcalculation.presentation.ui.settings_screen

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

@Composable
fun SettingsScreen(
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
            title = "Настройки",
            icon = Icons.Default.Search,
            contentDescription = "Поиск",
            onIconClick = { /* Поиск */ }
        )

        Spacer(modifier = Modifier.height(24.dp))

        CustomGradientButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            text = "Количество измерений",
            onClick = {
                navController.navigate(Screen.SpeedCount.route)
            }
        )
    }
}


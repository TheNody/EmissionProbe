package com.calculation.tipcalculation.presentation.ui.used_documents

import androidx.compose.foundation.layout.*
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
fun UsedDocumentsScreen(
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
            title = "Используемые документы",
            icon = Icons.Default.Search,
            contentDescription = "Поиск",
            onIconClick = { /* TODO */ }
        )

        Spacer(modifier = Modifier.height(24.dp))

        CustomGradientButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            text = "ГОСТ 17.2.4.06-90",
            onClick = {
                navController.navigate(Screen.GOST17.route)
            }
        )
    }
}
package com.calculation.tipcalculation.presentation.ui.internal_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.calculation.tipcalculation.R
import com.calculation.tipcalculation.presentation.components.CustomBackground
import com.calculation.tipcalculation.presentation.components.TopBarWithDescription

@Composable
fun InternalCalcScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            CustomBackground()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(horizontal = 24.dp, vertical = 10.dp)
            ) {
                Spacer(modifier = Modifier.height(70.dp))

                Text(
                    text = "Здесь будет экран расчёта внутренней фильтрации",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }

            TopBarWithDescription(
                title = "Внутренняя фильтрация",
                descriptionTitle = "CAMPAGNOLO - CENTAUR",
                descriptionText = "The Centaur groupset offers smooth shifting and a durable build, making it ideal for endurance rides. It includes a precise 11-speed drivetrain and robust internal cable routing for sleek performance.\n\nPerfect for competitive cyclists seeking performance without compromise.",
                iconResId = R.drawable.internal_transmission_svgrepo_com,
                onBackClick = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 24.dp)
                    .zIndex(1f)
            )
        }
    }
}

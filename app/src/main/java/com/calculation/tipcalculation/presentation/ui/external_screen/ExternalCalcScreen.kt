package com.calculation.tipcalculation.presentation.ui.external_screen

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
fun ExternalCalcScreen(navController: NavController) {
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
                    text = "Здесь будет экран расчёта внешней фильтрации",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }

            TopBarWithDescription(
                title = "Внешняя фильтрация",
                descriptionTitle = "PEUGEOT - LR01",
                descriptionText = "The LR01 uses the same design as the most iconic bikes from PEUGEOT Cycles’ 130-year history and combines it with agile, dynamic performance that's perfectly suited to navigating today’s cities.\n\nAs well as a lugged steel frame and iconic PEUGEOT black-and-white chequer design, this city bike also features a 16-speed Shimano Claris drivetrain.",
                iconResId = R.drawable.external_transmission_svgrepo_com,
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

package com.calculation.tipcalculation.presentation.ui.measurement_check

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.calculation.tipcalculation.R
import com.calculation.tipcalculation.presentation.components.CustomBackground
import com.calculation.tipcalculation.presentation.components.CustomGradientButton
import com.calculation.tipcalculation.presentation.components.MainTopBarWithBackArrowAndCustomIcon
import com.calculation.tipcalculation.presentation.navigation.Screen

@Composable
fun SectionTypeSelectionScreen(
    navController: NavController
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            CustomBackground()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(horizontal = 24.dp)
            ) {

                MainTopBarWithBackArrowAndCustomIcon(
                    title = "Проверка участка замера",
                    iconResId = R.drawable.ic_measurement,
                    onBackClick = { navController.popBackStack() },
                    onCustomIconClick = { /* Например, показать справку */ }
                )

                Spacer(modifier = Modifier.height(24.dp))

                CustomGradientButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    text = "Прямоугольное сечение",
                    onClick = {
                        navController.navigate(Screen.RectangularSection.route)
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                CustomGradientButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    text = "Круглое сечение",
                    onClick = {
                        navController.navigate(Screen.RoundSection.route)
                    }
                )
            }
        }
    }
}
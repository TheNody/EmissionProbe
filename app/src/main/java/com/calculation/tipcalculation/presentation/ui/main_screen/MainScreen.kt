package com.calculation.tipcalculation.presentation.ui.main_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.calculation.tipcalculation.presentation.components.CustomBackground
import com.calculation.tipcalculation.presentation.components.CustomGradientButton
import com.calculation.tipcalculation.presentation.components.MainTopBar
import com.calculation.tipcalculation.presentation.components.SlantedBottomBar
import com.calculation.tipcalculation.presentation.navigation.Screen

@Composable
fun MainScreen(
    navController: NavHostController,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    val bottomIcons = listOf(
        rememberVectorPainter(Icons.Default.Done),
        rememberVectorPainter(Icons.Default.ShoppingCart),
        rememberVectorPainter(Icons.Default.Person),
        rememberVectorPainter(Icons.Default.Settings)
    )

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
                            navController.navigate(Screen.ExternalCalc.route)
                        }
                    )

                    CustomGradientButton(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp),
                        text = "Внутренняя фильтрация",
                        onClick = {
                            navController.navigate(Screen.InternalCalc.route)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                CustomGradientButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    text = "Калькулятор",
                    onClick = {
                        navController.navigate(Screen.Calculator.route)
                    }
                )

                Spacer(modifier = Modifier.weight(1f))
            }

            Box(
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                SlantedBottomBar(
                    icons = bottomIcons,
                    selectedIndex = selectedIndex,
                    onItemSelected = onTabSelected
                )
            }
        }
    }
}
//package com.calculation.tipcalculation.ui
//
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.ArrowForward
//import androidx.compose.material3.HorizontalDivider
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import com.calculation.tipcalculation.utils.ADVANCED_SETTINS_SCREEN
//import com.calculation.tipcalculation.utils.EXTERNAL_FILTER_SCREEN
//import com.calculation.tipcalculation.utils.INTERNAL_FILTER_SCREEN
//import com.calculation.tipcalculation.utils.MEASUREMENT_COUNT_SCREEN
//
//@Composable
//fun SettingsScreen(navController: NavController) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth(),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(
//                text = "Настройки",
//                fontSize = 30.sp,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
//            )
//        }
//
//        SettingTab(title = "Наконечники для внутренней фильтрации") {
//            navController.navigate(INTERNAL_FILTER_SCREEN)
//        }
//        HorizontalDivider(color = Color.Black, thickness = 1.dp)
//
//        SettingTab(title = "Наконечники для внешней фильтрации") {
//            navController.navigate(EXTERNAL_FILTER_SCREEN)
//        }
//        HorizontalDivider(color = Color.Black, thickness = 1.dp)
//
//        SettingTab(title = "Количество измерений(скоростей)") {
//            navController.navigate(MEASUREMENT_COUNT_SCREEN)
//        }
//        HorizontalDivider(color = Color.Black, thickness = 1.dp)
//
//        SettingTab(title = "Продвинутые настройки") {
//            navController.navigate(ADVANCED_SETTINS_SCREEN)
//        }
//        HorizontalDivider(color = Color.Black, thickness = 1.dp)
//    }
//}
//
//@Composable
//fun SettingTab(title: String, onClick: () -> Unit) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable { onClick() }
//            .padding(vertical = 16.dp),
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        Text(
//            text = title,
//            fontSize = 16.sp,
//            modifier = Modifier.padding(start = 16.dp)
//        )
//        Icon(
//            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
//            contentDescription = "Arrow Forward",
//            modifier = Modifier.padding(end = 16.dp)
//        )
//    }
//}
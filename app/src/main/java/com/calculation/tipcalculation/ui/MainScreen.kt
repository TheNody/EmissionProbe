//package com.calculation.tipcalculation.ui
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.res.vectorResource
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import com.calculation.tipcalculation.R
//import com.calculation.tipcalculation.screen_comp.DialogWithButton
//import com.calculation.tipcalculation.screen_comp.IconBox
//import com.calculation.tipcalculation.viewmodel.CalculationViewModel
//
//@Composable
//fun MainScreen(
//    navController: NavController,
//    calculationViewModel : CalculationViewModel,
//) {
//
//    val openAlertEmptyString = remember { mutableStateOf(false) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Top
//    ) {
//        Row(
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.Top
//        ) {
//            IconBox(
//                icon = ImageVector.vectorResource(id = R.drawable.external_transmission_svgrepo_com),
//                label = "Внешняя фильтрация",
//                onClick = { navController.navigate(EXTERNAL_CALCULATION_SCREEN) }
//            )
//            Spacer(modifier = Modifier.width(5.dp))
//            IconBox(
//                icon = ImageVector.vectorResource(id = R.drawable.internal_transmission_svgrepo_com),
//                label = "Внутренняя фильтрация",
//                onClick = { navController.navigate(INTERNAL_CALCULATION_SCREEN) }
//            )
//        }
//
//        Spacer(modifier = Modifier.height(10.dp))
//
//        if (openAlertEmptyString.value) {
//
//            DialogWithButton(
//                onDismissRequest = {openAlertEmptyString.value = false},
//                navController = navController,
//                calculationViewModel.returnVariantNumber()
//            )
//        }
//
//        Row(
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.Top
//        ) {
//            IconBox(
//                icon = ImageVector.vectorResource(id = R.drawable.result_svgrepo_com),
//                label = "Результаты",
//                onClick = {
//                    openAlertEmptyString.value = true
//                }
//            )
//            Spacer(modifier = Modifier.width(10.dp))
//            IconBox(
//                icon = ImageVector.vectorResource(id = R.drawable.save_svgrepo_com),
//                label = "Сохранённые результаты",
//                onClick = { navController.navigate(HISTORY_SCREEN) }
//            )
//        }
//        Spacer(modifier = Modifier.height(16.dp))
//        IconBox(
//            icon = ImageVector.vectorResource(id = R.drawable.settings_svgrepo_com),
//            label = "Настройки",
//            onClick = { navController.navigate(SETTINGS_SCREEN) },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 16.dp)
//        )
//    }
//}
//

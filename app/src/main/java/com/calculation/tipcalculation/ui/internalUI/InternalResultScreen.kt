package com.calculation.tipcalculation.ui.internalUI

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.calculation.tipcalculation.db_Main.SettingsViewModel
import com.calculation.tipcalculation.screen_comp.ResultCard
import com.calculation.tipcalculation.utils.HISTORY_SCREEN
import java.util.Locale

@Composable
fun InternalResultScreen(
    settingsViewModel: SettingsViewModel,
    navController: NavController
) {
    val data = settingsViewModel.data
    val reportDataState = settingsViewModel.reportData
    val reportData = reportDataState.value

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                ResultCard(
                    title = "Идеальный наконечник",
                    value = String.format(Locale.getDefault(), "%.2f", data.average.value)
                )
            }
            item {
                ResultCard(
                    title = "Выбранный диаметр",
                    value = String.format(Locale.getDefault(), "%.2f", data.selectedDiameter.value)
                )
            }
            item {
                ResultCard(
                    title = "vp выбранного наконечника",
                    value = String.format(Locale.getDefault(), "%.2f", data.vpOfSelectedDiameter.value)
                )
            }
            item {
                Button(onClick = {
                    try {
                        reportData?.let {
                            settingsViewModel.saveReportData(it)
                            navController.navigate(HISTORY_SCREEN)
                        } ?: Log.e("InternalResultScreen", "Нет данных для сохранения")
                    } catch (e: Exception) {
                        Log.e("InternalResultScreen", "Ошибка при сохранении данных", e)
                    }
                }) {
                    Text("Сохранить результаты")
                }
            }
        }
    }
}


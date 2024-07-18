package com.calculation.tipcalculation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.calculation.tipcalculation.db_Main.SettingsViewModel
import com.calculation.tipcalculation.screen_comp.ResultCard
import java.util.Locale

@Composable
fun InternalResultScreen(settingsViewModel: SettingsViewModel) {
    val data = settingsViewModel.data

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
        }
    }
}

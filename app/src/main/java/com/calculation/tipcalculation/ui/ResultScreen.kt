package com.calculation.tipcalculation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.calculation.tipcalculation.db_Main.SettingsViewModel
import com.calculation.tipcalculation.screen_comp.ExpandableTable
import com.calculation.tipcalculation.screen_comp.ResultCard
import com.calculation.tipcalculation.screen_comp.Table
import com.calculation.tipcalculation.screen_comp.TableRow
import java.util.Locale

@Composable
fun ResultScreen(settingsViewModel: SettingsViewModel, tabIndex: Int) {
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(tabIndex) }
    val tabTitles = listOf("Внешняя фильтрация", "Внутренняя фильтрация")
    val data = settingsViewModel.data

    Column(modifier = Modifier.fillMaxSize()) {
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            edgePadding = 16.dp
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) }
                )
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (selectedTabIndex == 0) {
                item {
                    ResultCard(
                        title = "Vp",
                        value = String.format(Locale.getDefault(), "%.2f", data.selectedVp.value)
                    )
                }
                item {
                    ResultCard(
                        title = "Ближайший рассчитанный наконечник",
                        value = String.format(Locale.getDefault(), "%.2f", data.firstSuitableDiameter.value)
                    )
                }
                item {
                    ExpandableTable(title = "Показать данные") {
                        Table {
                            TableRow("Vср. (м/с)", "${String.format(Locale.getDefault(), "%.2f", data.srznach.value)} ± ${String.format(Locale.getDefault(), "%.2f", data.sigma.value)}")
                            TableRow("d идеальный", String.format(Locale.getDefault(), "%.2f", data.average.value))
                            TableRow("d реал", String.format(Locale.getDefault(), "%.2f", data.tipSize.value))
                            TableRow("P атм", String.format(Locale.getDefault(), "%.2f", data.patm.value))
                            TableRow("V aсп усл", String.format(Locale.getDefault(), "%.2f", data.aspUsl.value))
                            TableRow("P асп, мм вод.ст. ВП-20", String.format(Locale.getDefault(), "%.2f", data.result.value))
                            TableRow("V aсп усл1", String.format(Locale.getDefault(), "%.2f", data.aspUsl1.value))
                            TableRow("d усл2", String.format(Locale.getDefault(), "%.2f", data.duslov1.value))
                            TableRow("d реал", String.format(Locale.getDefault(), "%.2f", data.dreal.value))
                            TableRow("V aсп усл2", String.format(Locale.getDefault(), "%.2f", data.vsp2.value))
                            TableRow("Рассчитанный нак.", String.format(Locale.getDefault(), "%.2f", data.calculatedTip.value))
                            TableRow("Выбранный нак.", String.format(Locale.getDefault(), "%.2f", data.vibrNak.value))
                        }
                    }
                }
                item {
                    ExpandableTable(title = "Проверенные диаметры и Vp") {
                        Table {
                            data.checkedDiametersList.forEach { (diameter, vp) ->
                                TableRow(
                                    title = "Диаметр: ${String.format(Locale.getDefault(), "%.2f", diameter.value)}",
                                    value = "Vp: ${String.format(Locale.getDefault(), "%.2f", vp.value)}"
                                )
                            }
                        }
                    }
                }
            } else if (selectedTabIndex == 1) {
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
}


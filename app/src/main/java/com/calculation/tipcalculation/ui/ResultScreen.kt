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
import com.calculation.tipcalculation.screen_comp.ExpandableTable
import com.calculation.tipcalculation.screen_comp.ResultCard
import com.calculation.tipcalculation.screen_comp.Table
import com.calculation.tipcalculation.screen_comp.TableRow
import com.calculation.tipcalculation.viewmodel.StateViewModel
import java.util.Locale

@Composable
fun ResultScreen(viewModel: StateViewModel, tabIndex: Int) {
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(tabIndex) }
    val tabTitles = listOf("Внешняя фильтрация", "Внутренняя фильтрация")

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
                        value = String.format(Locale.getDefault(), "%.2f", viewModel.selectedVp)
                    )
                }
                item {
                    ResultCard(
                        title = "Ближайший рассчитанный наконечник",
                        value = String.format(Locale.getDefault(), "%.2f", viewModel.firstSuitableDiameter)
                    )
                }
                item {
                    ExpandableTable(title = "Показать данные") {
                        Table {
                            TableRow("Vср. (м/с)", "${String.format(Locale.getDefault(), "%.2f", viewModel.srznach)} ± ${String.format(Locale.getDefault(), "%.2f", viewModel.sigma)}")
                            TableRow("d идеальный", String.format(Locale.getDefault(), "%.2f", viewModel.average))
                            TableRow("d реал", String.format(Locale.getDefault(), "%.2f", viewModel.tipSize))
                            TableRow("P атм", viewModel.patm)
                            TableRow("V aсп усл", String.format(Locale.getDefault(), "%.2f", viewModel.aspUsl))
                            TableRow("P асп, мм вод.ст. ВП-20", String.format(Locale.getDefault(), "%.2f", viewModel.result))
                            TableRow("V aсп усл1", String.format(Locale.getDefault(), "%.2f", viewModel.aspUsl1))
                            TableRow("d усл2", String.format(Locale.getDefault(), "%.2f", viewModel.duslov1))
                            TableRow("d реал", String.format(Locale.getDefault(), "%.2f", viewModel.dreal))
                            TableRow("V aсп усл2", String.format(Locale.getDefault(), "%.2f", viewModel.vsp2))
                            TableRow("Рассчитанный нак.", String.format(Locale.getDefault(), "%.2f", viewModel.calculatedTip))
                            TableRow("Выбранный нак.", String.format(Locale.getDefault(), "%.2f", viewModel.vibrNak))
                        }
                    }
                }
                item {
                    ExpandableTable(title = "Проверенные диаметры и Vp") {
                        Table {
                            viewModel.checkedDiametersList.forEach { (diameter, vp) ->
                                TableRow(
                                    title = "Диаметр: ${String.format(Locale.getDefault(), "%.2f", diameter)}",
                                    value = "Vp: ${String.format(Locale.getDefault(), "%.2f", vp)}"
                                )
                            }
                        }
                    }
                }
            } else if (selectedTabIndex == 1) {
                item {
                    ResultCard(
                        title = "Идеальный наконечник",
                        value = String.format(Locale.getDefault(), "%.2f", viewModel.average)
                    )
                }
                item {
                    ResultCard(
                        title = "Выбранный диаметр",
                        value = String.format(Locale.getDefault(), "%.2f", viewModel.selectedDiameter)
                    )
                }
                item {
                    ResultCard(
                        title = "vp выбранного наконечника",
                        value = String.format(Locale.getDefault(), "%.2f", viewModel.vpOfSelectedDiameter)
                    )
                }
            }
        }
    }
}
package com.calculation.tipcalculation.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.calculation.tipcalculation.domain.model.ExternalResultHistory
import com.calculation.tipcalculation.domain.model.InternalResultHistory
import com.calculation.tipcalculation.presentation.theme.Typography

@Composable
fun InternalHistoryCard(item: InternalResultHistory) {
    Text("Исходные данные", style = Typography.headlineSmall)
    Spacer(modifier = Modifier.height(8.dp))

    ResultText("P атм", "${item.patm} мм рт. ст.")
    ResultText("t среды", "${item.tsr} °C")
    ResultText("t асп.", "${item.tasp} °C")
    ResultText("P ср.", "${item.plsr} мм вод. ст.")
    ResultText("P реом.", "${item.preom} мм рт. ст.")
    ResultText("Скорости", item.speeds.joinToString("; ") { "%.2f".format(it) })
    ResultText("Выбранный диаметр", "%.4f".format(item.selectedTip))

    Spacer(modifier = Modifier.height(16.dp))
    Text("Результат", style = Typography.headlineSmall)
    Spacer(modifier = Modifier.height(8.dp))

    ResultText("Средняя скорость", "%.4f м/с".format(item.averageSpeed))
    ResultText("Идеальный наконечник", "%.4f".format(item.averageTip))
    ResultText("Vp выбранного", "%.4f м/с".format(item.vp))
}

@Composable
fun ExternalHistoryCard(item: ExternalResultHistory) {
    Text("Исходные данные", style = Typography.headlineSmall)
    Spacer(modifier = Modifier.height(8.dp))

    ResultText("P атм", "%.2f мм рт. ст.".format(item.patm))
    ResultText("P ср.", "%.2f мм вод. ст.".format(item.plsr))
    ResultText("P реом.", "%.2f мм рт. ст.".format(item.preom))
    ResultText("t среды", "%.2f °C".format(item.tsr))
    ResultText("t асп.", "%.2f °C".format(item.tasp))
    ResultText("Скорости", item.speeds.joinToString("; ") { "%.2f".format(it) })

    Spacer(modifier = Modifier.height(16.dp))
    Text("Результат", style = Typography.headlineSmall)
    Spacer(modifier = Modifier.height(8.dp))

    ResultText("Средняя скорость", "%.2f м/с".format(item.srznach))
    ResultText("СКО", "%.3f".format(item.sko))
    ResultText("Σ отклонений", "%.2f".format(item.sigma))
    ResultText("Идеальный d", "%.2f".format(item.average))
    ResultText("Рассчитанный наконечник", "%.2f".format(item.calculatedTip))
    ResultText("Размер наконечника", "%.2f".format(item.tipSize))
    ResultText("Vp выбранного", "%.2f".format(item.selectedVp))
    ResultText("Vp усл.", "%.2f".format(item.aspUsl))
    ResultText("P асп, мм вод. ст. ВП-20", "%.2f".format(item.result))
    ResultText("Vp усл. 1", "%.2f".format(item.aspUsl1))
    ResultText("d усл. 2", "%.2f".format(item.duslov1))
    ResultText("Выбранный наконечник", "%.2f".format(item.vibrNak))
    ResultText("d реальный", "%.2f".format(item.dreal))
    ResultText("Vp усл. 2", "%.2f".format(item.vsp2))
    ResultText("Ближайший к идеальному", "%.2f".format(item.closestDiameter))
    ResultText("Первый подходящий", "%.2f".format(item.firstSuitableDiameter))

    Spacer(modifier = Modifier.height(16.dp))
    Text("Проверенные диаметры и Vp", style = Typography.headlineSmall)

    item.checkedDiametersList.forEach { (d, vp) ->
        ResultText("Диаметр: %.2f".format(d), "Vp: %.2f".format(vp))
    }
}


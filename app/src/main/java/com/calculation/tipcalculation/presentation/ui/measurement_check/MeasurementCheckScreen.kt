package com.calculation.tipcalculation.presentation.ui.measurement_check

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import com.calculation.tipcalculation.presentation.components.CustomGradientButton
import com.calculation.tipcalculation.presentation.components.CustomBackground2
import com.calculation.tipcalculation.utils.GOSTMeasurementTable
import com.calculation.tipcalculation.utils.GostKiTable

@Composable
fun MeasurementCheckScreen() {
    var diameterInput by remember { mutableStateOf("") }
    var distanceInput by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("") }
    var lOverDe by remember { mutableStateOf<Float?>(null) }
    var lzOverDe by remember { mutableStateOf<Float?>(null) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            CustomBackground2()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = diameterInput,
                    onValueChange = { diameterInput = it },
                    label = { Text("Диаметр D (мм)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = distanceInput,
                    onValueChange = { distanceInput = it },
                    label = { Text("Расстояние L (мм)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                CustomGradientButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Проверить",
                    onClick = {
                        val D = diameterInput.toDoubleOrNull()
                        val L = distanceInput.toDoubleOrNull()

                        if (D == null || L == null || D <= 0 || L <= 0) {
                            resultText = "❗ Пожалуйста, введите корректные числовые значения (D > 0 и L > 0)."
                            lOverDe = null
                            lzOverDe = null
                            return@CustomGradientButton
                        }

                        val pi = Math.PI
                        val De = D / pi
                        val ratioLDe = L / De

                        val requiredLz = if (ratioLDe < 8) 0.35 * L - 0.4 else 0.0
                        val ratioLzDe = if (ratioLDe < 8) requiredLz / De else 0.0

                        lOverDe = ratioLDe.toFloat()
                        lzOverDe = ratioLzDe.toFloat()

                        val rule = GOSTMeasurementTable.findRule(D, ratioLDe)

                        val baseTotalPoints = rule?.totalPoints
                        val finalTotalPoints = if (ratioLDe < 4 && baseTotalPoints != null) baseTotalPoints * 2 else baseTotalPoints
                        val diameterPoints = if (ratioLDe < 4) rule?.diameterPoints?.times(2) else rule?.diameterPoints

                        val ki = finalTotalPoints?.let { GostKiTable.getByTotalPoints(it) }

                        resultText = buildString {
                            appendLine("🔎 ПОДРОБНЫЕ РЕЗУЛЬТАТЫ:")
                            appendLine()
                            appendLine("📐 Введённый диаметр трубы (D):")
                            appendLine("→ D = %.2f мм".format(D))
                            appendLine()
                            appendLine("📏 Расстояние до измерительной точки (L):")
                            appendLine("→ L = %.2f мм".format(L))
                            appendLine()
                            appendLine("🔄 Эквивалентный диаметр (De = D / π):")
                            appendLine("→ π ≈ %.5f".format(pi))
                            appendLine("→ De = %.2f мм".format(De))
                            appendLine()
                            appendLine("📊 Отношение длины до точки к эквивалентному диаметру:")
                            appendLine("→ L / De = %.2f".format(ratioLDe))
                            appendLine()

                            if (ratioLDe >= 8) {
                                appendLine("✅ Участок соответствует ГОСТ 17.2.4.06-90")
                                appendLine("→ Прямолинейный участок достаточной длины.")
                                appendLine("→ Дополнительный участок Lz не требуется.")
                            } else {
                                appendLine("❌ Участок НЕ соответствует ГОСТ 17.2.4.06-90")
                                appendLine("→ Требуется компенсирующий участок после точки измерения.")
                                appendLine()
                                appendLine("📌 Расчёт по ГОСТ-графику (черт. 3):")
                                appendLine("→ Lz = 0.35 × L − 0.4 = %.2f мм".format(requiredLz))
                                appendLine("→ Отношение Lz / De = %.2f".format(ratioLzDe))
                                appendLine()
                                appendLine("📤 РЕЗУЛЬТАТ:")
                                appendLine("→ 📏 Требуемая длина участка после измерения (Lz):")
                                appendLine("   ✅ Lz = %.2f мм".format(requiredLz))
                                appendLine()

                                if (L > requiredLz) {
                                    appendLine("✅ ГОСТ п.2.2: условие L > Lz соблюдено.")
                                } else {
                                    appendLine("❌ ГОСТ п.2.2: L ≤ Lz — нарушено.")
                                    appendLine("→ Не рекомендуется проводить измерения в данной точке.")
                                }

                                appendLine()
                                if (ratioLDe < 4) {
                                    appendLine("⚠ ГОСТ п.2.3: L / De < 4")
                                    appendLine("→ Требуется увеличить число точек измерения в 2 раза.")
                                } else {
                                    appendLine("✅ ГОСТ п.2.3: минимальная длина участка соблюдена.")
                                }

                                appendLine()
                            }

                            appendLine("📏 Рекомендуемое количество точек измерения по ГОСТ:")
                            appendLine("→ Всего точек: ${finalTotalPoints ?: "—"} (по диаметру: ${diameterPoints ?: "—"})")

                            if (ratioLDe < 4) {
                                appendLine("⚠ Увеличено в 2 раза из-за короткой длины (L / De < 4)")
                            }

                            if (ki != null) {
                                appendLine()
                                appendLine("📈 Коэффициенты Ki:")
                                ki.kValues.forEachIndexed { index, value ->
                                    appendLine("→ Ki[${index + 1}] = %.2f".format(value))
                                }
                            }
                        }
                    }
                )


                Spacer(modifier = Modifier.height(24.dp))

                LzGraphChart(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    lOverDePoint = lOverDe,
                    lzOverDePoint = lzOverDe
                )

                if (resultText.isNotEmpty()) {
                    Text(
                        text = resultText,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun LzGraphChart(
    modifier: Modifier = Modifier,
    lOverDePoint: Float? = null,
    lzOverDePoint: Float? = null
) {
    val dataPoints = List(12) { i ->
        val lOverDe = 2.5f + i * 0.5f
        val lzOverDe = 0.35f * lOverDe - 0.4f
        lOverDe to lzOverDe
    }

    Canvas(modifier = modifier.height(250.dp).fillMaxWidth()) {
        val padding = 40f
        val xMin = 2.5f
        val xMax = 10f
        val yMax = 3f
        val yMin = 0f

        val xScale = (size.width - padding * 2) / (xMax - xMin)
        val yScale = (size.height - padding * 2) / (yMax - yMin)

        // Сетка
        for (i in 0..(xMax - xMin).toInt()) {
            val x = padding + i * xScale
            drawLine(Color.LightGray, Offset(x, padding), Offset(x, size.height - padding))
        }

        for (j in 0..yMax.toInt()) {
            val y = size.height - padding - j * yScale
            drawLine(Color.LightGray, Offset(padding, y), Offset(size.width - padding, y))
        }

        // Оси
        drawLine(Color.Black, Offset(padding, size.height - padding), Offset(size.width - padding, size.height - padding), strokeWidth = 3f)
        drawLine(Color.Black, Offset(padding, size.height - padding), Offset(padding, padding), strokeWidth = 3f)

        // Подписи
        drawContext.canvas.nativeCanvas.apply {
            drawText("L", size.width / 2, size.height - 5f, android.graphics.Paint().apply {
                color = android.graphics.Color.BLACK
                textSize = 32f
                textAlign = android.graphics.Paint.Align.CENTER
            })
            drawText("Lz", 5f, size.height / 2, android.graphics.Paint().apply {
                color = android.graphics.Color.BLACK
                textSize = 32f
                textAlign = android.graphics.Paint.Align.LEFT
            })
        }

        // Метки X
        val xLabels = listOf(2.5f, 4f, 5f, 6f, 8f)
        xLabels.forEach { label ->
            val x = padding + (label - xMin) * xScale
            drawContext.canvas.nativeCanvas.drawText(
                "%.1f".format(label),
                x,
                size.height - 10f,
                android.graphics.Paint().apply {
                    color = android.graphics.Color.BLACK
                    textSize = 24f
                    textAlign = android.graphics.Paint.Align.CENTER
                }
            )
        }

        // Метки Y
        val yLabels = listOf(0f, 1f, 2f, 3f)
        yLabels.forEach { label ->
            val y = size.height - padding - (label - yMin) * yScale
            drawContext.canvas.nativeCanvas.drawText(
                "%.0f".format(label),
                padding - 10f,
                y + 8f,
                android.graphics.Paint().apply {
                    color = android.graphics.Color.BLACK
                    textSize = 24f
                    textAlign = android.graphics.Paint.Align.RIGHT
                }
            )
        }

        // Линия графика Lz = 0.35 * L - 0.4
        for (i in 0 until dataPoints.size - 1) {
            val (x1, y1) = dataPoints[i]
            val (x2, y2) = dataPoints[i + 1]
            drawLine(
                color = Color.Blue,
                start = Offset(padding + (x1 - xMin) * xScale, size.height - padding - (y1 - yMin) * yScale),
                end = Offset(padding + (x2 - xMin) * xScale, size.height - padding - (y2 - yMin) * yScale),
                strokeWidth = 4f
            )
        }

        // Точка пользователя
        if (lOverDePoint != null && lzOverDePoint != null) {
            val x = padding + (lOverDePoint - xMin) * xScale
            val y = size.height - padding - (lzOverDePoint - yMin) * yScale

            drawCircle(
                color = Color.Red,
                radius = 8f,
                center = Offset(x, y)
            )
        }
    }
}
package com.calculation.tipcalculation.ui

import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.calculation.tipcalculation.R
import com.calculation.tipcalculation.db_Main.SettingsViewModel
import com.calculation.tipcalculation.db_Main.historyData.ReportDataEntity
import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.VerticalAlignment
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.text.DecimalFormat
import java.util.Locale

@ExperimentalFoundationApi
@Composable
fun HistoryScreen(settingsViewModel: SettingsViewModel) {
    val reportDataEntityList by settingsViewModel.reportDataList.observeAsState(emptyList())
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(reportDataEntityList, key = { it.id }) { reportDataEntity ->
            var isExpanded by remember { mutableStateOf(false) }
            var isHolding by remember { mutableStateOf(false) }
            val animatedProgress by animateFloatAsState(
                targetValue = if (isHolding) 1f else 0f,
                animationSpec = tween(durationMillis = 1000), label = ""
            )
            var isVisible by remember { mutableStateOf(true) }

            LaunchedEffect(animatedProgress) {
                if (animatedProgress == 1f) {
                    settingsViewModel.deleteReportData(reportDataEntity)
                    isVisible = false
                }
            }

            AnimatedVisibility(
                visible = isVisible,
                exit = fadeOut(animationSpec = tween(durationMillis = 1000))
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .combinedClickable(
                            onClick = {
                                isExpanded = !isExpanded
                            },
                            onLongClick = {
                                isHolding = true
                            }
                        )
                        .border(
                            BorderStroke(2.dp, if (isHolding) Color.Red else Color.Transparent),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = {
                                    isHolding = true
                                    tryAwaitRelease()
                                    isHolding = false
                                }
                            )
                        },
                    elevation = cardElevation(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = reportDataEntity.title,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = if (isExpanded) "Скрыть данные" else "Показать данные",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.Gray,
                                modifier = Modifier
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ) {
                                        isExpanded = !isExpanded
                                    }
                            )
                            if (isExpanded) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = "Patm: ${String.format(Locale.getDefault(), "%.3f", reportDataEntity.patm)}")
                                Text(text = "Tsr: ${String.format(Locale.getDefault(), "%.3f", reportDataEntity.tsr)}")
                                Text(text = "Tasp: ${String.format(Locale.getDefault(), "%.3f", reportDataEntity.tasp)}")
                                Text(text = "Plsr: ${String.format(Locale.getDefault(), "%.3f", reportDataEntity.plsr)}")
                                Text(text = "Measurement Count: ${reportDataEntity.measurementCount}")
                                Text(text = "Average Speed: ${String.format(Locale.getDefault(), "%.2f", reportDataEntity.averageSpeed)}")
                                Text(text = "Calculated Tip: ${String.format(Locale.getDefault(), "%.3f", reportDataEntity.calculatedTip)}")
                                Text(text = "First Suitable Tip: ${String.format(Locale.getDefault(), "%.3f", reportDataEntity.firstSuitableTip)}")
                                Text(text = "СКО: ${String.format(Locale.getDefault(), "%.3f", reportDataEntity.sko)}")
                            }
                        }
                        Box(
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.Top)
                                .clickable {
                                    generateExcelFile(context, reportDataEntity, reportDataEntity.title)
                                }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.excel_icon),
                                contentDescription = "Excel Icon",
                                modifier = Modifier.size(48.dp),
                                tint = Color(0xFF0F773D)
                            )
                        }
                    }

                    Canvas(modifier = Modifier.fillMaxSize()) {
                        if (isHolding) {
                            val centerX = size.width / 2
                            val startY = size.height
                            val endY = size.height * (1 - animatedProgress)
                            drawLine(
                                color = Color.Red,
                                start = Offset(centerX, startY),
                                end = Offset(centerX, endY),
                                strokeWidth = 5f
                            )
                        }
                    }
                }
            }
        }
    }
}

fun generateExcelFile(context: Context, reportData: ReportDataEntity, title: String) {
    val workbook: Workbook = XSSFWorkbook()
    val sheet = workbook.createSheet("Report Data")

    val headers = listOf(
        "№ п/п", "Место измерения","Ратм, кПа", "Ратм, кПа (с поправкой)", "Темп. г/х, оС", "Темп. перед ротаметром, оС",
        "Диаметр (размер) газохода (г/х), мм", "Кол-во точек изм. n", "Скорость в г/х, м/с", "Давление/разряжение в г/х, кПа",
        "Диаметр наконечника расч., мм", "Диаметр наконечника выбр., мм", "СКО, м/с"
    )

    val decimalFormat = DecimalFormat("#.###")
    val values = listOf(
        "", // № п/п
        "", // Место измерения
        decimalFormat.format(reportData.patm), //Ратм, кПа
        "", //Ратм, кПа (с поправкой)
        decimalFormat.format(reportData.tsr), // Темп. г/х, оС
        decimalFormat.format(reportData.tasp), // Темп. перед ротаметром, оС
        "", // Диаметр (размер) газохода (г/х), мм
        reportData.measurementCount.toString(), // Кол-во точек изм. n
        decimalFormat.format(reportData.averageSpeed), // Скорость в г/х, м/с
        decimalFormat.format(reportData.plsr), // Давление/разряжение в г/х, кПа
        decimalFormat.format(reportData.calculatedTip), // Диаметр наконечника расч., мм
        decimalFormat.format(reportData.firstSuitableTip), // Диаметр наконечника выбр., мм
        decimalFormat.format(reportData.sko)
    )

    val borderedStyle = workbook.createCellStyle().apply {
        borderTop = BorderStyle.THIN
        topBorderColor = IndexedColors.BLACK.index
        borderBottom = BorderStyle.THIN
        bottomBorderColor = IndexedColors.BLACK.index
        borderLeft = BorderStyle.THIN
        leftBorderColor = IndexedColors.BLACK.index
        borderRight = BorderStyle.THIN
        rightBorderColor = IndexedColors.BLACK.index
        alignment = HorizontalAlignment.CENTER
        verticalAlignment = VerticalAlignment.CENTER
        wrapText = true
    }

    val headerRow: Row = sheet.createRow(0)
    for (i in headers.indices) {
        val cell = headerRow.createCell(i)
        cell.setCellValue(headers[i])
        cell.cellStyle = borderedStyle
    }

    val valueRow: Row = sheet.createRow(1)
    for (i in values.indices) {
        val cell = valueRow.createCell(i)
        cell.setCellValue(values[i])
        cell.cellStyle = borderedStyle
    }

    for (i in headers.indices) {
        sheet.setColumnWidth(i, 4000)
    }

    try {
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "$title.xlsx")
        val fileOut = FileOutputStream(file)
        workbook.write(fileOut)
        fileOut.close()
        workbook.close()
        Log.d("Excel", "Excel file generated successfully at ${file.absolutePath}")
    } catch (e: Exception) {
        Log.e("Excel", "Error writing Excel file", e)
    }
}
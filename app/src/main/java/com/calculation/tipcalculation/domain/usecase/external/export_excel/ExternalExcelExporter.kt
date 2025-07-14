package com.calculation.tipcalculation.domain.usecase.external.export_excel

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import com.calculation.tipcalculation.domain.model.ExternalResultHistory
import com.calculation.tipcalculation.domain.model.ReportData
import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.VerticalAlignment
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.text.DecimalFormat

class ExternalExcelExporter {

    fun export(context: Context, data: ExternalResultHistory) {
        val reportData = ReportData(
            patm = data.patm,
            tsr = data.tsr,
            tasp = data.tasp,
            plsr = data.plsr,
            measurementCount = data.speeds.size,
            averageSpeed = data.srznach,
            calculatedTip = data.calculatedTip,
            firstSuitableTip = data.firstSuitableDiameter,
            sko = data.sko,
            timestamp = data.timestamp
        )

        val safeTimestamp = sanitizeTimestamp(reportData.timestamp)
        val fileName = "Отчёт_${safeTimestamp}.xlsx"

        val workbook: Workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Report Data")

        val headers = listOf(
            "№ п/п", "Место измерения", "Ратм, кПа", "Ратм, кПа (с поправкой)", "Темп. г/х, °С", "Темп. перед ротаметром, °С",
            "Диаметр газохода, мм", "Кол-во точек изм. n", "Скорость в г/х, м/с", "Давление/разряжение, кПа",
            "Диаметр наконечника расч., мм", "Диаметр наконечника выбр., мм", "СКО, м/с"
        )

        val df = DecimalFormat("#.###")
        val values = listOf(
            "", "", df.format(reportData.patm), "", df.format(reportData.tsr), df.format(reportData.tasp),
            "", reportData.measurementCount.toString(), df.format(reportData.averageSpeed), df.format(reportData.plsr),
            df.format(reportData.calculatedTip), df.format(reportData.firstSuitableTip), df.format(reportData.sko)
        )

        val style = workbook.createCellStyle().apply {
            borderTop = BorderStyle.THIN
            borderBottom = BorderStyle.THIN
            borderLeft = BorderStyle.THIN
            borderRight = BorderStyle.THIN
            alignment = HorizontalAlignment.CENTER
            verticalAlignment = VerticalAlignment.CENTER
            wrapText = true
        }

        sheet.createRow(0).apply {
            headers.forEachIndexed { i, h ->
                createCell(i).apply {
                    setCellValue(h)
                    cellStyle = style
                }
            }
        }

        sheet.createRow(1).apply {
            values.forEachIndexed { i, v ->
                createCell(i).apply {
                    setCellValue(v)
                    cellStyle = style
                }
            }
        }

        headers.indices.forEach { sheet.setColumnWidth(it, 4000) }

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                    put(MediaStore.MediaColumns.MIME_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + "/TipReports")
                }

                val uri = context.contentResolver.insert(
                    MediaStore.Downloads.EXTERNAL_CONTENT_URI,
                    contentValues
                ) ?: throw Exception("Не удалось создать URI для сохранения Excel-файла")

                context.contentResolver.openOutputStream(uri).use { outputStream ->
                    if (outputStream == null) throw Exception("Не удалось открыть OutputStream")
                    workbook.write(outputStream)
                }

                Log.d("Excel", "Файл успешно сохранён в MediaStore: $fileName")

            } else {
                val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val tipReportsDir = File(downloadsDir, "TipReports")
                if (!tipReportsDir.exists()) tipReportsDir.mkdirs()

                val file = File(tipReportsDir, fileName)
                file.outputStream().use { workbook.write(it) }

                Log.d("Excel", "Файл успешно сохранён: ${file.absolutePath}")
            }

        } catch (e: Exception) {
            Log.e("Excel", "Ошибка при сохранении Excel-файла: ${e.message}", e)
        } finally {
            workbook.close()
        }
    }

    private fun sanitizeTimestamp(timestamp: String): String {
        return timestamp
            .replace(":", "_")
            .replace(" ", "_")
            .replace("/", "_")
    }
}


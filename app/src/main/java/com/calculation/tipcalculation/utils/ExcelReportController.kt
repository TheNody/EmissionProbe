package com.calculation.tipcalculation.utils

import android.content.ActivityNotFoundException
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import com.calculation.tipcalculation.domain.model.ExternalResultHistory
import com.calculation.tipcalculation.domain.usecase.external.export_excel.AddExportedReportUseCase
import com.calculation.tipcalculation.domain.usecase.external.export_excel.ExportExternalResultToExcelUseCase
import com.calculation.tipcalculation.domain.usecase.external.export_excel.GetExportedReportsUseCase
import com.calculation.tipcalculation.domain.usecase.external.export_excel.RemoveExportedReportUseCase
import javax.inject.Inject

class ExcelReportController @Inject constructor(
    private val exportExcel: ExportExternalResultToExcelUseCase,
    private val addExported: AddExportedReportUseCase,
    private val removeExported: RemoveExportedReportUseCase,
    private val getExported: GetExportedReportsUseCase
) {

    fun saveReport(context: Context, data: ExternalResultHistory): Set<String> {
        exportExcel(context, data)
        addExported(data.timestamp)
        return getExported()
    }

    fun deleteReport(context: Context, timestamp: String): Set<String> {
        val safeTimestamp = timestamp.replace(":", "_").replace(" ", "_").replace("/", "_")
        val fileName = "Отчёт_${safeTimestamp}.xlsx"

        val resolver = context.contentResolver
        val collection = getDownloadsCollection()

        val cursor = resolver.query(
            collection,
            arrayOf(MediaStore.MediaColumns._ID),
            "${MediaStore.MediaColumns.DISPLAY_NAME} = ?",
            arrayOf(fileName),
            null
        )

        var updatedTimestamps = getExported()

        cursor?.use {
            if (it.moveToFirst()) {
                val id = it.getLong(it.getColumnIndexOrThrow(MediaStore.MediaColumns._ID))
                val uri = ContentUris.withAppendedId(collection, id)
                val deleted = resolver.delete(uri, null, null)

                if (deleted > 0) {
                    removeExported(timestamp)
                    updatedTimestamps = getExported()
                }
            }
        }

        return updatedTimestamps
    }

    fun openReport(context: Context, timestamp: String) {
        val safeTimestamp = timestamp.replace(":", "_").replace(" ", "_").replace("/", "_")
        val fileName = "Отчёт_${safeTimestamp}.xlsx"

        val resolver = context.contentResolver
        val collection = getDownloadsCollection()

        val cursor = resolver.query(
            collection,
            arrayOf(MediaStore.MediaColumns._ID, MediaStore.MediaColumns.DISPLAY_NAME),
            "${MediaStore.MediaColumns.DISPLAY_NAME} = ?",
            arrayOf(fileName),
            null
        )

        cursor?.use {
            if (it.moveToFirst()) {
                val id = it.getLong(it.getColumnIndexOrThrow(MediaStore.MediaColumns._ID))
                val uri = ContentUris.withAppendedId(collection, id)

                val intent = Intent(Intent.ACTION_VIEW).apply {
                    setDataAndType(uri, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                    flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK
                }

                try {
                    context.startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(context, "Нет приложения для открытия Excel-файлов", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Файл не найден", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getDownloadsCollection() =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Files.getContentUri("external")
        }
}
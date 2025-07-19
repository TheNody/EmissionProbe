package com.calculation.tipcalculation.data.local

import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import java.io.File
import androidx.core.content.edit

class ExportedReportsStore(context: Context) {

    private val prefs = context.getSharedPreferences("exported_reports", Context.MODE_PRIVATE)

    fun getTimestamps(): Set<String> {
        return prefs.getStringSet("timestamps", emptySet()) ?: emptySet()
    }

    fun addTimestamp(timestamp: String) {
        val set = getTimestamps().toMutableSet()
        set.add(timestamp)
        prefs.edit { putStringSet("timestamps", set) }
    }

    fun removeTimestamp(timestamp: String) {
        val set = getTimestamps().toMutableSet()
        set.remove(timestamp)
        prefs.edit { putStringSet("timestamps", set) }
    }

    fun invalidateDeletedReports(context: Context) {
        val validTimestamps = getTimestamps().toMutableSet()
        val iterator = validTimestamps.iterator()

        Log.d("InvalidateReports", "Начинаем проверку сохранённых отчётов...")
        Log.d("InvalidateReports", "Текущее количество сохранённых timestamps: ${validTimestamps.size}")

        while (iterator.hasNext()) {
            val timestamp = iterator.next()
            val safeFileName = "Отчёт_${timestamp.replace(":", "_").replace(" ", "_")}.xlsx"
            var fileExists = false

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                val resolver = context.contentResolver
                val collection = MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL)
                val projection = arrayOf(MediaStore.Downloads.DISPLAY_NAME)
                val selection = "${MediaStore.Downloads.DISPLAY_NAME} = ?"
                val selectionArgs = arrayOf(safeFileName)

                val cursor = resolver.query(collection, projection, selection, selectionArgs, null)

                if (cursor != null) {
                    fileExists = cursor.moveToFirst()
                    cursor.close()
                } else {
                    Log.e("InvalidateReports", "Ошибка запроса MediaStore: cursor == null")
                }
            } else {
                val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val reportFile = File(downloadsDir, "TipReports/$safeFileName")
                fileExists = reportFile.exists()
            }

            if (!fileExists) {
                Log.w("InvalidateReports", "Файл $safeFileName не найден — удаляем из prefs")
                iterator.remove()
            } else {
                Log.d("InvalidateReports", "Файл $safeFileName существует — оставляем в prefs")
            }
        }

        prefs.edit {
            putStringSet("timestamps", validTimestamps)
        }

        Log.d("InvalidateReports", "Финальный список timestamps: $validTimestamps")
    }
}

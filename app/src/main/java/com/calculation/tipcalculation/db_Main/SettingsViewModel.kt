package com.calculation.tipcalculation.db_Main

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.calculation.tipcalculation.db_Main.externalFilter.ExternalFilterTip
import com.calculation.tipcalculation.db_Main.externalFilter.ExternalFilterTipRepository
import com.calculation.tipcalculation.db_Main.historyData.ReportDataEntity
import com.calculation.tipcalculation.db_Main.historyData.ReportDataRepository
import com.calculation.tipcalculation.db_Main.internalFilter.FilterTip
import com.calculation.tipcalculation.db_Main.internalFilter.FilterTipRepository
import com.calculation.tipcalculation.db_Main.speedCount.SpeedCount
import com.calculation.tipcalculation.db_Main.speedCount.SpeedCountRepository
import com.calculation.tipcalculation.model.CalculationData
import com.calculation.tipcalculation.model.ReportData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val speedCountRepository: SpeedCountRepository
    private val externalFilterTipRepository: ExternalFilterTipRepository
    private val filterTipRepository: FilterTipRepository
    private val reportDataRepository: ReportDataRepository

    val allExternalFilterTips: LiveData<List<ExternalFilterTip>>
    val allFilterTips: LiveData<List<FilterTip>>
    private val speedCount: LiveData<SpeedCount?>
    var reportData: MutableState<ReportData?> = mutableStateOf(null)

    val speeds = mutableStateListOf<String>()
    var data by mutableStateOf(CalculationData())
    var enteredSpeedCount by mutableStateOf("")

    private val _reportDataList = MutableLiveData<List<ReportDataEntity>>()
    val reportDataList: LiveData<List<ReportDataEntity>> get() = _reportDataList

    init {
        val database = AppDatabase.getDatabase(application)
        speedCountRepository = SpeedCountRepository(database.speedDao())
        externalFilterTipRepository = ExternalFilterTipRepository(database.externalFilterTipDao())
        filterTipRepository = FilterTipRepository(database.filterTipDao())
        reportDataRepository = ReportDataRepository(database.reportDataDao())

        speedCount = speedCountRepository.measurementCount.asLiveData()
        allExternalFilterTips = externalFilterTipRepository.allExternalFilterTips
        allFilterTips = filterTipRepository.allFilterTips

        viewModelScope.launch {
            speedCountRepository.getSpeed()?.let {
                setSpeedCount(it.speedCount)
            }
        }

        loadAllReportData()
    }

    private fun loadAllReportData() = viewModelScope.launch(Dispatchers.IO) {
        val data = reportDataRepository.getAllReportData()
        _reportDataList.postValue(data)
    }

    fun insertSpeedCount(speedCount: Int) = viewModelScope.launch(Dispatchers.IO) {
        val speedEntity = SpeedCount(speedCount = speedCount)
        speedCountRepository.insert(speedEntity)
        withContext(Dispatchers.Main) {
            setSpeedCount(speedCount)
        }
    }

    fun deleteAllSpeedCount() = viewModelScope.launch(Dispatchers.IO) {
        speedCountRepository.deleteAll()
        withContext(Dispatchers.Main) {
            speeds.clear()
            enteredSpeedCount = ""
        }
    }

    private fun setSpeedCount(count: Int) {
        speeds.clear()
        speeds.addAll(List(count) { "" })
        enteredSpeedCount = count.toString()
        Log.d("SettingsViewModel", "Speed count set to $count")
    }

    fun resetValues() {
        data = CalculationData()
    }

    fun insertExternalFilterTip(externalFilterTip: ExternalFilterTip) = viewModelScope.launch(Dispatchers.IO) {
        externalFilterTipRepository.insert(externalFilterTip)
        Log.d("SettingsViewModel", "ExternalFilterTip сохранен: ${externalFilterTip.value}")

        allExternalFilterTips.value?.let { tips ->
            Log.d("SettingsViewModel", "Все ExternalFilterTip: ${tips.map { it.value }}")
        }
    }

    fun deleteExternalFilterTip(externalFilterTip: ExternalFilterTip) = viewModelScope.launch(Dispatchers.IO) {
        externalFilterTipRepository.delete(externalFilterTip)
        Log.d("SettingsViewModel", "ExternalFilterTip удален: ${externalFilterTip.value}")

        allExternalFilterTips.value?.let { tips ->
            Log.d("SettingsViewModel", "Все ExternalFilterTip: ${tips.map { it.value }}")
        }
    }

    fun insertFilterTip(filterTip: FilterTip) = viewModelScope.launch(Dispatchers.IO) {
        filterTipRepository.insert(filterTip)
        Log.d("SettingsViewModel", "FilterTip сохранен: ${filterTip.value}")

        val tips = filterTipRepository.getAllSync()
        Log.d("SettingsViewModel", "Все FilterTip: ${tips.map { it.value }}")
    }

    fun deleteFilterTip(filterTip: FilterTip) = viewModelScope.launch(Dispatchers.IO) {
        filterTipRepository.delete(filterTip)
        Log.d("SettingsViewModel", "FilterTip удален: ${filterTip.value}")

        val tips = filterTipRepository.getAllSync()
        Log.d("SettingsViewModel", "Все FilterTip: ${tips.map { it.value }}")
    }

    fun saveReportData(reportData: ReportData) = viewModelScope.launch(Dispatchers.IO) {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        val date = Date()
        val title = "Отчёт ${dateFormat.format(date)}"
        val entity = ReportDataEntity(
            title = title,
            patm = reportData.patm,
            tsr = reportData.tsr.value,
            tasp = reportData.tasp.value,
            plsr = reportData.plsr.value,
            measurementCount = reportData.measurementCount,
            averageSpeed = reportData.averageSpeed,
            calculatedTip = reportData.calculatedTip,
            firstSuitableTip = reportData.firstSuitableTip,
            sko = reportData.sko
        )
        val existingEntity = reportDataRepository.findReportData(entity)
        if (existingEntity == null) {
            reportDataRepository.insert(entity)
            Log.d("SettingsViewModel", "ReportData сохранен: $entity")
            loadAllReportData()
        } else {
            Log.d("SettingsViewModel", "ReportData уже существует и не будет сохранен")
        }
    }

    fun deleteReportData(reportData: ReportDataEntity) = viewModelScope.launch(Dispatchers.IO) {
        reportDataRepository.delete(reportData)
        Log.d("SettingsViewModel", "ReportData удален: $reportData")
        loadAllReportData()
    }

    fun updateReportData(newReportData: ReportData) {
        reportData.value = newReportData
    }

    fun savePreparedReportData(reportData: ReportData) {
        saveReportData(reportData)
    }
}

package com.calculation.tipcalculation.db_Main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.calculation.tipcalculation.db_Main.externalFilter.ExternalFilterTip
import com.calculation.tipcalculation.db_Main.externalFilter.ExternalFilterTipRepository
import com.calculation.tipcalculation.db_Main.internalFilter.FilterTip
import com.calculation.tipcalculation.db_Main.internalFilter.FilterTipRepository
import com.calculation.tipcalculation.db_Main.measurementCount.MeasurementCount
import com.calculation.tipcalculation.db_Main.measurementCount.MeasurementCountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val measurementCountRepository: MeasurementCountRepository
    private val externalFilterTipRepository: ExternalFilterTipRepository
    private val filterTipRepository: FilterTipRepository

    val measurementCount: LiveData<MeasurementCount?>
    val allExternalFilterTips: LiveData<List<ExternalFilterTip>>
    val allFilterTips: LiveData<List<FilterTip>>

    init {

        // Инициализация базы данных и репозиториев
        val database = AppDatabase.getDatabase(application)
        measurementCountRepository = MeasurementCountRepository(database.measurementCountDao())
        externalFilterTipRepository = ExternalFilterTipRepository(database.externalFilterTipDao())
        filterTipRepository = FilterTipRepository(database.filterTipDao())

        // Получение данных из репозиториев
        measurementCount = measurementCountRepository.measurementCount.asLiveData()
        allExternalFilterTips = externalFilterTipRepository.allExternalFilterTips
        allFilterTips = filterTipRepository.allFilterTips
    }

    // Методы для работы с MeasurementCount
    fun insertMeasurementCount(measurementCount: MeasurementCount) = viewModelScope.launch {
        measurementCountRepository.insert(measurementCount)
    }

    fun deleteMeasurementCount(measurementCount: MeasurementCount) = viewModelScope.launch {
        measurementCountRepository.delete(measurementCount)
    }

    // Методы для работы с ExternalFilterTip
    fun insertExternalFilterTip(externalFilterTip: ExternalFilterTip) = viewModelScope.launch {
        externalFilterTipRepository.insert(externalFilterTip)
        Log.d("SettingsViewModel", "ExternalFilterTip сохранен: ${externalFilterTip.value}")


        allExternalFilterTips.value?.let { tips ->
            Log.d("SettingsViewModel", "Все ExternalFilterTip: ${tips.map { it.value }}")
        }
    }

    fun deleteExternalFilterTip(externalFilterTip: ExternalFilterTip) = viewModelScope.launch {
        externalFilterTipRepository.delete(externalFilterTip)
        Log.d("SettingsViewModel", "ExternalFilterTip удален: ${externalFilterTip.value}")

        // Логирование всех ExternalFilterTip
        allExternalFilterTips.value?.let { tips ->
            Log.d("SettingsViewModel", "Все ExternalFilterTip: ${tips.map { it.value }}")
        }
    }

    // Методы для работы с FilterTip
    fun insertFilterTip(filterTip: FilterTip) = viewModelScope.launch {
        filterTipRepository.insert(filterTip)
        Log.d("SettingsViewModel", "FilterTip сохранен: ${filterTip.value}")


        val tips = withContext(Dispatchers.IO) { filterTipRepository.getAllSync() }
        Log.d("SettingsViewModel", "Все FilterTip: ${tips.map { it.value }}")
    }

    fun deleteFilterTip(filterTip: FilterTip) = viewModelScope.launch {
        filterTipRepository.delete(filterTip)
        Log.d("SettingsViewModel", "FilterTip удален: ${filterTip.value}")


        val tips = withContext(Dispatchers.IO) { filterTipRepository.getAllSync() }
        Log.d("SettingsViewModel", "Все FilterTip: ${tips.map { it.value }}")
    }
}
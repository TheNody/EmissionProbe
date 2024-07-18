package com.calculation.tipcalculation.db_Main

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.calculation.tipcalculation.db_Main.externalFilter.ExternalFilterTip
import com.calculation.tipcalculation.db_Main.externalFilter.ExternalFilterTipRepository
import com.calculation.tipcalculation.db_Main.internalFilter.FilterTip
import com.calculation.tipcalculation.db_Main.internalFilter.FilterTipRepository
import com.calculation.tipcalculation.db_Main.speedCount.SpeedCount
import com.calculation.tipcalculation.db_Main.speedCount.SpeedCountRepository
import com.calculation.tipcalculation.model.CalculationData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val speedCountRepository: SpeedCountRepository
    private val externalFilterTipRepository: ExternalFilterTipRepository
    private val filterTipRepository: FilterTipRepository

    val allExternalFilterTips: LiveData<List<ExternalFilterTip>>
    val allFilterTips: LiveData<List<FilterTip>>
    private val speedCount: LiveData<SpeedCount?>

    val speeds = mutableStateListOf<String>()
    var data by mutableStateOf(CalculationData())
    var enteredSpeedCount by mutableStateOf("")

    init {
        // Инициализация базы данных и репозиториев
        val database = AppDatabase.getDatabase(application)
        speedCountRepository = SpeedCountRepository(database.speedDao())
        externalFilterTipRepository = ExternalFilterTipRepository(database.externalFilterTipDao())
        filterTipRepository = FilterTipRepository(database.filterTipDao())

        // Получение данных из репозиториев
        speedCount = speedCountRepository.measurementCount.asLiveData()
        allExternalFilterTips = externalFilterTipRepository.allExternalFilterTips
        allFilterTips = filterTipRepository.allFilterTips

        viewModelScope.launch {
            val speedEntity = speedCountRepository.getSpeed()
            if (speedEntity != null) {
                setSpeedCount(speedEntity.speedCount)
            }
        }
    }

    // Методы для работы с SpeedEntity
    fun insertSpeedCount(speedCount: Int) = viewModelScope.launch {
        val speedEntity = SpeedCount(speedCount = speedCount)
        speedCountRepository.insert(speedEntity)
        setSpeedCount(speedCount)
    }

    fun deleteAllSpeedCount() = viewModelScope.launch {
        speedCountRepository.deleteAll()
        speeds.clear()
        enteredSpeedCount = ""
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
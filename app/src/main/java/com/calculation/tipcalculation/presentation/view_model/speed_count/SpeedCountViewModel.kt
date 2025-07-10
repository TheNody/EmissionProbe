package com.calculation.tipcalculation.presentation.view_model.speed_count

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calculation.tipcalculation.domain.model.SpeedCount
import com.calculation.tipcalculation.domain.usecase.speed_count.InsertSpeedUseCase
import com.calculation.tipcalculation.domain.usecase.speed_count.DeleteSpeedUseCase
import com.calculation.tipcalculation.domain.usecase.speed_count.GetSpeedUseCase
import com.calculation.tipcalculation.domain.usecase.speed_count.UpdateSpeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpeedCountViewModel @Inject constructor(
    private val insertSpeedCountUseCase: InsertSpeedUseCase,
    private val updateSpeedCountUseCase: UpdateSpeedUseCase,
    private val deleteSpeedCountUseCase: DeleteSpeedUseCase,
    private val getSpeedCountUseCase: GetSpeedUseCase
) : ViewModel() {

    private val _speedValue = MutableStateFlow("")
    val speedValue: StateFlow<String> = _speedValue

    private val _isConfirmed = MutableStateFlow(false)
    val isConfirmed: StateFlow<Boolean> = _isConfirmed

    private var speedId: Int? = null

    init {
        loadSpeedCount()
    }

    fun updateInput(value: String) {
        if (value.all { it.isDigit() }) {
            _speedValue.value = value
            _isConfirmed.value = false
        }
    }

    fun insertOrUpdateSpeedCount() {
        viewModelScope.launch {
            val count = _speedValue.value.toIntOrNull() ?: return@launch
            val entity = SpeedCount(id = speedId ?: 0, speedCount = count)

            if (speedId != null) {
                updateSpeedCountUseCase(entity)
            } else {
                insertSpeedCountUseCase(entity)
            }

            _isConfirmed.value = true
        }
    }

    fun deleteSpeedCount() {
        viewModelScope.launch {
            deleteSpeedCountUseCase()
            _speedValue.value = ""
            _isConfirmed.value = false
            speedId = null
        }
    }

    private fun loadSpeedCount() {
        viewModelScope.launch {
            val result = getSpeedCountUseCase()
            result?.let {
                _speedValue.value = it.speedCount.toString()
                speedId = it.id
                _isConfirmed.value = true
            }
        }
    }

    fun startEditing() {
        _isConfirmed.value = false
    }
}


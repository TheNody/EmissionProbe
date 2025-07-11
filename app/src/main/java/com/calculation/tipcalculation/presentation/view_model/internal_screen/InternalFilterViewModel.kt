package com.calculation.tipcalculation.presentation.view_model.internal_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calculation.tipcalculation.domain.model.FilterTip
import com.calculation.tipcalculation.domain.model.InternalResultHistory
import com.calculation.tipcalculation.domain.model.ValidationResult
import com.calculation.tipcalculation.domain.usecase.internal_filter.DeleteFilterTipUseCase
import com.calculation.tipcalculation.domain.usecase.internal_filter.GetFilterTipsUseCase
import com.calculation.tipcalculation.domain.usecase.internal_filter.InsertFilterTipUseCase
import com.calculation.tipcalculation.domain.usecase.internal_filter.ValidateInternalCalculationUseCase
import com.calculation.tipcalculation.domain.usecase.internal_result.internal_result_history.GetInternalResultHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InternalFilterViewModel @Inject constructor(
    private val insertFilterTipUseCase: InsertFilterTipUseCase,
    private val deleteFilterTipUseCase: DeleteFilterTipUseCase,
    private val getFilterTipsUseCase: GetFilterTipsUseCase,
    private val getInternalResultHistoryUseCase: GetInternalResultHistoryUseCase,
    private val validateInternalCalculationUseCase: ValidateInternalCalculationUseCase
) : ViewModel() {

    val tips: LiveData<List<FilterTip>> = getFilterTipsUseCase()

    private val _history = mutableStateOf<List<InternalResultHistory>>(emptyList())
    val history: State<List<InternalResultHistory>> = _history

    private val _validationResult = mutableStateOf<ValidationResult?>(null)
    val validationResult: State<ValidationResult?> = _validationResult

    init {
        loadHistory()
    }

    private fun loadHistory() {
        viewModelScope.launch {
            _history.value = getInternalResultHistoryUseCase()
        }
    }

    fun insertTip(value: String) {
        val doubleValue = value.toDoubleOrNull() ?: return
        viewModelScope.launch {
            insertFilterTipUseCase(FilterTip(value = doubleValue))
        }
    }

    fun deleteTip(tip: FilterTip) {
        viewModelScope.launch {
            deleteFilterTipUseCase(tip)
        }
    }

    fun validateBeforeCalculation(onSuccess: () -> Unit) {
        viewModelScope.launch {
            when (val result = validateInternalCalculationUseCase()) {
                is ValidationResult.Valid -> onSuccess()
                else -> _validationResult.value = result
            }
        }
    }

    fun dismissValidationDialog() {
        _validationResult.value = null
    }
}

package com.calculation.tipcalculation.presentation.view_model.external_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calculation.tipcalculation.domain.model.ExternalFilterTip
import com.calculation.tipcalculation.domain.model.ExternalResultHistory
import com.calculation.tipcalculation.domain.model.ValidationResult
import com.calculation.tipcalculation.domain.usecase.external.external_filter.DeleteExternalTipUseCase
import com.calculation.tipcalculation.domain.usecase.external.external_filter.GetExternalTipsUseCase
import com.calculation.tipcalculation.domain.usecase.external.external_filter.InsertExternalTipUseCase
import com.calculation.tipcalculation.domain.usecase.external.external_filter.ValidateExternalCalculationUseCase
import com.calculation.tipcalculation.domain.usecase.external.external_result.external_result_history.DeleteExternalResultHistoryUseCase
import com.calculation.tipcalculation.domain.usecase.external.external_result.external_result_history.GetExternalResultHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExternalFilterViewModel @Inject constructor(
    private val insertExternalTipUseCase: InsertExternalTipUseCase,
    private val deleteExternalTipUseCase: DeleteExternalTipUseCase,
    private val getExternalTipsUseCase: GetExternalTipsUseCase,
    getExternalResultHistoryUseCase: GetExternalResultHistoryUseCase,
    private val validateExternalCalculationUseCase: ValidateExternalCalculationUseCase,
    private val deleteExternalResultHistoryUseCase: DeleteExternalResultHistoryUseCase
) : ViewModel() {

    val tips: LiveData<List<ExternalFilterTip>> = getExternalTipsUseCase()

    val history: StateFlow<List<ExternalResultHistory>> =
        getExternalResultHistoryUseCase()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    private val _validationResult = mutableStateOf<ValidationResult?>(null)
    val validationResult: State<ValidationResult?> = _validationResult

    fun insertTip(value: String) {
        val doubleValue = value.toDoubleOrNull() ?: return
        viewModelScope.launch {
            insertExternalTipUseCase(ExternalFilterTip(value = doubleValue))
        }
    }

    fun deleteTip(tip: ExternalFilterTip) {
        viewModelScope.launch {
            deleteExternalTipUseCase(tip)
        }
    }

    fun validateBeforeCalculation(onSuccess: () -> Unit) {
        viewModelScope.launch {
            when (val result = validateExternalCalculationUseCase()) {
                is ValidationResult.Valid -> onSuccess()
                else -> _validationResult.value = result
            }
        }
    }

    fun dismissValidationDialog() {
        _validationResult.value = null
    }

    fun deleteResult(item: ExternalResultHistory) {
        viewModelScope.launch {
            deleteExternalResultHistoryUseCase(item)
        }
    }
}
package com.calculation.tipcalculation.presentation.view_model.settings_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calculation.tipcalculation.domain.usecase.external.external_result.external_result_history.DeleteAllExternalResultHistoryUseCase
import com.calculation.tipcalculation.domain.usecase.internal.internal_result.internal_result_history.DeleteAllInternalResultHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.State


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val deleteAllExternalResultHistoryUseCase: DeleteAllExternalResultHistoryUseCase,
    private val deleteAllInternalResultHistoryUseCase: DeleteAllInternalResultHistoryUseCase
) : ViewModel() {

    private val _isDialogVisible = mutableStateOf(false)
    val isDialogVisible: State<Boolean> = _isDialogVisible

    fun onClearHistoryClick() {
        _isDialogVisible.value = true
    }

    fun dismissDialog() {
        _isDialogVisible.value = false
    }

    fun confirmClearHistory() {
        viewModelScope.launch {
            deleteAllExternalResultHistoryUseCase()
            deleteAllInternalResultHistoryUseCase()
            _isDialogVisible.value = false
        }
    }
}
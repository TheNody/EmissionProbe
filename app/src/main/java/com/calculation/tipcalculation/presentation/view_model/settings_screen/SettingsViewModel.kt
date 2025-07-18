package com.calculation.tipcalculation.presentation.view_model.settings_screen

import android.content.Context
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Environment
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calculation.tipcalculation.domain.usecase.external.external_result.external_result_history.DeleteAllExternalResultHistoryUseCase
import com.calculation.tipcalculation.domain.usecase.internal.internal_result.internal_result_history.DeleteAllInternalResultHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val deleteAllExternalResultHistoryUseCase: DeleteAllExternalResultHistoryUseCase,
    private val deleteAllInternalResultHistoryUseCase: DeleteAllInternalResultHistoryUseCase,
) : ViewModel() {

    private val _isDialogVisible = mutableStateOf(false)
    val isDialogVisible: State<Boolean> = _isDialogVisible

    private val _showPathDialog = mutableStateOf(false)
    val showPathDialog: State<Boolean> = _showPathDialog

    private val _reportFolderPath = mutableStateOf("")
    val reportFolderPath: State<String> = _reportFolderPath

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

    fun showReportFolderPath() {
        val path = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)}/TipReports"
        _reportFolderPath.value = path
        _showPathDialog.value = true
    }

    fun dismissPathDialog() {
        _showPathDialog.value = false
    }

    fun copyPathToClipboard(context: Context) {
        val clipboard = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Report folder path", _reportFolderPath.value)
        clipboard.setPrimaryClip(clip)
        _showPathDialog.value = false
    }
}
package com.calculation.tipcalculation.presentation.view_model.history_screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calculation.tipcalculation.domain.model.ExternalResultHistory
import com.calculation.tipcalculation.domain.usecase.external.export_excel.GetExportedReportsUseCase
import com.calculation.tipcalculation.domain.usecase.external.export_excel.InvalidateExportedReportsUseCase
import com.calculation.tipcalculation.domain.usecase.external.external_result.external_result_history.GetExternalResultHistoryUseCase
import com.calculation.tipcalculation.domain.usecase.internal.internal_result.internal_result_history.GetInternalResultHistoryUseCase
import com.calculation.tipcalculation.presentation.ui.history_screen.model.HistoryType
import com.calculation.tipcalculation.presentation.ui.history_screen.model.UnifiedHistoryItem
import com.calculation.tipcalculation.utils.ExcelReportController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    getInternalHistory: GetInternalResultHistoryUseCase,
    getExternalHistory: GetExternalResultHistoryUseCase,
    private val getExportedReports: GetExportedReportsUseCase,
    private val invalidateReports: InvalidateExportedReportsUseCase,
    private val reportController: ExcelReportController
) : ViewModel() {

    private val _exportedTimestamps = MutableStateFlow<Set<String>>(emptySet())
    val exportedTimestamps: StateFlow<Set<String>> = _exportedTimestamps.asStateFlow()

    val unifiedHistory: StateFlow<List<UnifiedHistoryItem>> = combine(
        getInternalHistory(),
        getExternalHistory()
    ) { internal, external ->
        val internalItems = internal.map {
            UnifiedHistoryItem(HistoryType.INTERNAL, it.timestamp, internalData = it)
        }
        val externalItems = external.map {
            UnifiedHistoryItem(HistoryType.EXTERNAL, it.timestamp, externalData = it)
        }
        (internalItems + externalItems).sortedByDescending { it.timestamp }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun saveExternalReport(context: Context, data: ExternalResultHistory) {
        _exportedTimestamps.value = reportController.saveReport(context, data)
    }

    fun deleteReportFile(context: Context, timestamp: String) {
        _exportedTimestamps.value = reportController.deleteReport(context, timestamp)
    }

    fun openExcelFile(context: Context, timestamp: String) {
        reportController.openReport(context, timestamp)
    }

    fun refreshExportedReports(context: Context) {
        invalidateReports(context)
        _exportedTimestamps.value = getExportedReports()
    }
}

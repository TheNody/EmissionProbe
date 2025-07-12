package com.calculation.tipcalculation.presentation.view_model.external_screen.external_result_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calculation.tipcalculation.domain.model.ExternalResultData
import com.calculation.tipcalculation.domain.model.ExternalResultHistory
import com.calculation.tipcalculation.domain.usecase.external.external_result.ClearExternalResultUseCase
import com.calculation.tipcalculation.domain.usecase.external.external_result.GetExternalResultUseCase
import com.calculation.tipcalculation.domain.usecase.external.external_result.external_result_history.InsertExternalResultHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ExternalResultViewModel @Inject constructor(
    getExternalResultUseCase: GetExternalResultUseCase,
    private val insertHistoryUseCase: InsertExternalResultHistoryUseCase,
    private val clearResultUseCase: ClearExternalResultUseCase
) : ViewModel() {

    val savedResult: StateFlow<ExternalResultData?> = getExternalResultUseCase()

    fun saveResultToHistory() {
        val data = savedResult.value ?: return

        val timestamp = SimpleDateFormat("dd:MM:yyyy HH:mm", Locale.getDefault())
            .format(Date())

        val history = ExternalResultHistory(
            patm = data.patm,
            plsr = data.plsr,
            preom = data.preom,
            tsr = data.tsr,
            tasp = data.tasp,
            speeds = data.speeds,
            srznach = data.srznach,
            sigma = data.sigma,
            sko = data.sko,
            average = data.average,
            calculatedTip = data.calculatedTip,
            tipSize = data.tipSize,
            aspUsl = data.aspUsl,
            result = data.result,
            aspUsl1 = data.aspUsl1,
            duslov1 = data.duslov1,
            vibrNak = data.vibrNak,
            dreal = data.dreal,
            vsp2 = data.vsp2,
            closestDiameter = data.closestDiameter,
            firstSuitableDiameter = data.firstSuitableDiameter,
            selectedVp = data.selectedVp,
            checkedDiametersList = data.checkedDiametersList,
            timestamp = timestamp
        )

        viewModelScope.launch {
            insertHistoryUseCase(history)
            clearResultUseCase()
        }
    }
}
package com.calculation.tipcalculation.presentation.view_model.external_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calculation.tipcalculation.domain.model.ExternalFilterTip
import com.calculation.tipcalculation.domain.usecase.external_filter.DeleteExternalTipUseCase
import com.calculation.tipcalculation.domain.usecase.external_filter.GetExternalTipsUseCase
import com.calculation.tipcalculation.domain.usecase.external_filter.InsertExternalTipUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExternalFilterViewModel @Inject constructor(
    private val insertExternalTipUseCase: InsertExternalTipUseCase,
    private val deleteExternalTipUseCase: DeleteExternalTipUseCase,
    private val getExternalTipsUseCase: GetExternalTipsUseCase
) : ViewModel() {

    val tips: LiveData<List<ExternalFilterTip>> = getExternalTipsUseCase()

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
}


package com.calculation.tipcalculation.presentation.view_model.internal_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calculation.tipcalculation.domain.model.FilterTip
import com.calculation.tipcalculation.domain.usecase.internal_filter.DeleteFilterTipUseCase
import com.calculation.tipcalculation.domain.usecase.internal_filter.GetFilterTipsUseCase
import com.calculation.tipcalculation.domain.usecase.internal_filter.InsertFilterTipUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InternalFilterViewModel @Inject constructor(
    private val insertFilterTipUseCase: InsertFilterTipUseCase,
    private val deleteFilterTipUseCase: DeleteFilterTipUseCase,
    private val getFilterTipsUseCase: GetFilterTipsUseCase
) : ViewModel() {

    val tips: LiveData<List<FilterTip>> = getFilterTipsUseCase()

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
}

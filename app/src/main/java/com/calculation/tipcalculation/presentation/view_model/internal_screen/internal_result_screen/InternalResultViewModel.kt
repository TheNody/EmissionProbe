package com.calculation.tipcalculation.presentation.view_model.internal_screen.internal_result_screen

import androidx.lifecycle.ViewModel
import com.calculation.tipcalculation.domain.model.InternalResultData
import com.calculation.tipcalculation.domain.usecase.internal_result.GetInternalResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class InternalResultViewModel @Inject constructor(
    getInternalResultUseCase: GetInternalResultUseCase
) : ViewModel() {
    val result: StateFlow<InternalResultData?> = getInternalResultUseCase()
}

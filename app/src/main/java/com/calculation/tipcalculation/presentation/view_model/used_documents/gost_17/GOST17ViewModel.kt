package com.calculation.tipcalculation.presentation.view_model.used_documents.gost_17

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class GOST17ViewModel @Inject constructor() : ViewModel() {
    private val _documentUrl = MutableStateFlow(
        "https://docs.google.com/gview?embedded=true&url=https://www.np-ciz.ru/userfiles/17_2_4_06-90.pdf"
    )
    val documentUrl: StateFlow<String> = _documentUrl
}

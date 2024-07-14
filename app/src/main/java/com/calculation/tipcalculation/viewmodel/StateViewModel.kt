package com.calculation.tipcalculation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StateViewModel : ViewModel() {

    private val _speeds = MutableLiveData<List<String>>()
    val speeds: LiveData<List<String>> = _speeds

    var patm by mutableStateOf("")
    var plsr by mutableStateOf("")
    var tsr by mutableStateOf("")
    var tasp by mutableStateOf("")
    var preom by mutableStateOf("")

    var srznach by mutableDoubleStateOf(0.0)
    var sigma by mutableDoubleStateOf(0.0)
    var average by mutableDoubleStateOf(0.0)
    var tipSize by mutableDoubleStateOf(0.0)
    var aspUsl by mutableDoubleStateOf(0.0)
    var result by mutableDoubleStateOf(0.0)
    var aspUsl1 by mutableDoubleStateOf(0.0)
    var duslov1 by mutableDoubleStateOf(0.0)
    var vibrNak by mutableDoubleStateOf(0.0)
    var dreal by mutableDoubleStateOf(0.0)
    var vsp2 by mutableDoubleStateOf(0.0)
    var calculatedTip by mutableDoubleStateOf(0.0)

    var closestDiameter by mutableDoubleStateOf(0.0)
    var firstSuitableDiameter by mutableDoubleStateOf(0.0)
    var suitableDiameters = mutableStateListOf<Double>()
    var unsuitableDiameters = mutableStateListOf<Double>()
    var selectedVp by mutableDoubleStateOf(0.0)
    var selectedInnerTip by mutableStateOf("")
    var isButtonVisible by mutableStateOf(false)

    var checkedDiametersList = mutableStateListOf<Pair<Double, Double>>()
    var selectedDiameter by mutableDoubleStateOf(0.0)
    var vpOfSelectedDiameter by mutableDoubleStateOf(0.0)

    fun resetValues() {
        _speeds.value = emptyList()
        patm = ""
        plsr = ""
        tsr = ""
        tasp = ""
        preom = ""
        srznach = 0.0
        sigma = 0.0
        average = 0.0
        tipSize = 0.0
        aspUsl = 0.0
        result = 0.0
        aspUsl1 = 0.0
        duslov1 = 0.0
        vibrNak = 0.0
        dreal = 0.0
        vsp2 = 0.0
        calculatedTip = 0.0
        closestDiameter = 0.0
        firstSuitableDiameter = 0.0
        selectedVp = 0.0
        selectedDiameter = 0.0
        vpOfSelectedDiameter = 0.0
        suitableDiameters.clear()
        unsuitableDiameters.clear()
        checkedDiametersList.clear()
        selectedInnerTip = ""
        isButtonVisible = false
    }

    fun setSpeedCount(count: Int) {
        Log.d("StateViewModel", "Установка количества скоростей: $count")
        _speeds.value = List(count) { "" }
        if (_speeds.value.isNullOrEmpty()) {
            Log.e("StateViewModel", "Ошибка: список скоростей пустой после установки количества скоростей.")
        }
    }
}
package com.calculation.tipcalculation.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf

data class CalculationData(
    var patm: MutableState<String> = mutableStateOf(""),
    var plsr: MutableState<String> = mutableStateOf(""),
    var tsr: MutableState<String> = mutableStateOf(""),
    var tasp: MutableState<String> = mutableStateOf(""),
    var preom: MutableState<String> = mutableStateOf(""),
    var srznach: MutableState<Double> = mutableDoubleStateOf(0.0),
    var sigma: MutableState<Double> = mutableDoubleStateOf(0.0),
    var average: MutableState<Double> = mutableDoubleStateOf(0.0),
    var tipSize: MutableState<Double> = mutableDoubleStateOf(0.0),
    var aspUsl: MutableState<Double> = mutableDoubleStateOf(0.0),
    var result: MutableState<Double> = mutableDoubleStateOf(0.0),
    var aspUsl1: MutableState<Double> = mutableDoubleStateOf(0.0),
    var duslov1: MutableState<Double> = mutableDoubleStateOf(0.0),
    var vibrNak: MutableState<Double> = mutableDoubleStateOf(0.0),
    var dreal: MutableState<Double> = mutableDoubleStateOf(0.0),
    var vsp2: MutableState<Double> = mutableDoubleStateOf(0.0),
    var calculatedTip: MutableState<Double> = mutableDoubleStateOf(0.0),
    var closestDiameter: MutableState<Double> = mutableDoubleStateOf(0.0),
    var firstSuitableDiameter: MutableState<Double> = mutableDoubleStateOf(0.0),
    var suitableDiameters: MutableList<MutableState<Double>> = mutableListOf(),
    var unsuitableDiameters: MutableList<MutableState<Double>> = mutableListOf(),
    var selectedVp: MutableState<Double> = mutableDoubleStateOf(0.0),
    var selectedInnerTip: MutableState<String> = mutableStateOf(""),
    var isButtonVisible: MutableState<Boolean> = mutableStateOf(false),
    var checkedDiametersList: MutableList<Pair<MutableState<Double>, MutableState<Double>>> = mutableListOf(),
    var selectedDiameter: MutableState<Double> = mutableDoubleStateOf(0.0),
    var vpOfSelectedDiameter: MutableState<Double> = mutableDoubleStateOf(0.0)
)

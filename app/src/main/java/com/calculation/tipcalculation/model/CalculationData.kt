package com.calculation.tipcalculation.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf

data class CalculationData(
    var patm: MutableState<Double> = mutableDoubleStateOf(0.0),
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
    var checkedDiametersList: MutableList<Pair<MutableState<Double>, MutableState<Double>>> = mutableListOf(),
    var selectedDiameter: MutableState<Double> = mutableDoubleStateOf(0.0),
    var vpOfSelectedDiameter: MutableState<Double> = mutableDoubleStateOf(0.0)
)

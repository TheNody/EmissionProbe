package com.calculation.tipcalculation.presentation.core

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import org.mariuszgromada.math.mxparser.License

@HiltAndroidApp
class App : Application(){
    override fun onCreate() {
        super.onCreate()

        License.iConfirmNonCommercialUse("Your Name or Organization")
    }
}
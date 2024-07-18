package com.calculation.tipcalculation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.calculation.tipcalculation.navigation.AppNavigation
import com.calculation.tipcalculation.utils.RootDetection


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }

        if (RootDetection.isDeviceRooted(this)) {
            throw RuntimeException("Device is rooted. Application is not allowed to run on rooted devices.")
        }

    }
}


package com.calculation.tipcalculation.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun StubScreen(title: String) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

@Composable fun ExternalResultScreen() = StubScreen("External Result")
@Composable fun InternalResultScreen() = StubScreen("Internal Result")
@Composable fun ExternalTipsScreen() = StubScreen("External Tips")
@Composable fun InternalTipsScreen() = StubScreen("Internal Tips")
@Composable fun HistoryScreen() = StubScreen("History")
@Composable fun MeasurementScreen() = StubScreen("Measurement")
@Composable fun AdvancedSettingsScreen() = StubScreen("Advanced Settings")

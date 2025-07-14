package com.calculation.tipcalculation.presentation.ui.history_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.calculation.tipcalculation.presentation.components.*
import com.calculation.tipcalculation.presentation.theme.Typography
import com.calculation.tipcalculation.presentation.ui.history_screen.model.HistoryType
import com.calculation.tipcalculation.presentation.view_model.history_screen.HistoryViewModel

@Composable
fun HistoryScreen(
    navController: NavHostController,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val history by viewModel.unifiedHistory.collectAsState()
    val savedTimestamps by viewModel.exportedTimestamps.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.refreshExportedReports(context)
    }

    AppScaffold(
        navController = navController,
        selectedIndex = selectedIndex,
        onTabSelected = onTabSelected
    ) {
        MainTopBar(title = "История вычислений", icon = Icons.Default.Search, contentDescription = "Поиск") {}

        Spacer(modifier = Modifier.height(24.dp))

        if (history.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text("Нет данных", style = Typography.bodyLarge, modifier = Modifier.align(Alignment.Center))
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(bottom = 64.dp)
            ) {
                items(history) { item ->
                    val isSaved = savedTimestamps.contains(item.timestamp)
                    val typeLabel = when (item.type) {
                        HistoryType.INTERNAL -> "Внутренняя фильтрация"
                        HistoryType.EXTERNAL -> "Внешняя фильтрация"
                    }

                    ExpandableBoxCardWithTypeAndDate(
                        timestamp = item.timestamp,
                        typeLabel = typeLabel,
                        isExternal = item.type == HistoryType.EXTERNAL,
                        isSaved = isSaved,
                        onSaveClick = {
                            item.externalData?.let { viewModel.saveExternalReport(context, it) }
                        },
                        onDeleteClick = {
                            viewModel.deleteReportFile(context, item.timestamp)
                        },
                        onOpenClick = {
                            viewModel.openExcelFile(context, item.timestamp)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        when (item.type) {
                            HistoryType.INTERNAL -> item.internalData?.let { InternalHistoryCard(it) }
                            HistoryType.EXTERNAL -> item.externalData?.let { ExternalHistoryCard(it) }
                        }
                    }
                }
            }
        }
    }
}


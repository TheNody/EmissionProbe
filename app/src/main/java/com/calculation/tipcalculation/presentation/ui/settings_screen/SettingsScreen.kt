package com.calculation.tipcalculation.presentation.ui.settings_screen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.calculation.tipcalculation.presentation.components.AppScaffold
import com.calculation.tipcalculation.presentation.components.CustomAlertDialog
import com.calculation.tipcalculation.presentation.components.CustomGradientButton
import com.calculation.tipcalculation.presentation.components.MainTopBar
import com.calculation.tipcalculation.presentation.navigation.Screen
import com.calculation.tipcalculation.presentation.view_model.settings_screen.SettingsViewModel
import com.calculation.tipcalculation.utils.navigateSingleTopTo

@Composable
fun SettingsScreen(
    navController: NavHostController,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val isDialogVisible by viewModel.isDialogVisible
    val showPathDialog by viewModel.showPathDialog
    val reportFolderPath by viewModel.reportFolderPath
    val context = LocalContext.current

    AppScaffold(
        navController = navController,
        selectedIndex = selectedIndex,
        onTabSelected = onTabSelected
    ) {
        MainTopBar(
            title = "Настройки",
            icon = Icons.Default.Search,
            contentDescription = "Поиск",
            onIconClick = { /* Поиск */ }
        )

        Spacer(modifier = Modifier.height(24.dp))

        CustomGradientButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            text = "Количество измерений",
            onClick = {
                navController.navigate(Screen.SpeedCount.route)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomGradientButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            text = "Очистить историю",
            onClick = {
                viewModel.onClearHistoryClick()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomGradientButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            text = "Где хранятся отчёты",
            onClick = {
                viewModel.showReportFolderPath()
            }
        )

        if (isDialogVisible) {
            CustomAlertDialog(
                titleText = "Очистить историю",
                bodyText = "Будет удалена только история расчётов. Скачанные расчёты сохранятся.",
                confirmButtonText = "Удалить",
                cancelButtonText = "Отмена",
                onDismissRequest = { viewModel.dismissDialog() },
                onConfirm = { viewModel.confirmClearHistory() },
                onCancel = { viewModel.dismissDialog() }
            )
        }

        if (showPathDialog) {
            CustomAlertDialog(
                titleText = "Папка с отчётами",
                bodyText = reportFolderPath,
                confirmButtonText = "Скопировать",
                cancelButtonText = "Закрыть",
                onDismissRequest = { viewModel.dismissPathDialog() },
                onConfirm = {
                    viewModel.copyPathToClipboard(context)
                },
                onCancel = { viewModel.dismissPathDialog() }
            )
        }
    }
}


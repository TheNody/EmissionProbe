package com.calculation.tipcalculation.presentation.ui.measurement_check

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.calculation.tipcalculation.R
import com.calculation.tipcalculation.domain.model.RectangularResultHistory
import com.calculation.tipcalculation.domain.model.RoundResultHistory
import com.calculation.tipcalculation.domain.model.SectionHistoryItem
import com.calculation.tipcalculation.presentation.components.*
import com.calculation.tipcalculation.presentation.navigation.Screen
import com.calculation.tipcalculation.presentation.theme.Typography
import com.calculation.tipcalculation.presentation.view_model.measurement_check.SectionTypeSelectionViewModel

@Composable
fun SectionTypeSelectionScreen(
    navController: NavController,
    viewModel: SectionTypeSelectionViewModel = hiltViewModel()
) {
    val roundHistory by viewModel.roundHistory.collectAsState()
    val rectangularHistory by viewModel.rectangularHistory.collectAsState()
    val scrollState = rememberScrollState()

    var showDeleteDialog by remember { mutableStateOf(false) }
    var roundItemToDelete by remember { mutableStateOf<RoundResultHistory?>(null) }
    var rectangularItemToDelete by remember { mutableStateOf<RectangularResultHistory?>(null) }
    val combinedResults = remember(roundHistory, rectangularHistory) {
        (roundHistory.map { SectionHistoryItem.Round(it) } +
                rectangularHistory.map { SectionHistoryItem.Rectangular(it) })
            .sortedByDescending { it.timestamp }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            CustomBackground()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(horizontal = 24.dp)
                    .verticalScroll(scrollState)
            ) {
                MainTopBarWithBackArrowAndCustomIcon(
                    title = "Проверка участка замера",
                    iconResId = R.drawable.ic_measurement,
                    onBackClick = { navController.popBackStack() },
                    onCustomIconClick = { /* показать справку */ }
                )

                Spacer(modifier = Modifier.height(24.dp))

                CustomGradientButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    text = "Прямоугольное сечение",
                    onClick = {
                        navController.navigate(Screen.RectangularSection.route)
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                CustomGradientButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    text = "Круглое сечение",
                    onClick = {
                        navController.navigate(Screen.RoundSection.route)
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                ShadowedCard(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Сохранённые результаты",
                        style = Typography.titleMedium.copy(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.35.sp,
                            brush = Brush.linearGradient(
                                listOf(Color(0xFF3CA4EB), Color(0xFF4286EE))
                            )
                        ),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    if (roundHistory.isEmpty() && rectangularHistory.isEmpty()) {
                        Text(
                            text = "Нет сохранённых данных",
                            style = Typography.bodyLarge,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }

                    combinedResults.forEach { result ->
                        when (result) {
                            is SectionHistoryItem.Round -> {
                                ExpandableBoxCard(
                                    dateText = result.timestamp,
                                    sectionTypeText = "Круглое сечение",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 6.dp),
                                    expandedContent = {
                                        Text(
                                            text = "Круглое сечение",
                                            style = Typography.headlineSmall,
                                            color = Color(0xFF1A1B20)
                                        )
                                        Spacer(modifier = Modifier.height(12.dp))

                                        val item = result.data
                                        ResultText("Диаметр D", "%.2f мм".format(item.d))
                                        ResultText("Эквивалентный диаметр De", "%.2f мм".format(item.de))
                                        ResultText("L", "%.2f мм".format(item.l))
                                        ResultText("L / De", "%.2f".format(item.lOverDe))
                                        ResultText("Lz", "%.2f мм".format(item.lz))
                                        ResultText(
                                            "Количество точек",
                                            "${item.rule.totalPoints} (по диаметру: ${item.rule.diameterPoints})"
                                        )

                                        item.ki?.let { kiList ->
                                            Spacer(modifier = Modifier.height(12.dp))
                                            Text("Коэффициенты Ki", style = Typography.titleMedium)
                                            Text(kiList.joinToString(", ") { "%.2f".format(it) })
                                        }
                                    },
                                    onDeleteClick = {
                                        roundItemToDelete = result.data
                                        showDeleteDialog = true
                                    }
                                )
                            }

                            is SectionHistoryItem.Rectangular -> {
                                ExpandableBoxCard(
                                    dateText = result.timestamp,
                                    sectionTypeText = "Прямоугольное сечение",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 6.dp),
                                    expandedContent = {
                                        Text(
                                            text = "Прямоугольное сечение",
                                            style = Typography.headlineSmall,
                                            color = Color(0xFF1A1B20)
                                        )
                                        Spacer(modifier = Modifier.height(12.dp))

                                        val item = result.data
                                        ResultText("A", "%.2f мм".format(item.a))
                                        ResultText("B", "%.2f мм".format(item.b))
                                        ResultText("L", "%.2f мм".format(item.l))
                                        ResultText("Эквивалентный диаметр De", "%.2f мм".format(item.de))
                                        ResultText("L / De", "%.2f".format(item.lOverDe))
                                        ResultText("Lz", "%.2f мм".format(item.lz))
                                        ResultText(
                                            "Количество точек",
                                            "${item.rule.totalPoints} (на A: ${item.rule.na}, на B: ${item.rule.nb})"
                                        )

                                        item.ki?.let { kiList ->
                                            Spacer(modifier = Modifier.height(12.dp))
                                            Text("Коэффициенты Ki", style = Typography.titleMedium)
                                            Text(kiList.joinToString(", ") { "%.2f".format(it) })
                                        }
                                    },
                                    onDeleteClick = {
                                        rectangularItemToDelete = result.data
                                        showDeleteDialog = true
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }

                Spacer(modifier = Modifier.height(24.dp))
            }

            if (showDeleteDialog) {
                CustomAlertDialog(
                    titleText = "Удалить результат?",
                    bodyText = "Вы действительно хотите удалить этот результат? Это действие необратимо.",
                    confirmButtonText = "Удалить",
                    cancelButtonText = "Отмена",
                    onDismissRequest = {
                        showDeleteDialog = false
                        roundItemToDelete = null
                        rectangularItemToDelete = null
                    },
                    onConfirm = {
                        roundItemToDelete?.let { viewModel.deleteRoundResult(it) }
                        rectangularItemToDelete?.let { viewModel.deleteRectangularResult(it) }

                        showDeleteDialog = false
                        roundItemToDelete = null
                        rectangularItemToDelete = null
                    },
                    onCancel = {
                        showDeleteDialog = false
                        roundItemToDelete = null
                        rectangularItemToDelete = null
                    }
                )
            }
        }
    }
}


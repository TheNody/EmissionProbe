package com.calculation.tipcalculation.presentation.ui.calculator_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.calculation.tipcalculation.R
import com.calculation.tipcalculation.presentation.components.CalculatorIconButton
import com.calculation.tipcalculation.presentation.components.CalculatorKeyButton
import com.calculation.tipcalculation.presentation.components.CustomBackground2
import com.calculation.tipcalculation.presentation.view_model.calculator_screen.CalculatorViewModel
import com.calculation.tipcalculation.utils.ErrorMessageMapper

@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel = hiltViewModel()
) {
    val expression by viewModel.expression.collectAsState()
    val result by viewModel.result.collectAsState()

    val context = LocalContext.current
    val error = viewModel.error.collectAsState().value
    val scrollState = rememberScrollState()


    LaunchedEffect(error) {
        error?.let {
            ErrorMessageMapper.showToast(context, it)
            viewModel.consumeError()
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            CustomBackground2()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .navigationBarsPadding(),
                verticalArrangement = Arrangement.Bottom
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                ) {
                    LaunchedEffect(expression) {
                        scrollState.animateScrollTo(scrollState.maxValue)
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 40.dp, max = 120.dp)
                            .verticalScroll(scrollState),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Text(
                            text = expression,
                            color = Color.White,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.End,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 4.dp),
                            softWrap = true
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 30.dp, max = 60.dp),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(
                            text = result,
                            color = Color(0xFFA4A4A4),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Light,
                            textAlign = TextAlign.End,
                            softWrap = false,
                            maxLines = 1
                        )
                    }

                    Spacer(modifier = Modifier.height(22.dp))
                }

                val iconList = listOf(
                    R.drawable.ic_history,
                    R.drawable.ic_ruler,
                    R.drawable.ic_formula,
                    R.drawable.ic_close
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(18.dp, Alignment.Start)
                ) {
                    iconList.take(3).forEach { iconId ->
                        CalculatorIconButton(
                            iconResId = iconId,
                            tint = Color(0xFFD8D8D8),
                            onClick = {
                                // TODO: handle top icon click
                            }
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    CalculatorIconButton(
                        iconResId = iconList[3],
                        tint = Color(0xFF8BC34A),
                        onClick = {
                            viewModel.backspace()
                        },
                        onHold = {
                            viewModel.backspace()
                        }
                    )
                }

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .padding(bottom = 14.dp),
                    color = Color(0xFF353F54)
                )

                val keys = listOf(
                    listOf("C", "()", "%","÷"),
                    listOf("7", "8", "9", "×"),
                    listOf("4", "5", "6", "−"),
                    listOf("1", "2", "3", "+"),
                    listOf("+/−", "0", ",", "=")
                )

                keys.forEach { row ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        row.forEach { label ->
                            CalculatorKeyButton(
                                text = label,
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    viewModel.onKeyClick(label)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

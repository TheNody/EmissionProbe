package com.calculation.tipcalculation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.calculation.tipcalculation.db_Main.SettingsViewModel
import com.calculation.tipcalculation.db_Main.internalFilter.FilterTip
import com.calculation.tipcalculation.screen_comp.ValueItem

@Composable
fun InternalFilterTipsScreen(settingsViewModel: SettingsViewModel = viewModel()) {
    var inputValue by remember { mutableStateOf("") }
    val valuesList by settingsViewModel.allFilterTips.observeAsState(listOf())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Введите наконечники для внутренней фильтрации",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp, top = 12.dp)
        )

        OutlinedTextField(
            value = inputValue,
            onValueChange = {
                if (it.all { char -> char.isDigit() || char == '.' }) {
                    inputValue = it
                }
            },
            label = { Text("Введите значение") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (inputValue.isNotEmpty()) {
                        val valueToAdd = if (inputValue.endsWith(".")) {
                            inputValue + "0"
                        } else {
                            inputValue
                        }
                        val filterTip = FilterTip(value = valueToAdd.toDouble())
                        settingsViewModel.insertFilterTip(filterTip)
                        inputValue = ""
                    }
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        if (valuesList.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Transparent)
                    .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    itemsIndexed(valuesList) { _, value ->
                        ValueItem(value = value.value.toString(), onDelete = {
                            settingsViewModel.deleteFilterTip(value)
                        })
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}
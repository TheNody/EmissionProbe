package com.calculation.tipcalculation.screen_comp

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.calculation.tipcalculation.R
import com.calculation.tipcalculation.utils.BottomBarTab


//Bottom Bar Comp

@Composable
fun CustomBottomBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    val tabs = listOf(
        BottomBarTab("Рассчёт", R.drawable.calculator_svgrepo_com),
        BottomBarTab("Результат", R.drawable.result_svgrepo_com),
        BottomBarTab("История", R.drawable.history_svgrepo_com),
        BottomBarTab("Настройки", R.drawable.settings_svgrepo_com)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        tabs.forEachIndexed { index, tab ->
            BottomBarItem(
                tab = tab,
                isSelected = selectedTab == index,
                onClick = { onTabSelected(index) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun BottomBarItem(
    tab: BottomBarTab,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val iconColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray, label = ""
    )
    val textColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray, label = ""
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp)
    ) {
        Icon(
            painter = painterResource(id = tab.iconRes),
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = tab.title,
            color = textColor,
            fontSize = 14.sp
        )
    }
}


//Calculation Screen Comp

@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    imeAction: ImeAction,
    onImeAction: () -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = imeAction),
        modifier = Modifier.fillMaxWidth(),
        maxLines = 1,
        singleLine = true,
        keyboardActions = KeyboardActions(
            onNext = { if (imeAction == ImeAction.Next) onImeAction() },
            onDone = { if (imeAction == ImeAction.Done) onImeAction() }
        )
    )
}





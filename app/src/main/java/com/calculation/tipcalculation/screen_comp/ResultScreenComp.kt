package com.calculation.tipcalculation.screen_comp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ResultCard(title: String, value: String, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = title, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = value, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ExpandableTable(title: String, content: @Composable () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    onClick = { expanded = !expanded },
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                )
                .padding(vertical = 16.dp)
                .border(2.dp, Color.Black, shape = RoundedCornerShape(8.dp))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            }
        }
        AnimatedVisibility(visible = expanded) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                content()
            }
        }
    }
}

@Composable
fun Table(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .border(1.dp, Color.Black, shape = RoundedCornerShape(8.dp))
    ) {
        content()
    }
}

@Composable
fun TableRow(title: String, value: String) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
        }
        HorizontalDivider(color = Color.Black, thickness = 1.dp)
    }
}
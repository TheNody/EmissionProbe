package com.calculation.tipcalculation.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.calculation.tipcalculation.presentation.ui.theme.Typography

@Composable
fun CustomAlertDialog(
    titleText: String,
    bodyText: String,
    confirmButtonText: String,
    cancelButtonText: String,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(
                    text = confirmButtonText,
                    style = Typography.titleMedium,
                    color = Color(0xFFE57373)
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text(
                    text = cancelButtonText,
                    style = Typography.titleMedium,
                )
            }
        },
        title = {
            Text(
                text = titleText,
                style = Typography.headlineSmall,
                color = Color.White
            )
        },
        text = {
            Text(
                text = bodyText,
                style = Typography.bodyLarge.copy(fontSize = 17.sp)
            )
        },
        containerColor = Color(0xFF242C3A),
    )
}

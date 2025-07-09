//package com.calculation.tipcalculation.screen_comp
//
//import android.util.Log
//import androidx.compose.animation.AnimatedVisibility
//import androidx.compose.animation.core.EaseOutCubic
//import androidx.compose.animation.core.MutableTransitionState
//import androidx.compose.animation.core.tween
//import androidx.compose.animation.slideInVertically
//import androidx.compose.animation.slideOutVertically
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.LocalIndication
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.interaction.MutableInteractionSource
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardActions
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Clear
//import androidx.compose.material.icons.filled.KeyboardArrowDown
//import androidx.compose.material.icons.filled.KeyboardArrowUp
//import androidx.compose.material3.Button
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.HorizontalDivider
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.focus.FocusDirection
//import androidx.compose.ui.focus.FocusManager
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.window.Dialog
//import androidx.navigation.NavController
//import com.calculation.tipcalculation.db_Main.SettingsViewModel
//import com.calculation.tipcalculation.model.Field
//
////region Calculation Screen Comp
//
//@Composable
//fun CustomOutlinedTextField(
//    value: String,
//    onValueChange: (String) -> Unit,
//    label: String,
//    imeAction: ImeAction,
//    onImeAction: () -> Unit
//) {
//    OutlinedTextField(
//        value = value,
//        onValueChange = onValueChange,
//        label = { Text(label) },
//        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = imeAction),
//        modifier = Modifier.fillMaxWidth(),
//        maxLines = 1,
//        singleLine = true,
//        keyboardActions = KeyboardActions(
//            onNext = { if (imeAction == ImeAction.Next) onImeAction() },
//            onDone = { if (imeAction == ImeAction.Done) onImeAction() }
//        )
//    )
//}
//
//@Composable
//fun CustomTextFieldItem(field: Field, focusManager: FocusManager) {
//    CustomOutlinedTextField(
//        value = field.value,
//        onValueChange = {
//            field.onValueChange(it)
//            Log.d("CalculationScreen", "Добавлено значение ${field.label}: $it")
//        },
//        label = field.label,
//        imeAction = ImeAction.Next,
//        onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
//    )
//    Spacer(modifier = Modifier.height(8.dp))
//}
//
//@Composable
//fun CustomSpeedTextFieldItem(index: Int, speeds: List<String>, settingsViewModel: SettingsViewModel, focusManager: FocusManager) {
//    CustomOutlinedTextField(
//        value = speeds[index],
//        onValueChange = {
//            settingsViewModel.speeds[index] = it
//            Log.d("CalculationScreen", "Добавлено значение скорости V${index + 1}: $it")
//        },
//        label = "Введите V${index + 1} (м/с)", // Убедитесь, что номер скорости отображается правильно
//        imeAction = if (index == speeds.size - 1) ImeAction.Done else ImeAction.Next,
//        onImeAction = {
//            if (index == speeds.size - 1) {
//                focusManager.clearFocus()
//            } else {
//                focusManager.moveFocus(FocusDirection.Down)
//            }
//        }
//    )
//    Spacer(modifier = Modifier.height(8.dp))
//}
//
////endregion
//
////region Settings Screen Comp
//
//@Composable
//fun ValueItem(value: String, onDelete: () -> Unit) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 16.dp, horizontal = 8.dp),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Text(
//            text = value,
//            fontSize = 18.sp
//        )
//        Icon(
//            imageVector = Icons.Default.Clear,
//            contentDescription = "Удалить",
//            tint = Color.Red,
//            modifier = Modifier
//                .clickable(
//                    onClick = { onDelete() },
//                    indication = null,
//                    interactionSource = remember { MutableInteractionSource() }
//                )
//        )
//    }
//}
//
////endregion
//
////region Result Screen Comp
//
//@Composable
//fun ResultCard(title: String, value: String, modifier: Modifier = Modifier) {
//    Card(
//        shape = RoundedCornerShape(8.dp),
//        modifier = modifier
//            .fillMaxWidth()
//            .padding(vertical = 4.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//    ) {
//        Column(
//            modifier = Modifier
//                .padding(16.dp)
//                .fillMaxWidth(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            Text(text = title, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(text = value, fontSize = 24.sp, fontWeight = FontWeight.Bold)
//        }
//    }
//}
//
//@Composable
//fun ExpandableTable(title: String, content: @Composable () -> Unit) {
//    var expanded by remember { mutableStateOf(false) }
//    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .clickable(
//                    onClick = { expanded = !expanded },
//                    indication = null,
//                    interactionSource = remember { MutableInteractionSource() }
//                )
//                .padding(vertical = 16.dp)
//                .border(2.dp, Color.Black, shape = RoundedCornerShape(8.dp))
//        ) {
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.Center,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp)
//            ) {
//                Icon(
//                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
//                    contentDescription = null
//                )
//                Spacer(modifier = Modifier.width(8.dp))
//                Text(
//                    text = title,
//                    style = MaterialTheme.typography.labelLarge,
//                    modifier = Modifier.align(Alignment.CenterVertically)
//                )
//                Spacer(modifier = Modifier.width(8.dp))
//                Icon(
//                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
//                    contentDescription = null
//                )
//            }
//        }
//        AnimatedVisibility(visible = expanded) {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(8.dp)
//            ) {
//                content()
//            }
//        }
//    }
//}
//
//@Composable
//fun Table(content: @Composable () -> Unit) {
//    Column(
//        modifier = Modifier
//            .border(1.dp, Color.Black, shape = RoundedCornerShape(8.dp))
//    ) {
//        content()
//    }
//}
//
//@Composable
//fun TableRow(title: String, value: String) {
//    Column {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp)
//        ) {
//            Text(
//                text = title,
//                style = MaterialTheme.typography.bodyLarge,
//                modifier = Modifier.weight(1f)
//            )
//            Text(
//                text = value,
//                style = MaterialTheme.typography.bodyLarge,
//                modifier = Modifier.weight(1f)
//            )
//        }
//        HorizontalDivider(color = Color.Black, thickness = 1.dp)
//    }
//}
//
////endregion
//
////region Main Screen Comp
//@Composable
//fun IconBox(
//    icon: ImageVector,
//    label: String,
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Card(
//        modifier = modifier
//            .padding(8.dp)
//            .border(
//                BorderStroke(1.5.dp, Color.Black),
//                shape = RoundedCornerShape(16.dp)
//            )
//            .size(100.dp)
//            .clickable(
//                onClick = onClick,
//                interactionSource = remember { MutableInteractionSource() },
//                indication = LocalIndication.current
//            ),
//        shape = RoundedCornerShape(16.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
//        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
//    ) {
//        Box(
//            contentAlignment = Alignment.Center,
//            modifier = Modifier.fillMaxSize()
//        ) {
//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center
//            ) {
//                Icon(
//                    imageVector = icon,
//                    contentDescription = null,
//                    modifier = Modifier.size(24.dp),
//                    tint = Color.Black
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//                Text(
//                    text = label,
//                    style = MaterialTheme.typography.bodySmall.copy(
//                        color = Color.Black,
//                        fontSize = 14.sp
//                    ),
//                    textAlign = TextAlign.Center
//                )
//            }
//        }
//    }
//}
//
//
//
//
//@Composable
//fun DialogWithButton (
//    onDismissRequest: () -> Unit,
//    navController: NavController,
//    whatSeeButton: Int
//) {
//    Dialog(onDismissRequest = { onDismissRequest() }) {
//        val whatSeeEmpty = remember { mutableStateOf(false) }
//        val whatSeeOne = remember { mutableStateOf(false) }
//        val whatSeeTwo = remember { mutableStateOf(false) }
//        val whatSeeThree = remember { mutableStateOf(false) }
//
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(220.dp)
//                .padding(horizontal = 20.dp),
//            shape = RoundedCornerShape(18.dp),
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize(),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally,
//            ) {
//                when (whatSeeButton) {
//                    0 -> { whatSeeEmpty.value = true }
//                    1 -> whatSeeOne.value = true
//                    2 -> { whatSeeTwo.value = true }
//                    3 -> { whatSeeThree.value = true }
//                }
//
//                if (whatSeeEmpty.value) {
//                    Text(
//                        text = "Вы не ввели ни одних данных для вычислений",
//                        fontSize = 24.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = Color.Black,
//                        modifier = Modifier.padding(10.dp)
//                    )
//                }
//
//                if (whatSeeOne.value) {
//                    Text(
//                        text = "Посмотреть Internal результаты",
//                        fontSize = 18.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = Color.Black,
//                        modifier = Modifier.padding(10.dp)
//                    )
//                    Button(
//                        onClick = {
//                            navController.navigate(INTERNAL_RESULT_SCREEN)
//                        }
//                    ) {
//                        Text(text = "Результатам Internal")
//                    }
//                }
//
//                if (whatSeeThree.value) {
//                    Text(
//                        text = "Посмотреть EXternal результаты",
//                        fontSize = 18.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = Color.Black,
//                        modifier = Modifier.padding(10.dp)
//                    )
//                    Button(
//                        onClick = {
//                            navController.navigate(EXTERNAL_RESULT_SCREEN)
//                        }
//                    ) {
//                        Text(text = "Результатам EXternal")
//                    }
//                }
//
//                if (whatSeeTwo.value) {
//                    Text(
//                        text = "Выберите какие результаты хотите посмотреть",
//                        fontSize = 18.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = Color.Black,
//                        modifier = Modifier.padding(10.dp)
//                    )
//                    Button(
//                        onClick = {
//                            navController.navigate(INTERNAL_RESULT_SCREEN)
//                        }
//                    ) {
//                        Text(text = "Перейти к результатам Internal")
//                    }
//
//                    Spacer(modifier = Modifier.padding(top = 20.dp))
//
//                    Button(
//                        onClick = {
//                            navController.navigate(EXTERNAL_RESULT_SCREEN)
//                        }
//                    ) {
//                        Text(text = "Перейти к результатам External")
//                    }
//                }
//            }
//        }
//    }
//}
//
////endregion
//
//
//
//

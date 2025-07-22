package com.calculation.tipcalculation.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.calculation.tipcalculation.R
import com.calculation.tipcalculation.presentation.theme.Typography

val DarkFill = Color(0xFF242C3B)
val InnerShadowColor = Color(0xFF191E29)
val DropShadowColor = Color(0xFF2C3648)

@Composable
fun InnerShadowBox(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    height: Dp = 60.dp,
    cornerRadius: Dp = 8.dp,
    placeholder: String = "",
    onDone: () -> Unit
) {
    val shape = RoundedCornerShape(cornerRadius)

    val strokeBrush = Brush.linearGradient(
        colors = listOf(
            Color.White.copy(alpha = 0.2f),
            Color.Black.copy(alpha = 0.2f)
        ),
        start = Offset.Zero,
        end = Offset.Infinite
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .border(BorderStroke(0.5.dp, strokeBrush), shape = shape)
            .shadow(
                elevation = 10.dp,
                shape = shape,
                ambientColor = DropShadowColor,
                spotColor = DropShadowColor
            )
            .background(DarkFill, shape)
            .innerShadow(
                shape = shape,
                color = InnerShadowColor,
                offsetX = 4.dp,
                offsetY = 10.dp,
                blur = 30.dp
            )
            .padding(horizontal = 16.dp)
    ) {
        TextField(
            value = value,
            onValueChange = {
                val newValue = it.replace(',', '.')
                if (
                    newValue.count { c -> c == '.' } <= 1 &&
                    newValue.all { c -> c.isDigit() || c == '.' } &&
                    !newValue.startsWith(".")
                ) {
                    onValueChange(newValue)
                }
            },
            modifier = Modifier.fillMaxSize(),
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color.LightGray,
                    style = Typography.bodyLarge
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onDone()
                }
            ),
            singleLine = true,
            textStyle = Typography.bodyLarge.copy(color = Color.White),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedPlaceholderColor = Color.LightGray,
                unfocusedPlaceholderColor = Color.LightGray
            )
        )
    }
}

@Composable
fun InnerShadowBoxWithButton(
    value: String,
    onValueChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onStartEditing: () -> Unit,
    isConfirmed: Boolean,
    modifier: Modifier = Modifier,
    height: Dp = 60.dp,
    cornerRadius: Dp = 12.dp,
    placeholder: String = ""
) {
    val shape = RoundedCornerShape(cornerRadius)

    val strokeBrush = Brush.linearGradient(
        colors = listOf(
            Color.White.copy(alpha = 0.2f),
            Color.Black.copy(alpha = 0.2f)
        ),
        start = Offset.Zero,
        end = Offset.Infinite
    )

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    var requestFocus by remember { mutableStateOf(false) }

    LaunchedEffect(requestFocus) {
        if (requestFocus) {
            focusRequester.requestFocus()
            requestFocus = false
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .border(BorderStroke(0.5.dp, strokeBrush), shape)
            .shadow(
                elevation = 10.dp,
                shape = shape,
                ambientColor = DropShadowColor,
                spotColor = DropShadowColor
            )
            .background(DarkFill, shape)
            .innerShadow(
                shape = shape,
                color = InnerShadowColor,
                offsetX = 4.dp,
                offsetY = 10.dp,
                blur = 30.dp
            )
            .padding(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = value,
                onValueChange = {
                    if (it.all(Char::isDigit)) {
                        onValueChange(it)
                    }
                },
                modifier = Modifier
                    .weight(0.7f)
                    .fillMaxHeight()
                    .focusRequester(focusRequester),
                placeholder = {
                    Text(
                        text = placeholder,
                        color = Color.LightGray,
                        style = Typography.bodyLarge
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (value.isNotEmpty()) {
                            focusManager.clearFocus()
                            onConfirm()
                        }
                    }
                ),
                singleLine = true,
                textStyle = Typography.bodyLarge.copy(color = Color.White),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    cursorColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedPlaceholderColor = Color.LightGray,
                    unfocusedPlaceholderColor = Color.LightGray
                ),
                readOnly = isConfirmed
            )

            Spacer(modifier = Modifier.width(8.dp))

            GradientConfirmButton(
                isConfirmed = isConfirmed,
                onClick = {
                    if (isConfirmed) {
                        onStartEditing()
                        requestFocus = true
                    } else {
                        if (value.isNotEmpty()) {
                            focusManager.clearFocus()
                            onConfirm()
                        }
                    }
                },
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxHeight()
            )
        }
    }
}

@Composable
fun BoxCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF363E51),
                        Color(0xFF4C5770)
                    )
                )
            )
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.2f),
                        Color.Black.copy(alpha = 0.2f)
                    )
                ),
                shape = RoundedCornerShape(20.dp)
            )
            .shadow(
                elevation = 10.dp,
                spotColor = Color(0x33667BA5),
                ambientColor = Color(0x33667BA5)
            )
            .padding(16.dp)
    ) {
        Column {
            content()
        }
    }
}

@Composable
fun ExpandableBoxCard(
    dateText: String,
    modifier: Modifier = Modifier,
    sectionTypeText: String? = null,
    expandedContent: @Composable ColumnScope.() -> Unit,
    onDeleteClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF363E51),
                        Color(0xFF4C5770)
                    )
                )
            )
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.2f),
                        Color.Black.copy(alpha = 0.2f)
                    )
                ),
                shape = RoundedCornerShape(20.dp)
            )
            .shadow(
                elevation = 10.dp,
                spotColor = Color(0x33667BA5),
                ambientColor = Color(0x33667BA5)
            )
            .clickable {
                if (expanded) expanded = false
            }
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Дата расчёта: $dateText",
                        style = Typography.titleMedium,
                        color = Color.White
                    )

                    if (!expanded && !sectionTypeText.isNullOrBlank()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = sectionTypeText,
                            style = Typography.bodySmall,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }

                if (!expanded) {
                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_eye),
                            contentDescription = "Показать",
                            tint = Color.White,
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .size(20.dp)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    expanded = true
                                }
                        )

                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete),
                            contentDescription = "Удалить",
                            tint = Color.White,
                            modifier = Modifier
                                .size(20.dp)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    onDeleteClick()
                                }
                        )
                    }
                }
            }

            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier.padding(top = 12.dp),
                    content = expandedContent
                )
            }
        }
    }
}

@Composable
fun ValuesListCard(
    values: List<String>,
    onRemove: (String) -> Unit
) {
    val listState = rememberLazyListState()
    var previousSize by remember { mutableIntStateOf(values.size) }

    LaunchedEffect(values.size) {
        if (values.size > previousSize) {
            listState.animateScrollToItem(values.lastIndex)
        }
        previousSize = values.size
    }

    BoxCard {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 500.dp)
        ) {
            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(values) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp, horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item,
                            color = Color.White,
                            style = Typography.bodyLarge.copy(fontSize = 24.sp),
                            modifier = Modifier.weight(1f)
                        )

                        IconButton(
                            onClick = { onRemove(item) },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Удалить",
                                tint = Color.LightGray,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InnerShadowBoxWithNegative(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    height: Dp = 60.dp,
    cornerRadius: Dp = 8.dp,
    placeholder: String = "",
    onDone: () -> Unit
) {
    val shape = RoundedCornerShape(cornerRadius)

    val strokeBrush = Brush.linearGradient(
        colors = listOf(
            Color.White.copy(alpha = 0.2f),
            Color.Black.copy(alpha = 0.2f)
        ),
        start = Offset.Zero,
        end = Offset.Infinite
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .border(BorderStroke(0.5.dp, strokeBrush), shape = shape)
            .shadow(
                elevation = 10.dp,
                shape = shape,
                ambientColor = DropShadowColor,
                spotColor = DropShadowColor
            )
            .background(DarkFill, shape)
            .innerShadow(
                shape = shape,
                color = InnerShadowColor,
                offsetX = 4.dp,
                offsetY = 10.dp,
                blur = 30.dp
            )
            .padding(horizontal = 16.dp)
    ) {
        TextField(
            value = value,
            onValueChange = {
                val newValue = it.replace(',', '.')
                if (newValue.matches(Regex("^-?\\d*\\.?\\d*$"))) {
                    onValueChange(newValue)
                }
            },
            modifier = Modifier.fillMaxSize(),
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color.LightGray,
                    style = Typography.bodyLarge
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onDone()
                }
            ),
            singleLine = true,
            textStyle = Typography.bodyLarge.copy(color = Color.White),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedPlaceholderColor = Color.LightGray,
                unfocusedPlaceholderColor = Color.LightGray
            )
        )
    }
}

@Composable
fun ExpandableBoxCardWithTypeAndDate(
    timestamp: String,
    typeLabel: String,
    modifier: Modifier = Modifier,
    isExternal: Boolean = false,
    isSaved: Boolean = false,
    onSaveClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    onOpenClick: () -> Unit = {},
    expandedContent: @Composable ColumnScope.() -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
                brush = Brush.linearGradient(
                    listOf(Color(0xFF363E51), Color(0xFF4C5770))
                )
            )
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    listOf(
                        Color.White.copy(alpha = 0.2f),
                        Color.Black.copy(alpha = 0.2f)
                    )
                ),
                shape = RoundedCornerShape(20.dp)
            )
            .shadow(10.dp, spotColor = Color(0x33667BA5), ambientColor = Color(0x33667BA5))
            .clickable { expanded = !expanded }
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Дата расчёта: $timestamp",
                    style = Typography.titleMedium,
                    color = Color.White
                )

                if (!expanded && isExternal) {
                    Row {
                        if (isSaved) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_eye),
                                contentDescription = "Открыть отчёт",
                                tint = Color.White,
                                modifier = Modifier
                                    .padding(end = 12.dp)
                                    .size(20.dp)
                                    .clickable { onOpenClick() }
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.ic_delete),
                                contentDescription = "Удалить отчёт",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable { onDeleteClick() }
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_save),
                                contentDescription = "Сохранить",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable { onSaveClick() }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = typeLabel,
                style = Typography.bodyLarge,
                color = Color.White
            )

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    expandedContent()
                }
            }
        }
    }
}
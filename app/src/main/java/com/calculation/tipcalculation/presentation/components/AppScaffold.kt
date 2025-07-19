package com.calculation.tipcalculation.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.calculation.tipcalculation.R

@Composable
fun AppScaffold(
    navController: NavHostController,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    val bottomIcons = listOf(
        BottomBarIcon.ResourceIcon(R.drawable.ic_main),
        BottomBarIcon.ResourceIcon(R.drawable.ic_history),
        BottomBarIcon.PainterIcon(rememberVectorPainter(Icons.Default.Settings)),
        BottomBarIcon.ResourceIcon(R.drawable.ic_document),
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(Modifier.fillMaxSize()) {
            CustomBackground()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 10.dp)
                    .statusBarsPadding()
            ) {
                content()
            }

            Box(
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                SlantedBottomBar(
                    icons = bottomIcons,
                    selectedIndex = selectedIndex,
                    onItemSelected = onTabSelected
                )
            }
        }
    }
}

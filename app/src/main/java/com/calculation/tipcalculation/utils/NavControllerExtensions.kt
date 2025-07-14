package com.calculation.tipcalculation.utils

import androidx.navigation.NavController

fun NavController.navigateSingleTopTo(route: String) {
    val currentRoute = this.currentBackStackEntry?.destination?.route
    if (currentRoute != route) {
        this.navigate(route) {
            launchSingleTop = true
            restoreState = true
            popUpTo(graph.startDestinationId) {
                saveState = true
            }
        }
    }
}
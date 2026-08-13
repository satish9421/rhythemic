/*
 * Rhythemic Utility Module
 *
 * Internal helper functions
 * Signature: Rhythemic::UTILITY::V1
 */

package com.j.rhythemic.ui.utils

import androidx.navigation.NavController
import com.j.rhythemic.ui.screens.Screens

fun NavController.backToMain() {
    val mainRoutes = Screens.MainScreens.map { it.route }

    while (previousBackStackEntry != null &&
        currentBackStackEntry?.destination?.route !in mainRoutes
    ) {
        popBackStack()
    }
}

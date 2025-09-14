package com.gaugustini.mhfudatabase.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.gaugustini.mhfudatabase.ui.navigation.NavigationActions
import com.gaugustini.mhfudatabase.ui.navigation.NavigationGraph
import com.gaugustini.mhfudatabase.ui.theme.Theme

@Composable
fun MHFUDatabase() {
    Theme {
        val navController = rememberNavController()
        val navigationActions = remember(navController) { NavigationActions(navController) }

        NavigationGraph(
            navController = navController,
            navigationActions = navigationActions,
        )
    }
}

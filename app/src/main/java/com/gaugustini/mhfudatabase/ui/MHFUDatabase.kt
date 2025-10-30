package com.gaugustini.mhfudatabase.ui

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gaugustini.mhfudatabase.ui.components.BetaDialog
import com.gaugustini.mhfudatabase.ui.components.Drawer
import com.gaugustini.mhfudatabase.ui.navigation.Destinations
import com.gaugustini.mhfudatabase.ui.navigation.NavigationActions
import com.gaugustini.mhfudatabase.ui.navigation.NavigationGraph
import com.gaugustini.mhfudatabase.ui.theme.Theme
import kotlinx.coroutines.launch

@Composable
fun MHFUDatabase(
    uiState: MainUiState,
    onBetaDialogDismissed: () -> Unit,
) {
    val navController = rememberNavController()
    val navigationActions = remember(navController) { NavigationActions(navController) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Destinations.ARMOR_SET_LIST

    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    Theme(
        themeMode = uiState.themeMode!!,
    ) {
        if (uiState.showBetaDialog) {
            BetaDialog(
                onConfirm = onBetaDialogDismissed,
                onDismiss = onBetaDialogDismissed,
            )
        }

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                Drawer(
                    drawerState = drawerState,
                    currentRoute = currentRoute,
                    closeDrawer = { coroutineScope.launch { drawerState.close() } },
                    navigateToArmorSetList = navigationActions.navigateToArmorSetList,
                    navigateToDecorationList = navigationActions.navigateToDecorationList,
                    navigateToItemList = navigationActions.navigateToItemList,
                    navigateToItemCombinationList = navigationActions.navigateToItemCombinationList,
                    navigateToLocationList = navigationActions.navigateToLocationList,
                    navigateToMonsterList = navigationActions.navigateToMonsterList,
                    navigateToQuestList = navigationActions.navigateToQuestList,
                    navigateToSkillTreeList = navigationActions.navigateToSkillTreeList,
                    navigateToWeaponList = navigationActions.navigateToWeaponTypeList,
                    navigateToSettings = navigationActions.navigateToSettings,
                    navigateToAbout = navigationActions.navigateToAbout,
                )
            },
        ) {
            NavigationGraph(
                navController = navController,
                navigationActions = navigationActions,
                openDrawer = { coroutineScope.launch { drawerState.open() } },
            )
        }
    }
}

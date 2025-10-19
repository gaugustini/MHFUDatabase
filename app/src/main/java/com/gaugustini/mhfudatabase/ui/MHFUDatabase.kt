package com.gaugustini.mhfudatabase.ui

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gaugustini.mhfudatabase.data.ThemeMode
import com.gaugustini.mhfudatabase.data.UserPreferences
import com.gaugustini.mhfudatabase.ui.components.BetaDialog
import com.gaugustini.mhfudatabase.ui.components.Drawer
import com.gaugustini.mhfudatabase.ui.navigation.Destinations
import com.gaugustini.mhfudatabase.ui.navigation.NavigationActions
import com.gaugustini.mhfudatabase.ui.navigation.NavigationGraph
import com.gaugustini.mhfudatabase.ui.theme.Theme
import kotlinx.coroutines.launch

@Composable
fun MHFUDatabase() {
    val context = LocalContext.current

    val themeMode by UserPreferences.getThemeMode(context)
        .collectAsState(initial = ThemeMode.SYSTEM)

    Theme(
        themeMode = themeMode,
    ) {

        val navController = rememberNavController()
        val navigationActions = remember(navController) { NavigationActions(navController) }

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route ?: Destinations.ARMOR_SET_LIST

        val coroutineScope = rememberCoroutineScope()
        val drawerState = rememberDrawerState(DrawerValue.Closed)

        var showBetaDialog by rememberSaveable { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            if (UserPreferences.isFirstLaunch(context)) {
                showBetaDialog = true
                UserPreferences.setFirstLaunchDone(context)
            }
        }

        if (showBetaDialog) {
            BetaDialog(
                onConfirm = { showBetaDialog = false },
                onDismiss = { showBetaDialog = false },
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

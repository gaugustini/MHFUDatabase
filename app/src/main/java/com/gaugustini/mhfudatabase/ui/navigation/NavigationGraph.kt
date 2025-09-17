package com.gaugustini.mhfudatabase.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gaugustini.mhfudatabase.ui.armor.detail.ArmorDetailRoute
import com.gaugustini.mhfudatabase.ui.armor.list.ArmorSetListRoute
import com.gaugustini.mhfudatabase.ui.item.detail.ItemDetailRoute
import com.gaugustini.mhfudatabase.ui.item.list.ItemListRoute
import com.gaugustini.mhfudatabase.ui.itemcombination.list.ItemCombinationListRoute

@Composable
fun NavigationGraph(
    navController: NavHostController,
    navigationActions: NavigationActions,
    openDrawer: () -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = Destinations.ARMOR_SET_LIST,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(durationMillis = 300, easing = EaseOut),
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(durationMillis = 450, easing = EaseIn),
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(durationMillis = 300, easing = EaseOut),
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(durationMillis = 450, easing = EaseIn),
            )
        },
        modifier = modifier,
    ) {

        // Main Routes

        composable(Destinations.SEARCH) {
            Text("MHFU")
        }
        composable(Destinations.ARMOR_SET_LIST) {
            ArmorSetListRoute(
                openDrawer = openDrawer,
                openSearch = navigationActions.navigateToSearch,
                onArmorClick = navigationActions.navigateToArmorDetail,
            )
        }
        composable(Destinations.DECORATION_LIST) {
            Text("MHFU")
        }
        composable(Destinations.ITEM_LIST) {
            ItemListRoute(
                openDrawer = openDrawer,
                openSearch = navigationActions.navigateToSearch,
                onItemClick = navigationActions.navigateToItemDetail,
            )
        }
        composable(Destinations.ITEM_COMBINATION_LIST) {
            ItemCombinationListRoute(
                openDrawer = openDrawer,
                openSearch = navigationActions.navigateToSearch,
                onItemClick = navigationActions.navigateToItemDetail,
            )
        }
        composable(Destinations.LOCATION_LIST) {
            Text("MHFU")
        }
        composable(Destinations.MONSTER_LIST) {
            Text("MHFU")
        }
        composable(Destinations.QUEST_LIST) {
            Text("MHFU")
        }
        composable(Destinations.SKILL_TREE_LIST) {
            Text("MHFU")
        }
        composable(Destinations.WEAPON_TYPE_LIST) {
            Text("MHFU")
        }

        // Detail Routes

        composable(
            route = Destinations.ARMOR_DETAIL,
            arguments = listOf(
                navArgument("armorId") { type = NavType.IntType },
            ),
        ) {
            ArmorDetailRoute(
                navigateBack = navigationActions.navigateBack,
                openSearch = navigationActions.navigateToSearch,
                onArmorClick = navigationActions.navigateToArmorDetail,
                onSkillClick = navigationActions.navigateToSkillTreeDetail,
                onItemClick = navigationActions.navigateToItemDetail,
            )
        }
        composable(
            route = Destinations.DECORATION_DETAIL,
            arguments = listOf(
                navArgument("decorationId") { type = NavType.IntType },
            ),
        ) {
            Text("MHFU")
        }
        composable(
            route = Destinations.ITEM_DETAIL,
            arguments = listOf(
                navArgument("itemId") { type = NavType.IntType },
            ),
        ) {
            ItemDetailRoute(
                navigateBack = navigationActions.navigateBack,
                openSearch = navigationActions.navigateToSearch,
            )
        }
        composable(
            route = Destinations.LOCATION_DETAIL,
            arguments = listOf(
                navArgument("locationId") { type = NavType.IntType },
            ),
        ) {
            Text("MHFU")
        }
        composable(
            route = Destinations.MONSTER_DETAIL,
            arguments = listOf(
                navArgument("monsterId") { type = NavType.IntType },
            ),
        ) {
            Text("MHFU")
        }
        composable(
            route = Destinations.QUEST_DETAIL,
            arguments = listOf(
                navArgument("questId") { type = NavType.IntType },
            ),
        ) {
            Text("MHFU")
        }
        composable(
            route = Destinations.SKILL_TREE_DETAIL,
            arguments = listOf(
                navArgument("skillTreeId") { type = NavType.IntType },
            ),
        ) {
            Text("MHFU")
        }
        composable(
            route = Destinations.WEAPON_GRAPH,
            arguments = listOf(
                navArgument("weaponType") { type = NavType.StringType },
            ),
        ) {
            Text("MHFU")
        }
        composable(
            route = Destinations.WEAPON_DETAIL,
            arguments = listOf(
                navArgument("weaponId") { type = NavType.IntType },
            ),
        ) {
            Text("MHFU")
        }
    }
}

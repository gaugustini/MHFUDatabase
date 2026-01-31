package com.gaugustini.mhfudatabase.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gaugustini.mhfudatabase.ui.features.about.AboutRoute
import com.gaugustini.mhfudatabase.ui.features.armor.detail.ArmorDetailRoute
import com.gaugustini.mhfudatabase.ui.features.armor.list.ArmorSetListRoute
import com.gaugustini.mhfudatabase.ui.features.decoration.detail.DecorationDetailRoute
import com.gaugustini.mhfudatabase.ui.features.decoration.list.DecorationListRoute
import com.gaugustini.mhfudatabase.ui.features.item.detail.ItemDetailRoute
import com.gaugustini.mhfudatabase.ui.features.item.list.ItemListRoute
import com.gaugustini.mhfudatabase.ui.features.itemcombination.list.ItemCombinationListRoute
import com.gaugustini.mhfudatabase.ui.features.location.detail.LocationDetailRoute
import com.gaugustini.mhfudatabase.ui.features.location.list.LocationListRoute
import com.gaugustini.mhfudatabase.ui.features.monster.detail.MonsterDetailRoute
import com.gaugustini.mhfudatabase.ui.features.monster.list.MonsterListRoute
import com.gaugustini.mhfudatabase.ui.features.quest.detail.QuestDetailRoute
import com.gaugustini.mhfudatabase.ui.features.quest.list.QuestListRoute
import com.gaugustini.mhfudatabase.ui.features.search.SearchRoute
import com.gaugustini.mhfudatabase.ui.features.settings.SettingsRoute
import com.gaugustini.mhfudatabase.ui.features.skill.detail.SkillTreeDetailRoute
import com.gaugustini.mhfudatabase.ui.features.skill.list.SkillTreeListRoute
import com.gaugustini.mhfudatabase.ui.features.userset.detail.UserSetDetailRoute
import com.gaugustini.mhfudatabase.ui.features.userset.list.UserSetListRoute
import com.gaugustini.mhfudatabase.ui.features.weapon.detail.WeaponDetailRoute
import com.gaugustini.mhfudatabase.ui.features.weapon.graph.WeaponGraphRoute
import com.gaugustini.mhfudatabase.ui.features.weapon.list.WeaponTypeListRoute

@Composable
fun NavigationGraph(
    navController: NavHostController,
    navigationActions: NavigationActions,
    openDrawer: () -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = Destinations.MONSTER_LIST,
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
                animationSpec = tween(durationMillis = 300, easing = EaseIn),
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
                animationSpec = tween(durationMillis = 300, easing = EaseIn),
            )
        },
        modifier = modifier,
    ) {

        // Main Routes

        composable(Destinations.SEARCH) {
            SearchRoute(
                navigateBack = navigationActions.navigateBack,
                onArmorClick = navigationActions.navigateToArmorDetail,
                onDecorationClick = navigationActions.navigateToDecorationDetail,
                onItemClick = navigationActions.navigateToItemDetail,
                onLocationClick = navigationActions.navigateToLocationDetail,
                onMonsterClick = navigationActions.navigateToMonsterDetail,
                onQuestClick = navigationActions.navigateToQuestDetail,
                onSkillTreeClick = navigationActions.navigateToSkillTreeDetail,
                onSkillClick = navigationActions.navigateToSkillTreeDetail,
                onWeaponClick = navigationActions.navigateToWeaponDetail,
            )
        }
        composable(Destinations.ARMOR_SET_LIST) {
            ArmorSetListRoute(
                openDrawer = openDrawer,
                openSearch = navigationActions.navigateToSearch,
                onArmorClick = navigationActions.navigateToArmorDetail,
            )
        }
        composable(Destinations.DECORATION_LIST) {
            DecorationListRoute(
                openDrawer = openDrawer,
                openSearch = navigationActions.navigateToSearch,
                onDecorationClick = navigationActions.navigateToDecorationDetail,
            )
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
            LocationListRoute(
                openDrawer = openDrawer,
                openSearch = navigationActions.navigateToSearch,
                onLocationClick = navigationActions.navigateToLocationDetail,
            )
        }
        composable(Destinations.MONSTER_LIST) {
            MonsterListRoute(
                openDrawer = openDrawer,
                openSearch = navigationActions.navigateToSearch,
                onMonsterClick = navigationActions.navigateToMonsterDetail,
            )
        }
        composable(Destinations.QUEST_LIST) {
            QuestListRoute(
                openDrawer = openDrawer,
                openSearch = navigationActions.navigateToSearch,
                onQuestClick = navigationActions.navigateToQuestDetail,
            )
        }
        composable(Destinations.SKILL_TREE_LIST) {
            SkillTreeListRoute(
                openDrawer = openDrawer,
                openSearch = navigationActions.navigateToSearch,
                onSkillTreeClick = navigationActions.navigateToSkillTreeDetail,
            )
        }
        composable(Destinations.WEAPON_TYPE_LIST) {
            WeaponTypeListRoute(
                openDrawer = openDrawer,
                openSearch = navigationActions.navigateToSearch,
                onWeaponTypeClick = navigationActions.navigateToWeaponGraph,
            )
        }
        composable(Destinations.USER_EQUIPMENT_SET_LIST) {
            UserSetListRoute(
                openDrawer = openDrawer,
                openSearch = navigationActions.navigateToSearch,
                onEquipmentSetClick = navigationActions.navigateToUserEquipmentSetDetail,
            )
        }
        composable(Destinations.SETTINGS) {
            SettingsRoute(
                navigateBack = navigationActions.navigateBack,
                openSearch = navigationActions.navigateToSearch,
            )
        }
        composable(Destinations.ABOUT) {
            AboutRoute(
                navigateBack = navigationActions.navigateBack,
                openSearch = navigationActions.navigateToSearch,
            )
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
            DecorationDetailRoute(
                navigateBack = navigationActions.navigateBack,
                openSearch = navigationActions.navigateToSearch,
                onSkillClick = navigationActions.navigateToSkillTreeDetail,
                onItemClick = navigationActions.navigateToItemDetail,
            )
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
                onArmorClick = navigationActions.navigateToArmorDetail,
                onDecorationClick = navigationActions.navigateToDecorationDetail,
                onItemClick = navigationActions.navigateToItemDetail,
                onLocationClick = navigationActions.navigateToLocationDetail,
                onMonsterClick = navigationActions.navigateToMonsterDetail,
                onWeaponClick = navigationActions.navigateToWeaponDetail,
            )
        }
        composable(
            route = Destinations.LOCATION_DETAIL,
            arguments = listOf(
                navArgument("locationId") { type = NavType.IntType },
            ),
        ) {
            LocationDetailRoute(
                navigateBack = navigationActions.navigateBack,
                openSearch = navigationActions.navigateToSearch,
                onItemClick = navigationActions.navigateToItemDetail,
            )
        }
        composable(
            route = Destinations.MONSTER_DETAIL,
            arguments = listOf(
                navArgument("monsterId") { type = NavType.IntType },
            ),
        ) {
            MonsterDetailRoute(
                navigateBack = navigationActions.navigateBack,
                openSearch = navigationActions.navigateToSearch,
                onItemClick = navigationActions.navigateToItemDetail,
                onQuestClick = navigationActions.navigateToQuestDetail,
            )
        }
        composable(
            route = Destinations.QUEST_DETAIL,
            arguments = listOf(
                navArgument("questId") { type = NavType.IntType },
            ),
        ) {
            QuestDetailRoute(
                navigateBack = navigationActions.navigateBack,
                openSearch = navigationActions.navigateToSearch,
                onLocationClick = navigationActions.navigateToLocationDetail,
                onMonsterClick = navigationActions.navigateToMonsterDetail,
            )
        }
        composable(
            route = Destinations.SKILL_TREE_DETAIL,
            arguments = listOf(
                navArgument("skillTreeId") { type = NavType.IntType },
            ),
        ) {
            SkillTreeDetailRoute(
                navigateBack = navigationActions.navigateBack,
                openSearch = navigationActions.navigateToSearch,
                onArmorClick = navigationActions.navigateToArmorDetail,
                onDecorationClick = navigationActions.navigateToDecorationDetail,
            )
        }
        composable(
            route = Destinations.WEAPON_GRAPH,
            arguments = listOf(
                navArgument("weaponType") { type = NavType.StringType },
            ),
        ) {
            WeaponGraphRoute(
                navigateBack = navigationActions.navigateBack,
                openSearch = navigationActions.navigateToSearch,
                onWeaponClick = navigationActions.navigateToWeaponDetail,
            )
        }
        composable(
            route = Destinations.WEAPON_DETAIL,
            arguments = listOf(
                navArgument("weaponId") { type = NavType.IntType },
            ),
        ) {
            WeaponDetailRoute(
                navigateBack = navigationActions.navigateBack,
                openSearch = navigationActions.navigateToSearch,
                onItemClick = navigationActions.navigateToItemDetail,
                onWeaponClick = navigationActions.navigateToWeaponDetail,
            )
        }
        composable(
            route = Destinations.USER_EQUIPMENT_SET_DETAIL,
            arguments = listOf(
                navArgument("setId") { type = NavType.IntType },
            ),
        ) {
            UserSetDetailRoute(
                navigateBack = navigationActions.navigateBack,
                openSearch = navigationActions.navigateToSearch,
                onItemClick = navigationActions.navigateToItemDetail,
                onSkillClick = navigationActions.navigateToSkillTreeDetail,
            )
        }
    }
}

package com.gaugustini.mhfudatabase.ui.navigation

import androidx.navigation.NavHostController
import com.gaugustini.mhfudatabase.data.enums.WeaponType

class NavigationActions(
    navController: NavHostController,
) {

    val navigateBack: () -> Unit = {
        if (navController.previousBackStackEntry != null) {
            navController.popBackStack()
        }
    }

    // Main Routes

    val navigateToSearch: () -> Unit = {
        navController.navigate(Destinations.SEARCH) {
            launchSingleTop = true
        }
    }
    val navigateToArmorSetList: () -> Unit = {
        navController.navigate(Destinations.ARMOR_SET_LIST) {
            launchSingleTop = true
        }
    }
    val navigateToDecorationList: () -> Unit = {
        navController.navigate(Destinations.DECORATION_LIST) {
            launchSingleTop = true
        }
    }
    val navigateToItemList: () -> Unit = {
        navController.navigate(Destinations.ITEM_LIST) {
            launchSingleTop = true
        }
    }
    val navigateToItemCombinationList: () -> Unit = {
        navController.navigate(Destinations.ITEM_COMBINATION_LIST) {
            launchSingleTop = true
        }
    }
    val navigateToLocationList: () -> Unit = {
        navController.navigate(Destinations.LOCATION_LIST) {
            launchSingleTop = true
        }
    }
    val navigateToMonsterList: () -> Unit = {
        navController.navigate(Destinations.MONSTER_LIST) {
            launchSingleTop = true
        }
    }
    val navigateToQuestList: () -> Unit = {
        navController.navigate(Destinations.QUEST_LIST) {
            launchSingleTop = true
        }
    }
    val navigateToSkillTreeList: () -> Unit = {
        navController.navigate(Destinations.SKILL_TREE_LIST) {
            launchSingleTop = true
        }
    }
    val navigateToWeaponTypeList: () -> Unit = {
        navController.navigate(Destinations.WEAPON_TYPE_LIST) {
            launchSingleTop = true
        }
    }
    val navigateToAbout: () -> Unit = {
        navController.navigate(Destinations.ABOUT) {
            launchSingleTop = true
        }
    }

    // Detail Routes

    val navigateToArmorDetail: (armorId: Int) -> Unit = {
        navController.navigate(Destinations.armorDetailRoute(it)) {
            launchSingleTop = true
        }
    }
    val navigateToDecorationDetail: (decorationId: Int) -> Unit = {
        navController.navigate(Destinations.decorationDetailRoute(it)) {
            launchSingleTop = true
        }
    }
    val navigateToItemDetail: (itemId: Int) -> Unit = {
        navController.navigate(Destinations.itemDetailRoute(it)) {
            launchSingleTop = true
        }
    }
    val navigateToLocationDetail: (locationId: Int) -> Unit = {
        navController.navigate(Destinations.locationDetailRoute(it)) {
            launchSingleTop = true
        }
    }
    val navigateToMonsterDetail: (monsterId: Int) -> Unit = {
        navController.navigate(Destinations.monsterDetailRoute(it)) {
            launchSingleTop = true
        }
    }
    val navigateToQuestDetail: (questId: Int) -> Unit = {
        navController.navigate(Destinations.questDetailRoute(it)) {
            launchSingleTop = true
        }
    }
    val navigateToSkillTreeDetail: (skillTreeId: Int) -> Unit = {
        navController.navigate(Destinations.skillTreeDetailRoute(it)) {
            launchSingleTop = true
        }
    }
    val navigateToWeaponGraph: (weaponType: WeaponType) -> Unit = {
        navController.navigate(Destinations.weaponGraphRoute(it.name)) {
            launchSingleTop = true
        }
    }
    val navigateToWeaponDetail: (weaponId: Int) -> Unit = {
        navController.navigate(Destinations.weaponDetailRoute(it)) {
            launchSingleTop = true
        }
    }

}

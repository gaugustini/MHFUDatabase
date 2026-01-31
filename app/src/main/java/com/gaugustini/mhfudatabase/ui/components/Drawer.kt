package com.gaugustini.mhfudatabase.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.ui.navigation.Destinations
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.LocalIsDarkTheme
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews

private data class DrawerItem(
    val label: String,
    val icon: Int,
    val route: String,
)

@Composable
fun Drawer(
    drawerState: DrawerState,
    currentRoute: String,
    modifier: Modifier = Modifier,
    closeDrawer: () -> Unit = {},
    navigateToArmorSetList: () -> Unit = {},
    navigateToDecorationList: () -> Unit = {},
    navigateToItemList: () -> Unit = {},
    navigateToItemCombinationList: () -> Unit = {},
    navigateToLocationList: () -> Unit = {},
    navigateToMonsterList: () -> Unit = {},
    navigateToQuestList: () -> Unit = {},
    navigateToSkillTreeList: () -> Unit = {},
    navigateToWeaponList: () -> Unit = {},
    navigateToUserEquipmentSetList: () -> Unit = {},
    navigateToSettings: () -> Unit = {},
    navigateToAbout: () -> Unit = {},
) {
    val mainRoutes = listOf(
        DrawerItem(
            label = stringResource(R.string.screen_monster_list),
            icon = R.drawable.ic_ui_monster,
            route = Destinations.MONSTER_LIST,
        ),
        DrawerItem(
            label = stringResource(R.string.screen_weapon_list),
            icon = R.drawable.ic_ui_weapon,
            route = Destinations.WEAPON_TYPE_LIST,
        ),
        DrawerItem(
            label = stringResource(R.string.screen_armor_set_list),
            icon = R.drawable.ic_armor_chest,
            route = Destinations.ARMOR_SET_LIST,
        ),
        DrawerItem(
            label = stringResource(R.string.screen_quest_list),
            icon = R.drawable.ic_ui_quest,
            route = Destinations.QUEST_LIST,
        ),
        DrawerItem(
            label = stringResource(R.string.screen_item_list),
            icon = R.drawable.ic_ui_item,
            route = Destinations.ITEM_LIST,
        ),
        DrawerItem(
            label = stringResource(R.string.screen_item_combination_list),
            icon = R.drawable.ic_ui_item_combination,
            route = Destinations.ITEM_COMBINATION_LIST,
        ),
        DrawerItem(
            label = stringResource(R.string.screen_location_list),
            icon = R.drawable.ic_ui_location,
            route = Destinations.LOCATION_LIST,
        ),
        DrawerItem(
            label = stringResource(R.string.screen_skill_tree_list),
            icon = R.drawable.ic_ui_skill,
            route = Destinations.SKILL_TREE_LIST,
        ),
        DrawerItem(
            label = stringResource(R.string.screen_decoration_list),
            icon = R.drawable.ic_ui_decoration,
            route = Destinations.DECORATION_LIST,
        ),
        DrawerItem(
            label = stringResource(R.string.screen_user_set_list),
            icon = R.drawable.ic_armor_set,
            route = Destinations.USER_EQUIPMENT_SET_LIST,
        )
    )
    val otherRoutes = listOf(
        DrawerItem(
            label = stringResource(R.string.screen_settings),
            icon = if (LocalIsDarkTheme.current) R.drawable.ic_ui_settings_white else R.drawable.ic_ui_settings_black,
            route = Destinations.SETTINGS,
        ),
        DrawerItem(
            label = stringResource(R.string.screen_about),
            icon = R.drawable.ic_item_ticket,
            route = Destinations.ABOUT,
        ),
    )

    ModalDrawerSheet(
        drawerState = drawerState,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(R.drawable.header),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = Dimension.Padding.medium,
                    )
            )

            mainRoutes.forEach { item ->
                NavigationDrawerItem(
                    icon = {
                        Image(
                            painter = painterResource(item.icon),
                            contentDescription = null,
                            modifier = Modifier.size(Dimension.Size.extraSmall),
                        )
                    },
                    label = {
                        Text(
                            text = item.label,
                            style = MaterialTheme.typography.titleMedium,
                        )
                    },
                    selected = currentRoute == item.route,
                    shape = RectangleShape,
                    onClick = {
                        when (item.route) {
                            Destinations.ARMOR_SET_LIST -> navigateToArmorSetList()
                            Destinations.DECORATION_LIST -> navigateToDecorationList()
                            Destinations.ITEM_LIST -> navigateToItemList()
                            Destinations.ITEM_COMBINATION_LIST -> navigateToItemCombinationList()
                            Destinations.LOCATION_LIST -> navigateToLocationList()
                            Destinations.MONSTER_LIST -> navigateToMonsterList()
                            Destinations.QUEST_LIST -> navigateToQuestList()
                            Destinations.SKILL_TREE_LIST -> navigateToSkillTreeList()
                            Destinations.WEAPON_TYPE_LIST -> navigateToWeaponList()
                            Destinations.USER_EQUIPMENT_SET_LIST -> navigateToUserEquipmentSetList()
                        }
                        closeDrawer()
                    },
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(
                    vertical = Dimension.Padding.medium,
                )
            )

            otherRoutes.forEach { item ->
                NavigationDrawerItem(
                    label = {
                        Text(
                            text = item.label,
                            style = MaterialTheme.typography.titleMedium,
                        )
                    },
                    icon = {
                        Image(
                            painter = painterResource(item.icon),
                            contentDescription = null,
                            modifier = Modifier.size(Dimension.Size.extraSmall),
                        )
                    },
                    selected = currentRoute == item.route,
                    shape = RectangleShape,
                    onClick = {
                        when (item.route) {
                            Destinations.SETTINGS -> navigateToSettings()
                            Destinations.ABOUT -> navigateToAbout()
                        }
                        closeDrawer()
                    },
                )
            }
        }
    }
}

@DevicePreviews
@Composable
fun DrawerPreview() {
    Theme {
        Drawer(
            drawerState = DrawerState(DrawerValue.Open),
            currentRoute = Destinations.ARMOR_SET_LIST,
        )
    }
}

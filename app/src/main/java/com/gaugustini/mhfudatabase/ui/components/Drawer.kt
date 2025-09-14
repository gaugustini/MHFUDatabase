package com.gaugustini.mhfudatabase.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.ui.navigation.Destinations
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme

data class DrawerItem(
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
) {
    val drawerItems = listOf(
        DrawerItem(
            label = stringResource(R.string.screen_armor_set_list),
            icon = R.drawable.ic_ui_armor_set,
            route = Destinations.ARMOR_SET_LIST,
        ),
        DrawerItem(
            label = stringResource(R.string.screen_decoration_list),
            icon = R.drawable.ic_ui_decoration,
            route = Destinations.DECORATION_LIST,
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
            label = stringResource(R.string.screen_monster_list),
            icon = R.drawable.ic_ui_monster,
            route = Destinations.MONSTER_LIST,
        ),
        DrawerItem(
            label = stringResource(R.string.screen_quest_list),
            icon = R.drawable.ic_ui_quest,
            route = Destinations.QUEST_LIST,
        ),
        DrawerItem(
            label = stringResource(R.string.screen_skill_tree_list),
            icon = R.drawable.ic_ui_skill,
            route = Destinations.SKILL_TREE_LIST,
        ),
        DrawerItem(
            label = stringResource(R.string.screen_weapon_list),
            icon = R.drawable.ic_ui_weapon,
            route = Destinations.WEAPON_TYPE_LIST,
        ),
    )

    ModalDrawerSheet(
        drawerState = drawerState,
        windowInsets = WindowInsets(),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(R.drawable.header),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )

            drawerItems.forEach { item ->
                NavigationDrawerItem(
                    label = {
                        Text(
                            text = item.label,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    },
                    icon = {
                        Image(
                            painter = painterResource(item.icon),
                            contentDescription = null,
                            modifier = Modifier.size(Dimension.Size.small),
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
                        }
                        closeDrawer()
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DrawerPreview() {
    Theme {
        Drawer(
            drawerState = DrawerState(DrawerValue.Open),
            currentRoute = Destinations.ARMOR_SET_LIST,
        )
    }
}

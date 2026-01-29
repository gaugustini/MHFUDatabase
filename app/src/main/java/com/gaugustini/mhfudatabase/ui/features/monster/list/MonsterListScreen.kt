package com.gaugustini.mhfudatabase.ui.features.monster.list

import androidx.annotation.StringRes
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.MonsterType
import com.gaugustini.mhfudatabase.domain.model.Monster
import com.gaugustini.mhfudatabase.ui.components.NavigationType
import com.gaugustini.mhfudatabase.ui.components.TabbedLayout
import com.gaugustini.mhfudatabase.ui.components.TopBar
import com.gaugustini.mhfudatabase.ui.features.monster.components.MonsterListItem
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewMonsterData

enum class MonsterListTab(@get:StringRes val title: Int) {
    LARGE(R.string.tab_monster_large),
    SMALL(R.string.tab_monster_small);
}

@Composable
fun MonsterListRoute(
    openDrawer: () -> Unit,
    openSearch: () -> Unit,
    onMonsterClick: (monsterId: Int) -> Unit,
    viewModel: MonsterListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MonsterListScreen(
        uiState = uiState,
        openDrawer = openDrawer,
        openSearch = openSearch,
        onMonsterClick = onMonsterClick,
    )
}

@Composable
fun MonsterListScreen(
    uiState: MonsterListState = MonsterListState(),
    openDrawer: () -> Unit = {},
    openSearch: () -> Unit = {},
    onMonsterClick: (monsterId: Int) -> Unit = {},
) {
    val pagerState = rememberPagerState(
        initialPage = uiState.initialTab.ordinal,
        pageCount = { MonsterListTab.entries.size },
    )
    TabbedLayout(
        pagerState = pagerState,
        tabTitles = MonsterListTab.entries.map { stringResource(it.title) },
        topBar = {
            TopBar(
                title = stringResource(R.string.screen_monster_list),
                navigationType = NavigationType.MENU,
                navigation = openDrawer,
                openSearch = openSearch,
            )
        },
    ) { tabIndex ->
        when (MonsterListTab.entries[tabIndex]) {
            MonsterListTab.LARGE -> {
                MonsterList(
                    monsters = uiState.monsters.filter { it.type == MonsterType.LARGE },
                    onMonsterClick = onMonsterClick,
                )
            }

            MonsterListTab.SMALL -> {
                MonsterList(
                    monsters = uiState.monsters.filter { it.type == MonsterType.SMALL },
                    onMonsterClick = onMonsterClick,
                )
            }
        }
    }
}

@Composable
fun MonsterList(
    monsters: List<Monster>,
    modifier: Modifier = Modifier,
    onMonsterClick: (monsterId: Int) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(monsters) { monster ->
            MonsterListItem(
                monster = monster,
                onMonsterClick = onMonsterClick,
            )
            HorizontalDivider()
        }
    }
}

@DevicePreviews
@Composable
fun MonsterListScreenPreview(
    @PreviewParameter(MonsterListScreenPreviewParamProvider::class) uiState: MonsterListState
) {
    Theme {
        MonsterListScreen(uiState)
    }
}

private class MonsterListScreenPreviewParamProvider : PreviewParameterProvider<MonsterListState> {

    override val values: Sequence<MonsterListState> = sequenceOf(
        MonsterListState(
            initialTab = MonsterListTab.LARGE,
            monsters = PreviewMonsterData.monsterList,
        ),
    )

}

package com.gaugustini.mhfudatabase.ui.features.monster.list

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.MonsterType
import com.gaugustini.mhfudatabase.ui.components.NavigationType
import com.gaugustini.mhfudatabase.ui.components.TabbedLayout
import com.gaugustini.mhfudatabase.ui.components.TopBar
import com.gaugustini.mhfudatabase.ui.features.monster.components.MonsterList
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewMonsterData

enum class MonsterListTab(@param:StringRes val title: Int) {
    LARGE(R.string.tab_monster_large),
    SMALL(R.string.tab_monster_small);

    companion object {
        val all = MonsterListTab.entries

        fun fromIndex(index: Int): MonsterListTab = all.getOrElse(index) { LARGE }

        fun toIndex(tab: MonsterListTab): Int = all.indexOf(tab)

    }
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
        initialPage = MonsterListTab.toIndex(uiState.initialTab),
        pageCount = { MonsterListTab.all.size },
    )
    TabbedLayout(
        pagerState = pagerState,
        tabTitles = MonsterListTab.all.map { stringResource(it.title) },
        topBar = {
            TopBar(
                title = stringResource(R.string.screen_monster_list),
                navigationType = NavigationType.MENU,
                navigation = openDrawer,
                openSearch = openSearch,
            )
        },
    ) { tabIndex ->
        when (MonsterListTab.fromIndex(tabIndex)) {
            MonsterListTab.LARGE -> MonsterList(
                monsters = uiState.monsters.filter { it.type == MonsterType.LARGE },
                onMonsterClick = onMonsterClick,
            )

            MonsterListTab.SMALL -> MonsterList(
                monsters = uiState.monsters.filter { it.type == MonsterType.SMALL },
                onMonsterClick = onMonsterClick,
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
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

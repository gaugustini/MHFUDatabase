package com.gaugustini.mhfudatabase.ui.features.monster.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.MonsterType
import com.gaugustini.mhfudatabase.domain.filter.MonsterFilter
import com.gaugustini.mhfudatabase.ui.components.FilterChipDropdown
import com.gaugustini.mhfudatabase.ui.components.NavigationType
import com.gaugustini.mhfudatabase.ui.components.TopBar
import com.gaugustini.mhfudatabase.ui.features.monster.components.MonsterListItem
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.itemsWithDivider
import com.gaugustini.mhfudatabase.util.preview.PreviewMonsterData

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
        onFilterChange = viewModel::onFilterChange,
        onMonsterClick = onMonsterClick,
    )
}

@Composable
fun MonsterListScreen(
    uiState: MonsterListState = MonsterListState(),
    openDrawer: () -> Unit = {},
    openSearch: () -> Unit = {},
    onFilterChange: (filter: MonsterFilter) -> Unit = {},
    onMonsterClick: (monsterId: Int) -> Unit = {},
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.screen_monster_list),
                navigationType = NavigationType.MENU,
                navigation = openDrawer,
                openSearch = openSearch,
                scrollBehavior = scrollBehavior,
                bottomContent = {
                    MonsterListFilter(
                        filter = uiState.filter,
                        onFilterChange = onFilterChange,
                    )
                }
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { innerPadding ->
        LazyColumn(
            contentPadding = PaddingValues(Dimension.Padding.medium),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            itemsWithDivider(
                items = uiState.monsters,
                key = { it.id }
            ) { monster ->
                MonsterListItem(
                    monster = monster,
                    onMonsterClick = onMonsterClick,
                    modifier = Modifier
                        .clip(RoundedCornerShape(Dimension.Radius.small))
                        .background(MaterialTheme.colorScheme.surface)
                )
            }
        }
    }
}

@Composable
fun MonsterListFilter(
    filter: MonsterFilter,
    modifier: Modifier = Modifier,
    onFilterChange: (filter: MonsterFilter) -> Unit = {},
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(Dimension.Spacing.medium),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimension.Padding.medium),
    ) {
        FilterChipDropdown(
            selected = filter.type != null,
            selectedItem = filter.type,
            items = listOf(null) + MonsterType.entries,
            onItemSelected = { selectedType ->
                onFilterChange(filter.copy(type = selectedType))
            },
            labelProvider = { type ->
                stringResource(
                    when (type) {
                        MonsterType.SMALL -> R.string.monster_filter_size_small
                        MonsterType.LARGE -> R.string.monster_filter_size_large
                        else -> R.string.monster_filter_size_all
                    }
                )
            }
        )
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
            monsters = PreviewMonsterData.monsterList,
        ),
    )

}

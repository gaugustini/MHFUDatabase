package com.gaugustini.mhfudatabase.ui.features.armor.list

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
import com.gaugustini.mhfudatabase.domain.enums.HunterType
import com.gaugustini.mhfudatabase.domain.model.ArmorSet
import com.gaugustini.mhfudatabase.ui.components.NavigationType
import com.gaugustini.mhfudatabase.ui.components.TabbedLayout
import com.gaugustini.mhfudatabase.ui.components.TopBar
import com.gaugustini.mhfudatabase.ui.features.armor.components.ArmorSetListItem
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewArmorData

enum class ArmorSetListTab(@get:StringRes val title: Int) {
    BLADEMASTER(R.string.tab_armor_blademaster),
    GUNNER(R.string.tab_armor_gunner);
}

@Composable
fun ArmorSetListRoute(
    openDrawer: () -> Unit,
    openSearch: () -> Unit,
    onArmorClick: (armorId: Int) -> Unit,
    viewModel: ArmorSetListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ArmorSetListScreen(
        uiState = uiState,
        openDrawer = openDrawer,
        openSearch = openSearch,
        onToggleExpand = viewModel::toggleExpansion,
        onArmorClick = onArmorClick,
    )
}

@Composable
fun ArmorSetListScreen(
    uiState: ArmorSetListState = ArmorSetListState(),
    openDrawer: () -> Unit = {},
    openSearch: () -> Unit = {},
    onToggleExpand: (armorSetId: Int, HunterType) -> Unit = { _, _ -> },
    onArmorClick: (armorId: Int) -> Unit = {},
) {
    val pagerState = rememberPagerState(
        initialPage = uiState.initialTab.ordinal,
        pageCount = { ArmorSetListTab.entries.size },
    )

    TabbedLayout(
        pagerState = pagerState,
        tabTitles = ArmorSetListTab.entries.map { stringResource(it.title) },
        topBar = {
            TopBar(
                title = stringResource(R.string.screen_armor_set_list),
                navigationType = NavigationType.MENU,
                navigation = openDrawer,
                openSearch = openSearch,
            )
        },
    ) { tabIndex ->
        when (ArmorSetListTab.entries[tabIndex]) {
            ArmorSetListTab.BLADEMASTER -> {
                ArmorSetList(
                    armorSets = uiState.armorSetsBlade,
                    expandedArmorSets = uiState.expandedArmorSetsBlade,
                    onToggleExpand = { onToggleExpand(it, HunterType.BLADE) },
                    onArmorClick = onArmorClick,
                )
            }

            ArmorSetListTab.GUNNER -> {
                ArmorSetList(
                    armorSets = uiState.armorSetsGunner,
                    expandedArmorSets = uiState.expandedArmorSetsGunner,
                    onToggleExpand = { onToggleExpand(it, HunterType.GUNNER) },
                    onArmorClick = onArmorClick,
                )
            }
        }
    }
}

@Composable
fun ArmorSetList(
    armorSets: List<ArmorSet>,
    expandedArmorSets: Set<Int>,
    modifier: Modifier = Modifier,
    onToggleExpand: (armorSetId: Int) -> Unit = {},
    onArmorClick: (armorId: Int) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(armorSets) { armorSet ->
            ArmorSetListItem(
                armorSet = armorSet,
                expanded = armorSet.id in expandedArmorSets,
                onToggleExpand = { onToggleExpand(armorSet.id) },
                onArmorClick = onArmorClick,
            )
            HorizontalDivider()
        }
    }
}

@DevicePreviews
@Composable
fun ArmorSetListScreenPreview(
    @PreviewParameter(ArmorSetListScreenPreviewParamProvider::class) uiState: ArmorSetListState
) {
    Theme {
        ArmorSetListScreen(uiState)
    }
}

private class ArmorSetListScreenPreviewParamProvider : PreviewParameterProvider<ArmorSetListState> {

    override val values: Sequence<ArmorSetListState> = sequenceOf(
        ArmorSetListState(
            initialTab = ArmorSetListTab.BLADEMASTER,
            armorSetsBlade = PreviewArmorData.armorSetList,
            expandedArmorSetsBlade = setOf(1),
        ),
    )

}

package com.gaugustini.mhfudatabase.ui.userset.detail

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
import com.gaugustini.mhfudatabase.ui.components.NavigationType
import com.gaugustini.mhfudatabase.ui.components.TabbedLayout
import com.gaugustini.mhfudatabase.ui.components.TopBar
import com.gaugustini.mhfudatabase.ui.theme.Theme

enum class UserSetDetailTab(@param:StringRes val title: Int) {
    EQUIPMENT(R.string.tab_user_set_detail_equipment),
    SUMMARY(R.string.tab_user_set_detail_summary);

    companion object {
        val all = UserSetDetailTab.entries

        fun fromIndex(index: Int): UserSetDetailTab = all.getOrElse(index) { EQUIPMENT }

        fun toIndex(tab: UserSetDetailTab): Int = all.indexOf(tab)

    }
}

@Composable
fun UserSetDetailRoute(
    navigateBack: () -> Unit,
    openSearch: () -> Unit,
    viewModel: UserSetDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    UserSetDetailScreen(
        uiState = uiState,
        navigateBack = navigateBack,
        openSearch = openSearch,
    )
}

@Composable
fun UserSetDetailScreen(
    uiState: UserSetDetailState = UserSetDetailState(),
    navigateBack: () -> Unit = {},
    openSearch: () -> Unit = {},
) {
    val pagerState = rememberPagerState(
        initialPage = UserSetDetailTab.toIndex(uiState.initialTab),
        pageCount = { UserSetDetailTab.all.size },
    )

    TabbedLayout(
        pagerState = pagerState,
        tabTitles = UserSetDetailTab.all.map { stringResource(it.title) },
        topBar = {
            TopBar(
                title = uiState.set?.name ?: stringResource(R.string.user_set_new),
                navigationType = NavigationType.BACK,
                navigation = navigateBack,
                openSearch = openSearch,
            )
        },
    ) { tabIndex ->
        when (UserSetDetailTab.fromIndex(tabIndex)) {
            UserSetDetailTab.EQUIPMENT -> UserSetDetailEquipmentContent(
                weapon = uiState.weapon,
                armors = uiState.armors,
                decorations = uiState.decorations,
                onWeaponClick = {
                    // TODO: Open weapon selection
                },
                onArmorClick = {
                    // TODO: Open armor selection
                },
                onDecorationClick = { _, _ ->
                    // TODO: Open decoration selection
                },
                onAddDecoration = {
                    // TODO: Open decoration selection
                },
                onRemoveDecoration = { _, _ ->
                    // TODO: Remove decoration
                },
            )

            UserSetDetailTab.SUMMARY -> UserSetDetailSummaryContent(
                set = uiState.set,
                weapon = uiState.weapon,
                armors = uiState.armors,
                onItemClick = {
                    // TODO: Open item detail
                },
                onSkillClick = {
                    // TODO: Open skill tree detail
                },
                onSkillTreeClick = {
                    // TODO: Open skill tree detail
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun UserSetDetailScreenPreview(
    @PreviewParameter(UserSetDetailScreenPreviewParamProvider::class) uiState: UserSetDetailState
) {
    Theme {
        UserSetDetailScreen(uiState)
    }
}

private class UserSetDetailScreenPreviewParamProvider : PreviewParameterProvider<UserSetDetailState> {

    override val values: Sequence<UserSetDetailState> = sequenceOf(
        UserSetDetailState(
            set = null,
            weapon = null,
            armors = listOf(),
            decorations = listOf(),
        ),
    )

}

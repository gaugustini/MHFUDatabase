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
import com.gaugustini.mhfudatabase.data.enums.ArmorType
import com.gaugustini.mhfudatabase.data.enums.EquipmentType
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
    onItemClick: (itemId: Int) -> Unit = {},
    onSkillClick: (skillTreeId: Int) -> Unit = {},
    viewModel: UserSetDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    UserSetDetailScreen(
        uiState = uiState,
        navigateBack = navigateBack,
        openSearch = openSearch,
        openWeaponSelection = viewModel::openWeaponSelection,
        openArmorSelection = viewModel::openArmorSelection,
        openDecorationSelection = viewModel::openDecorationSelection,
        closeSelection = viewModel::closeSelection,
        changeWeapon = viewModel::changeWeapon,
        changeArmor = viewModel::changeArmor,
        addDecoration = viewModel::addDecoration,
        removeDecoration = viewModel::removeDecoration,
        onItemClick = onItemClick,
        onSkillClick = onSkillClick,
    )
}

@Composable
fun UserSetDetailScreen(
    uiState: UserSetDetailState = UserSetDetailState(),
    navigateBack: () -> Unit = {},
    openSearch: () -> Unit = {},
    openWeaponSelection: () -> Unit = {},
    openArmorSelection: (armorType: ArmorType) -> Unit = {},
    openDecorationSelection: (equipmentType: EquipmentType, availableSlots: Int) -> Unit = { _, _ -> },
    closeSelection: () -> Unit = {},
    changeWeapon: (weaponId: Int) -> Unit = {},
    changeArmor: (armorId: Int) -> Unit = {},
    addDecoration: (decorationId: Int) -> Unit = {},
    removeDecoration: (decorationId: Int, equipmentType: EquipmentType) -> Unit = { _, _ -> },
    onItemClick: (itemId: Int) -> Unit = {},
    onSkillClick: (skillTreeId: Int) -> Unit = {},
) {
    if (!uiState.openSelectionEquipment) {
        val pagerState = rememberPagerState(
            initialPage = UserSetDetailTab.toIndex(uiState.initialTab),
            pageCount = { UserSetDetailTab.all.size },
        )

        TabbedLayout(
            pagerState = pagerState,
            tabTitles = UserSetDetailTab.all.map { stringResource(it.title) },
            topBar = { // TODO: Change top bar for this screen
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
                    weapon = uiState.setWeapon,
                    armors = uiState.setArmors,
                    decorations = uiState.setDecorations,
                    onWeaponClick = openWeaponSelection,
                    onArmorClick = openArmorSelection,
                    onAddDecoration = openDecorationSelection,
                    onRemoveDecoration = removeDecoration,
                )

                UserSetDetailTab.SUMMARY -> UserSetDetailSummaryContent(
                    set = uiState.set,
                    weapon = uiState.setWeapon,
                    armors = uiState.setArmors,
                    activeSkills = uiState.setActiveSkills,
                    skillTreePoints = uiState.setSkillTreePoints,
                    requiredMaterials = uiState.setRequiredMaterials,
                    onItemClick = onItemClick,
                    onSkillClick = onSkillClick,
                )
            }
        }
    } else {
        when (uiState.selectionType) {
            SelectionType.WEAPON -> {
                WeaponSelection(
                    weapons = uiState.weapons,
                    onWeaponClick = changeWeapon,
                    onBack = closeSelection,
                )
            }

            SelectionType.ARMOR -> {
                ArmorSelection(
                    armors = uiState.armors,
                    onArmorClick = changeArmor,
                    onBack = closeSelection,
                )
            }

            SelectionType.DECORATION -> {
                DecorationSelection(
                    decorations = uiState.decorations,
                    onDecorationClick = addDecoration,
                    onBack = closeSelection,
                )
            }

            else -> {}
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
            setWeapon = null,
            setArmors = listOf(),
            setDecorations = listOf(),
        ),
    )

}

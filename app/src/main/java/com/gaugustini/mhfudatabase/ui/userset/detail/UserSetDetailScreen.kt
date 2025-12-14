package com.gaugustini.mhfudatabase.ui.userset.detail

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.ui.userset.components.ArmorSelection
import com.gaugustini.mhfudatabase.ui.userset.components.ArmorSelectionFilter
import com.gaugustini.mhfudatabase.ui.userset.components.DecorationSelection
import com.gaugustini.mhfudatabase.ui.userset.components.DecorationSelectionFilter
import com.gaugustini.mhfudatabase.ui.userset.components.DeleteConfirmationDialog
import com.gaugustini.mhfudatabase.ui.userset.components.RenameDialog
import com.gaugustini.mhfudatabase.ui.userset.components.WeaponSelection
import com.gaugustini.mhfudatabase.ui.userset.components.WeaponSelectionFilter

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
        renameUserSet = viewModel::renameUserSet,
        deleteUserSet = viewModel::deleteUserSet,
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
        onWeaponFilterChange = viewModel::applyWeaponFilter,
        onArmorFilterChange = viewModel::applyArmorFilter,
        onDecorationFilterChange = viewModel::applyDecorationFilter,
    )
}

@Composable
fun UserSetDetailScreen(
    uiState: UserSetDetailState = UserSetDetailState(),
    navigateBack: () -> Unit = {},
    openSearch: () -> Unit = {},
    renameUserSet: (newSetName: String) -> Unit = {},
    deleteUserSet: () -> Unit = {},
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
    onWeaponFilterChange: (filter: WeaponSelectionFilter) -> Unit = {},
    onArmorFilterChange: (filter: ArmorSelectionFilter) -> Unit = {},
    onDecorationFilterChange: (filter: DecorationSelectionFilter) -> Unit = {},
) {
    var showRenameDialog by rememberSaveable { mutableStateOf(false) }
    var showDeleteDialog by rememberSaveable { mutableStateOf(false) }

    if (!uiState.openSelectionEquipment) {
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
                    actions = {
                        IconButton(
                            onClick = { showRenameDialog = true },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null,
                                modifier = Modifier.size(Dimension.Size.extraSmall)
                            )
                        }
                        IconButton(
                            onClick = { showDeleteDialog = true },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                modifier = Modifier.size(Dimension.Size.extraSmall)
                            )
                        }
                    }
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
                    filter = uiState.weaponSelectionFilter,
                    onWeaponClick = {
                        changeWeapon(it)
                        closeSelection()
                    },
                    onFilterChange = onWeaponFilterChange,
                    onBack = closeSelection,
                )
            }

            SelectionType.ARMOR -> {
                ArmorSelection(
                    armors = uiState.armors,
                    filter = uiState.armorSelectionFilter,
                    onArmorClick = {
                        changeArmor(it)
                        closeSelection()
                    },
                    onFilterChange = onArmorFilterChange,
                    onBack = closeSelection,
                )
            }

            SelectionType.DECORATION -> {
                DecorationSelection(
                    decorations = uiState.decorations,
                    filter = uiState.decorationSelectionFilter,
                    onDecorationClick = {
                        addDecoration(it)
                        closeSelection()
                    },
                    onFilterChange = onDecorationFilterChange,
                    onBack = closeSelection,
                )
            }

            else -> {}
        }
    }

    if (showRenameDialog) {
        RenameDialog(
            setName = uiState.set?.name ?: stringResource(R.string.user_set_new),
            onConfirm = { newName ->
                renameUserSet(newName)
                showRenameDialog = false
            },
            onDismiss = { showRenameDialog = false },
        )
    }

    if (showDeleteDialog) {
        DeleteConfirmationDialog(
            onConfirm = {
                deleteUserSet()
                showDeleteDialog = false
                navigateBack()
            },
            onDismiss = { showDeleteDialog = false },
        )
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

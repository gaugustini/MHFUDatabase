package com.gaugustini.mhfudatabase.ui.features.userset.detail

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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.ui.components.NavigationType
import com.gaugustini.mhfudatabase.ui.components.TabbedLayout
import com.gaugustini.mhfudatabase.ui.components.TopBar
import com.gaugustini.mhfudatabase.ui.features.userset.components.ArmorSelection
import com.gaugustini.mhfudatabase.ui.features.userset.components.DecorationSelection
import com.gaugustini.mhfudatabase.ui.features.userset.components.DeleteConfirmationDialog
import com.gaugustini.mhfudatabase.ui.features.userset.components.RenameDialog
import com.gaugustini.mhfudatabase.ui.features.userset.components.SkillTreeSelection
import com.gaugustini.mhfudatabase.ui.features.userset.components.WeaponSelection
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewUserEquipmentSet

enum class UserSetDetailTab(@get:StringRes val title: Int) {
    EQUIPMENT(R.string.tab_user_set_detail_equipment),
    SUMMARY(R.string.tab_user_set_detail_summary);
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
        onEvent = viewModel::onEvent,
        onItemClick = onItemClick,
        onSkillClick = onSkillClick,
    )
}

@Composable
fun UserSetDetailScreen(
    uiState: UserSetDetailState = UserSetDetailState(),
    navigateBack: () -> Unit = {},
    openSearch: () -> Unit = {},
    onEvent: (UserSetEvent) -> Unit = {},
    onItemClick: (itemId: Int) -> Unit = {},
    onSkillClick: (skillTreeId: Int) -> Unit = {},
) {
    var showRenameDialog by rememberSaveable { mutableStateOf(false) }
    var showDeleteDialog by rememberSaveable { mutableStateOf(false) }

    if (!uiState.openSelectionEquipment && !uiState.openSkillSelection) {
        val pagerState = rememberPagerState(
            initialPage = uiState.initialTab.ordinal,
            pageCount = { UserSetDetailTab.entries.size },
        )

        TabbedLayout(
            pagerState = pagerState,
            tabTitles = UserSetDetailTab.entries.map { stringResource(it.title) },
            topBar = {
                TopBar(
                    title = uiState.equipmentSet.name.ifBlank { stringResource(R.string.user_set_new) },
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
            when (UserSetDetailTab.entries[tabIndex]) {
                UserSetDetailTab.EQUIPMENT -> {
                    UserSetDetailEquipmentContent(
                        equipmentSet = uiState.equipmentSet,
                        openWeaponSelection = {
                            onEvent(UserSetEvent.OpenSelection(SelectionType.WEAPON))
                        },
                        openArmorSelection = { armorType ->
                            onEvent(UserSetEvent.OpenSelection(SelectionType.ARMOR, armorType))
                        },
                        openDecorationSelection = { equipmentType, availableSlots ->
                            onEvent(
                                UserSetEvent.OpenSelection(
                                    type = SelectionType.DECORATION,
                                    equipmentType = equipmentType,
                                    availableSlots = availableSlots
                                )
                            )
                        },
                        onRemoveDecoration = { decorationId, equipmentType ->
                            onEvent(UserSetEvent.RemoveDecoration(decorationId, equipmentType))
                        },
                    )
                }

                UserSetDetailTab.SUMMARY -> {
                    UserSetDetailSummaryContent(
                        equipmentSet = uiState.equipmentSet,
                        onItemClick = onItemClick,
                        onSkillClick = onSkillClick,
                    )
                }
            }
        }
    }

    if (uiState.openSelectionEquipment && !uiState.openSkillSelection) {
        when (uiState.selectionType) {
            SelectionType.WEAPON -> {
                WeaponSelection(
                    weapons = uiState.weapons,
                    filter = uiState.weaponFilter,
                    onWeaponClick = { weaponId ->
                        onEvent(UserSetEvent.ChangeWeapon(weaponId))
                        onEvent(UserSetEvent.CloseEquipmentSelection)
                    },
                    onFilterChange = { onEvent(UserSetEvent.ApplyWeaponFilter(it)) },
                    onBack = { onEvent(UserSetEvent.CloseEquipmentSelection) },
                )
            }

            SelectionType.ARMOR -> {
                ArmorSelection(
                    armors = uiState.armors,
                    filter = uiState.armorFilter,
                    onArmorClick = { armorId ->
                        onEvent(UserSetEvent.ChangeArmor(armorId))
                        onEvent(UserSetEvent.CloseEquipmentSelection)
                    },
                    onFilterChange = { onEvent(UserSetEvent.ApplyArmorFilter(it)) },
                    onBack = { onEvent(UserSetEvent.CloseEquipmentSelection) },
                    openSkillSelection = { onEvent(UserSetEvent.OpenSkillSelection) },
                )
            }

            SelectionType.DECORATION -> {
                DecorationSelection(
                    decorations = uiState.decorations,
                    maxAvailableSlots = uiState.maxAvailableSlots,
                    filter = uiState.decorationFilter,
                    onDecorationClick = { decorationId ->
                        onEvent(UserSetEvent.AddDecoration(decorationId))
                        onEvent(UserSetEvent.CloseEquipmentSelection)
                    },
                    onFilterChange = { onEvent(UserSetEvent.ApplyDecorationFilter(it)) },
                    onBack = { onEvent(UserSetEvent.CloseEquipmentSelection) },
                    openSkillSelection = { onEvent(UserSetEvent.OpenSkillSelection) },
                )
            }

            else -> {}
        }
    }

    if (uiState.openSelectionEquipment && uiState.openSkillSelection) {
        SkillTreeSelection(
            skills = uiState.skills,
            filter = uiState.skillFilter,
            onSkillTreeClick = { skillTreeId ->
                onEvent(UserSetEvent.AddSkillToFilter(skillTreeId))
                onEvent(UserSetEvent.CloseSkillSelection)
            },
            onFilterChange = { onEvent(UserSetEvent.ApplySkillTreeFilter(it)) },
            onBack = { onEvent(UserSetEvent.CloseSkillSelection) },
        )
    }

    if (showRenameDialog) {
        RenameDialog(
            setName = uiState.equipmentSet.name.ifBlank { stringResource(R.string.user_set_new) },
            onConfirm = { newName ->
                onEvent(UserSetEvent.Rename(newName))
                showRenameDialog = false
            },
            onDismiss = { showRenameDialog = false },
        )
    }

    if (showDeleteDialog) {
        DeleteConfirmationDialog(
            onConfirm = {
                onEvent(UserSetEvent.Delete)
                showDeleteDialog = false
                navigateBack()
            },
            onDismiss = { showDeleteDialog = false },
        )
    }
}

@DevicePreviews
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
            initialTab = UserSetDetailTab.EQUIPMENT,
            equipmentSet = PreviewUserEquipmentSet.userSet,
        ),
        UserSetDetailState(
            initialTab = UserSetDetailTab.SUMMARY,
            equipmentSet = PreviewUserEquipmentSet.userSet,
        ),
    )

}

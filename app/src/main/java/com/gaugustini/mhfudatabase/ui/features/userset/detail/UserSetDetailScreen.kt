package com.gaugustini.mhfudatabase.ui.features.userset.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.ui.components.NavigationType
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
import kotlin.math.roundToInt

enum class UserSetDetailPage {
    EQUIPMENT,
    SUMMARY;
}

@Composable
fun UserSetDetailRoute(
    navigateBack: () -> Unit,
    openSearch: () -> Unit,
    onArmorClick: (armorId: Int) -> Unit = {},
    onDecorationClick: (decorationId: Int) -> Unit = {},
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
        onArmorClick = onArmorClick,
        onDecorationClick = onDecorationClick,
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
    onArmorClick: (armorId: Int) -> Unit = {},
    onDecorationClick: (decorationId: Int) -> Unit = {},
    onItemClick: (itemId: Int) -> Unit = {},
    onSkillClick: (skillTreeId: Int) -> Unit = {},
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    var showRenameDialog by rememberSaveable { mutableStateOf(false) }
    var showDeleteDialog by rememberSaveable { mutableStateOf(false) }

    if (!uiState.openEquipmentSelection && !uiState.openSkillSelection) {
        Scaffold(
            topBar = {
                TopBar(
                    title = uiState.equipmentSet.name.ifBlank { stringResource(R.string.user_set_new) },
                    navigationType = NavigationType.BACK,
                    navigation = navigateBack,
                    openSearch = openSearch,
                    scrollBehavior = scrollBehavior,
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
            bottomBar = {
                ScrollingBottomBar(
                    scrollBehavior = scrollBehavior,
                ) {
                    NavigationBarItem(
                        selected = uiState.page == UserSetDetailPage.EQUIPMENT,
                        onClick = { onEvent(UserSetEvent.ChangePage(UserSetDetailPage.EQUIPMENT)) },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Build,
                                contentDescription = null,
                                modifier = Modifier.size(Dimension.Size.extraSmall)
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(R.string.user_set_equipment),
                            )
                        }
                    )
                    NavigationBarItem(
                        selected = uiState.page == UserSetDetailPage.SUMMARY,
                        onClick = { onEvent(UserSetEvent.ChangePage(UserSetDetailPage.SUMMARY)) },
                        icon = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ListAlt,
                                contentDescription = null,
                                modifier = Modifier.size(Dimension.Size.extraSmall)
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(R.string.user_set_summary),
                            )
                        }
                    )
                }
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        ) { innerPadding ->
            when (uiState.page) {
                UserSetDetailPage.EQUIPMENT -> {
                    UserSetDetailEquipmentContent(
                        equipmentSet = uiState.equipmentSet,
                        openWeaponSelection = {
                            onEvent(UserSetEvent.OpenEquipmentSelection(SelectionType.WEAPON))
                        },
                        openArmorSelection = { armorType ->
                            onEvent(UserSetEvent.OpenEquipmentSelection(SelectionType.ARMOR, armorType))
                        },
                        openDecorationSelection = { equipmentType, availableSlots ->
                            onEvent(
                                UserSetEvent.OpenEquipmentSelection(
                                    type = SelectionType.DECORATION,
                                    equipmentType = equipmentType,
                                    availableSlots = availableSlots
                                )
                            )
                        },
                        onRemoveDecoration = { decorationId, equipmentType ->
                            onEvent(UserSetEvent.RemoveDecoration(decorationId, equipmentType))
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }

                UserSetDetailPage.SUMMARY -> {
                    UserSetDetailSummaryContent(
                        equipmentSet = uiState.equipmentSet,
                        onArmorClick = onArmorClick,
                        onDecorationClick = onDecorationClick,
                        onItemClick = onItemClick,
                        onSkillClick = onSkillClick,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    if (uiState.openEquipmentSelection && !uiState.openSkillSelection) {
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

    if (uiState.openEquipmentSelection && uiState.openSkillSelection) {
        SkillTreeSelection(
            skills = uiState.skills,
            filter = uiState.skillFilter,
            onSkillTreeClick = { skillTreeId ->
                onEvent(UserSetEvent.SkillToFilter(skillTreeId))
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

@Composable
fun ScrollingBottomBar(
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Layout(
        modifier = modifier.background(MaterialTheme.colorScheme.surface),
        measurePolicy = { measurables, constraints ->
            val placeable = measurables.first().measure(constraints.copy(minHeight = 0))
            val totalHeight = placeable.height

            val currentHeight =
                (totalHeight + (scrollBehavior.state.collapsedFraction * -totalHeight)).roundToInt()
                    .coerceAtLeast(0)

            layout(constraints.maxWidth, currentHeight) {
                placeable.place(0, (scrollBehavior.state.collapsedFraction * totalHeight).roundToInt())
            }
        },
        content = {
            NavigationBar(
                containerColor = Color.Transparent,
                windowInsets = NavigationBarDefaults.windowInsets,
                content = content
            )
        }
    )
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
            page = UserSetDetailPage.EQUIPMENT,
            equipmentSet = PreviewUserEquipmentSet.userSet,
        ),
        UserSetDetailState(
            page = UserSetDetailPage.SUMMARY,
            equipmentSet = PreviewUserEquipmentSet.userSet,
        ),
    )

}

package com.gaugustini.mhfudatabase.ui.features.userset.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.Gender
import com.gaugustini.mhfudatabase.domain.enums.HunterType
import com.gaugustini.mhfudatabase.ui.components.NavigationType
import com.gaugustini.mhfudatabase.ui.components.TopBar
import com.gaugustini.mhfudatabase.ui.features.userset.components.UserSetListItem
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewUserEquipmentSet

@Composable
fun UserSetListRoute(
    openDrawer: () -> Unit,
    openSearch: () -> Unit,
    onEquipmentSetClick: (setId: Int, hunterType: HunterType, gender: Gender) -> Unit,
    viewModel: UserSetListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    UserSetListScreen(
        uiState = uiState,
        openDrawer = openDrawer,
        openSearch = openSearch,
        onEquipmentSetClick = onEquipmentSetClick,
    )
}

@Composable
fun UserSetListScreen(
    uiState: UserSetListState = UserSetListState(),
    openDrawer: () -> Unit = {},
    openSearch: () -> Unit = {},
    onEquipmentSetClick: (setId: Int, hunterType: HunterType, gender: Gender) -> Unit = { _, _, _ -> },
) {
    var showNewSetDialog by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.screen_user_set_list),
                navigationType = NavigationType.MENU,
                navigation = openDrawer,
                openSearch = openSearch,
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { showNewSetDialog = true },
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null,
                    )
                },
                text = {
                    Text(
                        text = stringResource(R.string.user_set_new),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                },
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(uiState.equipmentSets) { set ->
                UserSetListItem(
                    equipmentSet = set,
                    onEquipmentSetClick = {
                        onEquipmentSetClick(set.id, set.hunterType, set.gender)
                    },
                )
            }
        }

        if (showNewSetDialog) {
            NewSetDialog(
                onConfirm = { hunterType, gender ->
                    onEquipmentSetClick(0, hunterType, gender)
                    showNewSetDialog = false
                },
                onDismiss = { showNewSetDialog = false }
            )
        }
    }
}

@Composable
fun NewSetDialog(
    modifier: Modifier = Modifier,
    onConfirm: (hunterType: HunterType, gender: Gender) -> Unit = { _, _ -> },
    onDismiss: () -> Unit = {},
) {
    var selectedHunterType by rememberSaveable { mutableStateOf(HunterType.BLADE) }
    var selectedGender by rememberSaveable { mutableStateOf(Gender.MALE) }

    val hunterTypes = HunterType.entries.filter { it != HunterType.BOTH }
    val genders = Gender.entries.filter { it != Gender.BOTH }

    AlertDialog(
        title = {
            Text(
                text = stringResource(R.string.user_set_dialog_new_set_title),
                style = MaterialTheme.typography.titleLarge,
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                SelectionSection(
                    label = stringResource(R.string.user_set_dialog_hunter_type),
                    options = hunterTypes,
                    selectedOption = selectedHunterType,
                    onOptionSelected = { selectedHunterType = it },
                    labelProvider = { type ->
                        stringResource(
                            when (type) {
                                HunterType.BLADE -> R.string.user_set_dialog_hunter_type_blade
                                HunterType.GUNNER -> R.string.user_set_dialog_hunter_type_gunner
                                else -> R.string.user_set_dialog_hunter_type_blade
                            }
                        )
                    }
                )
                SelectionSection(
                    label = stringResource(R.string.user_set_dialog_gender),
                    options = genders,
                    selectedOption = selectedGender,
                    onOptionSelected = { selectedGender = it },
                    labelProvider = { gender ->
                        stringResource(
                            when (gender) {
                                Gender.MALE -> R.string.user_set_dialog_gender_male
                                Gender.FEMALE -> R.string.user_set_dialog_gender_female
                                else -> R.string.user_set_dialog_gender_male
                            }
                        )
                    }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(selectedHunterType, selectedGender) }
            ) {
                Text(
                    text = stringResource(R.string.user_set_dialog_confirm),
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(R.string.user_set_dialog_cancel),
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        },
        onDismissRequest = onDismiss,
        modifier = modifier,
    )
}

@Composable
private fun <T> SelectionSection(
    label: String,
    options: List<T>,
    selectedOption: T,
    onOptionSelected: (T) -> Unit,
    labelProvider: @Composable (T) -> String
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall,
        )
        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
            options.forEachIndexed { index, option ->
                SegmentedButton(
                    selected = option == selectedOption,
                    onClick = { onOptionSelected(option) },
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                    label = {
                        Text(
                            text = labelProvider(option),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                )
            }
        }
    }
}

@DevicePreviews
@Composable
fun UserSetListScreenPreview(
    @PreviewParameter(UserSetListScreenPreviewParamProvider::class) uiState: UserSetListState
) {
    Theme {
        UserSetListScreen(uiState)
    }
}

private class UserSetListScreenPreviewParamProvider : PreviewParameterProvider<UserSetListState> {

    override val values: Sequence<UserSetListState> = sequenceOf(
        UserSetListState(
            equipmentSets = PreviewUserEquipmentSet.userSetList,
        ),
    )

}

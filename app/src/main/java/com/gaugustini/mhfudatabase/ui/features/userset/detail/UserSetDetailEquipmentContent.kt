package com.gaugustini.mhfudatabase.ui.features.userset.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.EquipmentType
import com.gaugustini.mhfudatabase.domain.enums.WeaponType
import com.gaugustini.mhfudatabase.domain.model.Armor
import com.gaugustini.mhfudatabase.domain.model.EquipmentDecoration
import com.gaugustini.mhfudatabase.domain.model.UserEquipmentSet
import com.gaugustini.mhfudatabase.domain.model.Weapon
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.icons.ArmorIcon
import com.gaugustini.mhfudatabase.ui.components.icons.NoSlotIcon
import com.gaugustini.mhfudatabase.ui.components.icons.SlotIcon
import com.gaugustini.mhfudatabase.ui.components.icons.WeaponIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.MHFUColors
import com.gaugustini.mhfudatabase.util.preview.PreviewUserEquipmentSet

@Composable
fun UserSetDetailEquipmentContent(
    equipmentSet: UserEquipmentSet,
    modifier: Modifier = Modifier,
    openWeaponSelection: () -> Unit = {},
    openArmorSelection: (armorType: EquipmentType) -> Unit = {},
    openDecorationSelection: (equipmentType: EquipmentType, availableSlots: Int) -> Unit = { _, _ -> },
    onRemoveDecoration: (decorationId: Int, equipmentType: EquipmentType) -> Unit = { _, _ -> },
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(bottom = Dimension.Padding.endContent)
    ) {
        EquipmentWeaponListItem(
            weapon = equipmentSet.weapon,
            decorations = equipmentSet.decorations?.filter { it.equipmentType == EquipmentType.WEAPON }
                ?: emptyList(),
            onWeaponClick = openWeaponSelection,
            onAddDecoration = { openDecorationSelection(EquipmentType.WEAPON, it) },
            onRemoveDecoration = { onRemoveDecoration(it, EquipmentType.WEAPON) },
        )
        EquipmentArmorListItem(
            armor = equipmentSet.armors?.firstOrNull { it.type == EquipmentType.ARMOR_HEAD },
            armorType = EquipmentType.ARMOR_HEAD,
            decorations = equipmentSet.decorations?.filter { it.equipmentType == EquipmentType.ARMOR_HEAD }
                ?: emptyList(),
            onArmorClick = { openArmorSelection(EquipmentType.ARMOR_HEAD) },
            onAddDecoration = { openDecorationSelection(EquipmentType.ARMOR_HEAD, it) },
            onRemoveDecoration = { onRemoveDecoration(it, EquipmentType.ARMOR_HEAD) },
        )
        EquipmentArmorListItem(
            armor = equipmentSet.armors?.firstOrNull { it.type == EquipmentType.ARMOR_CHEST },
            armorType = EquipmentType.ARMOR_CHEST,
            decorations = equipmentSet.decorations?.filter { it.equipmentType == EquipmentType.ARMOR_CHEST }
                ?: emptyList(),
            onArmorClick = { openArmorSelection(EquipmentType.ARMOR_CHEST) },
            onAddDecoration = { openDecorationSelection(EquipmentType.ARMOR_CHEST, it) },
            onRemoveDecoration = { onRemoveDecoration(it, EquipmentType.ARMOR_CHEST) },
        )
        EquipmentArmorListItem(
            armor = equipmentSet.armors?.firstOrNull { it.type == EquipmentType.ARMOR_ARMS },
            armorType = EquipmentType.ARMOR_ARMS,
            decorations = equipmentSet.decorations?.filter { it.equipmentType == EquipmentType.ARMOR_ARMS }
                ?: emptyList(),
            onArmorClick = { openArmorSelection(EquipmentType.ARMOR_ARMS) },
            onAddDecoration = { openDecorationSelection(EquipmentType.ARMOR_ARMS, it) },
            onRemoveDecoration = { onRemoveDecoration(it, EquipmentType.ARMOR_ARMS) },
        )
        EquipmentArmorListItem(
            armor = equipmentSet.armors?.firstOrNull { it.type == EquipmentType.ARMOR_WAIST },
            armorType = EquipmentType.ARMOR_WAIST,
            decorations = equipmentSet.decorations?.filter { it.equipmentType == EquipmentType.ARMOR_WAIST }
                ?: emptyList(),
            onArmorClick = { openArmorSelection(EquipmentType.ARMOR_WAIST) },
            onAddDecoration = { openDecorationSelection(EquipmentType.ARMOR_WAIST, it) },
            onRemoveDecoration = { onRemoveDecoration(it, EquipmentType.ARMOR_WAIST) },
        )
        EquipmentArmorListItem(
            armor = equipmentSet.armors?.firstOrNull { it.type == EquipmentType.ARMOR_LEGS },
            armorType = EquipmentType.ARMOR_LEGS,
            decorations = equipmentSet.decorations?.filter { it.equipmentType == EquipmentType.ARMOR_LEGS }
                ?: emptyList(),
            onArmorClick = { openArmorSelection(EquipmentType.ARMOR_LEGS) },
            onAddDecoration = { openDecorationSelection(EquipmentType.ARMOR_LEGS, it) },
            onRemoveDecoration = { onRemoveDecoration(it, EquipmentType.ARMOR_LEGS) },
        )
    }
}

@Composable
fun EquipmentListItem(
    name: String?,
    numberOfSlots: Int,
    decorations: List<EquipmentDecoration>,
    icon: @Composable () -> Unit,
    onEquipmentClick: () -> Unit,
    onAddDecoration: (availableSlots: Int) -> Unit,
    onRemoveDecoration: (decorationId: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val totalDecorationSlots = decorations.sumOf { it.decoration.requiredSlots * it.quantity }
    val availableSlots = numberOfSlots - totalDecorationSlots
    val expanded = rememberSaveable { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
        modifier = modifier
            .padding(
                start = Dimension.Padding.large,
                end = Dimension.Padding.large,
                top = Dimension.Padding.medium,
            )
    ) {
        Column {
            ListItemLayout(
                leadingContent = icon,
                headlineContent = {
                    Text(
                        text = name ?: stringResource(R.string.user_set_nothing_equipped),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                },
                trailingContent = {
                    if (name != null) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .size(
                                    height = Dimension.Size.tiny,
                                    width = Dimension.Size.tiny * 3,
                                )
                        ) {
                            // Add decoration first
                            decorations.forEach { decoration ->
                                repeat(decoration.decoration.requiredSlots * decoration.quantity) {
                                    SlotIcon(
                                        filled = true,
                                        color = MHFUColors.getItemColor(decoration.decoration.color),
                                        modifier = Modifier
                                            .weight(1f)
                                            .aspectRatio(1f)
                                    )
                                }
                            }

                            // Add available slots after decorations
                            if (totalDecorationSlots < numberOfSlots) {
                                repeat(availableSlots) {
                                    SlotIcon(
                                        modifier = Modifier
                                            .weight(1f)
                                            .aspectRatio(1f)
                                    )
                                }
                            }

                            // Add no slot
                            repeat(3 - numberOfSlots) {
                                NoSlotIcon(
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f)
                                )
                            }
                        }
                    }
                },
                backgroundColor = Color.Transparent,
                contentPadding = if (numberOfSlots == 0) {
                    PaddingValues(all = Dimension.Padding.large)
                } else {
                    PaddingValues(
                        start = Dimension.Padding.large,
                        end = Dimension.Padding.large,
                        top = Dimension.Padding.large,
                        bottom = Dimension.Padding.medium,
                    )
                },
                modifier = Modifier.clickable { onEquipmentClick() }
            )
            if (numberOfSlots > 0) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (totalDecorationSlots == 0) {
                                onAddDecoration(availableSlots)
                            } else {
                                expanded.value = !expanded.value
                            }
                        }
                ) {
                    Icon(
                        imageVector = if (totalDecorationSlots == 0) {
                            Icons.Default.Add
                        } else {
                            if (expanded.value) {
                                Icons.Default.KeyboardArrowUp
                            } else {
                                Icons.Default.KeyboardArrowDown
                            }
                        },
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(Dimension.Size.extraSmall)
                    )
                }
            }
            AnimatedVisibility(
                visible = expanded.value && totalDecorationSlots > 0,
            ) {
                EquipmentDecorationList(
                    availableSlots = availableSlots,
                    decorations = decorations,
                    onAddDecoration = { onAddDecoration(availableSlots) },
                    onRemoveDecoration = onRemoveDecoration,
                )
            }
        }
    }
}

@Composable
fun EquipmentDecorationList(
    availableSlots: Int,
    decorations: List<EquipmentDecoration>,
    onAddDecoration: () -> Unit,
    onRemoveDecoration: (decorationId: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        decorations.forEach { decoration ->
            repeat(decoration.quantity) {
                ListItemLayout(
                    leadingContent = {
                        Image(
                            painter = painterResource(
                                id = R.drawable.ic_item_jewel
                            ),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(
                                color = MHFUColors.getItemColor(decoration.decoration.color),
                                blendMode = BlendMode.Modulate
                            ),
                            modifier = Modifier.size(Dimension.Size.small)
                        )
                    },
                    headlineContent = {
                        Text(
                            text = decoration.decoration.name,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    },
                    trailingContent = {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier
                                .size(Dimension.Size.extraSmall)
                                .clickable { onRemoveDecoration(decoration.decoration.id) }
                        )
                    },
                    backgroundColor = Color.Transparent,
                    contentPadding = PaddingValues(
                        horizontal = Dimension.Padding.large,
                        vertical = Dimension.Padding.medium
                    ),
                )
            }
        }
        if (availableSlots > 0) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onAddDecoration() }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(Dimension.Size.extraSmall)
                )
            }
        }
    }
}

@Composable
fun EquipmentWeaponListItem(
    weapon: Weapon?,
    decorations: List<EquipmentDecoration>,
    onWeaponClick: () -> Unit,
    onAddDecoration: (availableSlots: Int) -> Unit,
    onRemoveDecoration: (decorationId: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    EquipmentListItem(
        name = weapon?.name,
        numberOfSlots = weapon?.numberOfSlots ?: 0,
        decorations = decorations,
        icon = {
            WeaponIcon(
                type = weapon?.type ?: WeaponType.GREAT_SWORD,
                rarity = weapon?.rarity ?: 1,
                modifier = Modifier.size(Dimension.Size.large)
            )
        },
        onEquipmentClick = onWeaponClick,
        onAddDecoration = onAddDecoration,
        onRemoveDecoration = onRemoveDecoration,
        modifier = modifier,
    )
}

@Composable
fun EquipmentArmorListItem(
    armor: Armor?,
    armorType: EquipmentType,
    decorations: List<EquipmentDecoration>,
    onArmorClick: () -> Unit,
    onAddDecoration: (availableSlots: Int) -> Unit,
    onRemoveDecoration: (decorationId: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    EquipmentListItem(
        name = armor?.name,
        numberOfSlots = armor?.numberOfSlots ?: 0,
        decorations = decorations,
        icon = {
            ArmorIcon(
                type = armorType,
                rarity = armor?.rarity ?: 1,
                modifier = Modifier.size(Dimension.Size.large)
            )
        },
        onEquipmentClick = onArmorClick,
        onAddDecoration = onAddDecoration,
        onRemoveDecoration = onRemoveDecoration,
        modifier = modifier,
    )
}

@DevicePreviews
@Composable
fun UserSetDetailEquipmentContentPreview() {
    Theme {
        UserSetDetailEquipmentContent(
            equipmentSet = PreviewUserEquipmentSet.userSet,
        )
    }
}

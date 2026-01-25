package com.gaugustini.mhfudatabase.ui.features.userset.detail

import android.content.res.Configuration
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
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.EquipmentType
import com.gaugustini.mhfudatabase.domain.enums.WeaponType
import com.gaugustini.mhfudatabase.domain.model.Armor
import com.gaugustini.mhfudatabase.domain.model.EquipmentDecoration
import com.gaugustini.mhfudatabase.domain.model.Weapon
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.icons.ArmorIcon
import com.gaugustini.mhfudatabase.ui.components.icons.NoSlotIcon
import com.gaugustini.mhfudatabase.ui.components.icons.SlotIcon
import com.gaugustini.mhfudatabase.ui.components.icons.WeaponIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.MHFUColors
import com.gaugustini.mhfudatabase.util.preview.PreviewArmorData
import com.gaugustini.mhfudatabase.util.preview.PreviewUserEquipmentSet
import com.gaugustini.mhfudatabase.util.preview.PreviewWeaponData

@Composable
fun UserSetDetailEquipmentContent(
    weapon: Weapon?,
    armors: List<Armor>,
    decorations: List<EquipmentDecoration>,
    modifier: Modifier = Modifier,
    onWeaponClick: () -> Unit = {},
    onArmorClick: (armorType: EquipmentType) -> Unit = {},
    onAddDecoration: (equipmentType: EquipmentType, availableSlots: Int) -> Unit = { _, _ -> },
    onRemoveDecoration: (decorationId: Int, equipmentType: EquipmentType) -> Unit = { _, _ -> },
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(bottom = Dimension.Padding.endContent)
    ) {
        EquipmentWeaponListItem(
            weapon = weapon,
            decorations = decorations.filter { it.equipmentType == EquipmentType.WEAPON },
            onWeaponClick = onWeaponClick,
            onAddDecoration = { onAddDecoration(EquipmentType.WEAPON, it) },
            onRemoveDecoration = { onRemoveDecoration(it, EquipmentType.WEAPON) },
        )
        EquipmentArmorListItem(
            armor = armors.firstOrNull { it.type == EquipmentType.ARMOR_HEAD },
            armorType = EquipmentType.ARMOR_HEAD,
            decorations = decorations.filter { it.equipmentType == EquipmentType.ARMOR_HEAD },
            onArmorClick = { onArmorClick(EquipmentType.ARMOR_HEAD) },
            onAddDecoration = { onAddDecoration(EquipmentType.ARMOR_HEAD, it) },
            onRemoveDecoration = { onRemoveDecoration(it, EquipmentType.ARMOR_HEAD) },
        )
        EquipmentArmorListItem(
            armor = armors.firstOrNull { it.type == EquipmentType.ARMOR_CHEST },
            armorType = EquipmentType.ARMOR_CHEST,
            decorations = decorations.filter { it.equipmentType == EquipmentType.ARMOR_CHEST },
            onArmorClick = { onArmorClick(EquipmentType.ARMOR_CHEST) },
            onAddDecoration = { onAddDecoration(EquipmentType.ARMOR_CHEST, it) },
            onRemoveDecoration = { onRemoveDecoration(it, EquipmentType.ARMOR_CHEST) },
        )
        EquipmentArmorListItem(
            armor = armors.firstOrNull { it.type == EquipmentType.ARMOR_ARMS },
            armorType = EquipmentType.ARMOR_ARMS,
            decorations = decorations.filter { it.equipmentType == EquipmentType.ARMOR_ARMS },
            onArmorClick = { onArmorClick(EquipmentType.ARMOR_ARMS) },
            onAddDecoration = { onAddDecoration(EquipmentType.ARMOR_ARMS, it) },
            onRemoveDecoration = { onRemoveDecoration(it, EquipmentType.ARMOR_ARMS) },
        )
        EquipmentArmorListItem(
            armor = armors.firstOrNull { it.type == EquipmentType.ARMOR_WAIST },
            armorType = EquipmentType.ARMOR_WAIST,
            decorations = decorations.filter { it.equipmentType == EquipmentType.ARMOR_WAIST },
            onArmorClick = { onArmorClick(EquipmentType.ARMOR_WAIST) },
            onAddDecoration = { onAddDecoration(EquipmentType.ARMOR_WAIST, it) },
            onRemoveDecoration = { onRemoveDecoration(it, EquipmentType.ARMOR_WAIST) },
        )
        EquipmentArmorListItem(
            armor = armors.firstOrNull { it.type == EquipmentType.ARMOR_LEGS },
            armorType = EquipmentType.ARMOR_LEGS,
            decorations = decorations.filter { it.equipmentType == EquipmentType.ARMOR_LEGS },
            onArmorClick = { onArmorClick(EquipmentType.ARMOR_LEGS) },
            onAddDecoration = { onAddDecoration(EquipmentType.ARMOR_LEGS, it) },
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

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun UserSetDetailEquipmentContentPreview() {
    Theme {
        UserSetDetailEquipmentContent(
            weapon = PreviewWeaponData.weapon,
            armors = PreviewArmorData.armorList,
            decorations = PreviewUserEquipmentSet.decorationList,
        )
    }
}

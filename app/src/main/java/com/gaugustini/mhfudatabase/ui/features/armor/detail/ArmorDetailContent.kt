package com.gaugustini.mhfudatabase.ui.features.armor.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.model.Armor
import com.gaugustini.mhfudatabase.ui.components.ButtonPage
import com.gaugustini.mhfudatabase.ui.components.DetailHeader
import com.gaugustini.mhfudatabase.ui.components.SectionHeader
import com.gaugustini.mhfudatabase.ui.components.icons.ArmorIcon
import com.gaugustini.mhfudatabase.ui.features.armor.components.EquipmentStats
import com.gaugustini.mhfudatabase.ui.features.item.components.ItemQuantityListItem
import com.gaugustini.mhfudatabase.ui.features.skill.components.SkillPointListItem
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.ForEachWithDivider
import com.gaugustini.mhfudatabase.util.preview.PreviewArmorData

@Composable
fun ArmorDetailContent(
    armor: Armor,
    modifier: Modifier = Modifier,
    onArmorSetClick: (armorSetId: Int) -> Unit = {},
    onSkillClick: (skillTreeId: Int) -> Unit = {},
    onItemClick: (itemId: Int) -> Unit = {},
) {
    var statsExpanded by rememberSaveable { mutableStateOf(true) }
    var skillsExpanded by rememberSaveable { mutableStateOf(true) }
    var recipeAExpanded by rememberSaveable { mutableStateOf(true) }
    var recipeBExpanded by rememberSaveable { mutableStateOf(true) }

    Column(
        verticalArrangement = Arrangement.spacedBy(Dimension.Padding.medium),
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                top = Dimension.Padding.medium,
                bottom = Dimension.Padding.endContent
            )
    ) {
        DetailHeader(
            icon = {
                ArmorIcon(
                    type = armor.type,
                    rarity = armor.rarity,
                )
            },
            title = armor.name,
            subtitle = stringResource(R.string.armor_rarity, armor.rarity),
            description = armor.description,
            modifier = Modifier
                .padding(horizontal = Dimension.Padding.medium)
                .clip(RoundedCornerShape(Dimension.Radius.medium))
                .background(MaterialTheme.colorScheme.surface)
        )

        ButtonPage(
            title = stringResource(R.string.armor_set_details),
            onButtonClick = { onArmorSetClick(armor.armorSetId) },
            modifier = Modifier.padding(horizontal = Dimension.Padding.medium)
        )

        Column(
            modifier = Modifier
                .padding(horizontal = Dimension.Padding.medium)
                .clip(RoundedCornerShape(Dimension.Radius.medium))
                .background(MaterialTheme.colorScheme.surface)
        ) {
            SectionHeader(
                title = stringResource(R.string.list_equipment_stats),
                isExpandable = true,
                expanded = statsExpanded,
                modifier = Modifier.clickable { statsExpanded = !statsExpanded }
            )
            AnimatedVisibility(
                visible = statsExpanded,
            ) {
                EquipmentStats(
                    numberOfSlots = armor.numberOfSlots,
                    defense = armor.defense,
                    maxDefense = armor.maxDefense,
                    fire = armor.fire,
                    water = armor.water,
                    thunder = armor.thunder,
                    ice = armor.ice,
                    dragon = armor.dragon,
                    modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                )
            }
        }

        armor.skills?.let { skills ->
            if (skills.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = Dimension.Padding.medium)
                        .clip(RoundedCornerShape(Dimension.Radius.medium))
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    SectionHeader(
                        title = stringResource(R.string.list_skills),
                        isExpandable = true,
                        expanded = skillsExpanded,
                        modifier = Modifier.clickable { skillsExpanded = !skillsExpanded }
                    )
                    AnimatedVisibility(
                        visible = skillsExpanded,
                    ) {
                        Column {
                            skills.ForEachWithDivider { skill ->
                                SkillPointListItem(
                                    skill = skill,
                                    onSkillClick = onSkillClick,
                                )
                            }
                        }
                    }
                }
            }
        }

        armor.recipes?.let { recipes ->
            if (recipes.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = Dimension.Padding.medium)
                        .clip(RoundedCornerShape(Dimension.Radius.medium))
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    SectionHeader(
                        title = stringResource(
                            if (recipes.size == 1) {
                                R.string.list_recipe
                            } else {
                                R.string.list_recipe_a
                            }
                        ),
                        isExpandable = true,
                        expanded = recipeAExpanded,
                        modifier = Modifier.clickable { recipeAExpanded = !recipeAExpanded }
                    )
                    AnimatedVisibility(
                        visible = recipeAExpanded,
                    ) {
                        Column {
                            recipes[0].ForEachWithDivider { item ->
                                ItemQuantityListItem(
                                    item = item,
                                    onItemClick = onItemClick,
                                )
                            }
                        }
                    }
                }
                if (recipes.size > 1) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = Dimension.Padding.medium)
                            .clip(RoundedCornerShape(Dimension.Radius.medium))
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        SectionHeader(
                            title = stringResource(R.string.list_recipe_b),
                            isExpandable = true,
                            expanded = recipeBExpanded,
                            modifier = Modifier.clickable { recipeBExpanded = !recipeBExpanded }
                        )
                        AnimatedVisibility(
                            visible = recipeBExpanded,
                        ) {
                            Column {
                                recipes[1].ForEachWithDivider { item ->
                                    ItemQuantityListItem(
                                        item = item,
                                        onItemClick = onItemClick,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@DevicePreviews
@Composable
fun ArmorDetailContentPreview() {
    Theme {
        ArmorDetailContent(
            armor = PreviewArmorData.armor,
        )
    }
}

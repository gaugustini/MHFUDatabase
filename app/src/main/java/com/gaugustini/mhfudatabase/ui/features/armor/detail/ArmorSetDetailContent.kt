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
import com.gaugustini.mhfudatabase.domain.model.ArmorSet
import com.gaugustini.mhfudatabase.ui.components.DetailHeader
import com.gaugustini.mhfudatabase.ui.components.SectionHeader
import com.gaugustini.mhfudatabase.ui.components.icons.ArmorSetIcon
import com.gaugustini.mhfudatabase.ui.features.armor.components.ArmorListItem
import com.gaugustini.mhfudatabase.ui.features.armor.components.EquipmentStats
import com.gaugustini.mhfudatabase.ui.features.item.components.ItemQuantityListItem
import com.gaugustini.mhfudatabase.ui.features.skill.components.SkillPointListItem
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.ForEachWithDivider
import com.gaugustini.mhfudatabase.util.preview.PreviewArmorData

@Composable
fun ArmorSetDetailContent(
    armorSet: ArmorSet,
    modifier: Modifier = Modifier,
    onArmorClick: (armorId: Int) -> Unit = {},
    onSkillClick: (skillTreeId: Int) -> Unit = {},
    onItemClick: (itemId: Int) -> Unit = {},
) {
    var statsExpanded by rememberSaveable { mutableStateOf(true) }
    var armorsExpanded by rememberSaveable { mutableStateOf(true) }
    var skillsExpanded by rememberSaveable { mutableStateOf(true) }
    var recipeExpanded by rememberSaveable { mutableStateOf(true) }

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
                ArmorSetIcon(
                    rarity = armorSet.rarity,
                )
            },
            title = armorSet.name,
            subtitle = stringResource(R.string.armor_rarity, armorSet.rarity),
            modifier = Modifier
                .padding(horizontal = Dimension.Padding.medium)
                .clip(RoundedCornerShape(Dimension.Radius.medium))
                .background(MaterialTheme.colorScheme.surface)
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
                    numberOfSlots = null,
                    defense = armorSet.defense,
                    maxDefense = armorSet.maxDefense,
                    fire = armorSet.fire,
                    water = armorSet.water,
                    thunder = armorSet.thunder,
                    ice = armorSet.ice,
                    dragon = armorSet.dragon,
                )
            }
        }

        armorSet.armors?.let { armors ->
            if (armors.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = Dimension.Padding.medium)
                        .clip(RoundedCornerShape(Dimension.Radius.medium))
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    SectionHeader(
                        title = stringResource(R.string.list_armors),
                        isExpandable = true,
                        expanded = armorsExpanded,
                        modifier = Modifier.clickable { armorsExpanded = !armorsExpanded }
                    )
                    AnimatedVisibility(
                        visible = armorsExpanded,
                    ) {
                        Column {
                            armors.ForEachWithDivider { armor ->
                                ArmorListItem(
                                    armor = armor,
                                    onArmorClick = onArmorClick,
                                )
                            }
                        }
                    }
                }
            }
        }

        armorSet.skills?.let { skills ->
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

        armorSet.recipe?.let { recipe ->
            if (recipe.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = Dimension.Padding.medium)
                        .clip(RoundedCornerShape(Dimension.Radius.medium))
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    SectionHeader(
                        title = stringResource(R.string.list_recipe),
                        isExpandable = true,
                        expanded = recipeExpanded,
                        modifier = Modifier.clickable { recipeExpanded = !recipeExpanded }
                    )
                    AnimatedVisibility(
                        visible = recipeExpanded,
                    ) {
                        Column {
                            recipe.ForEachWithDivider { item ->
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

@DevicePreviews
@Composable
fun ArmorSetDetailContentPreview() {
    Theme {
        ArmorSetDetailContent(
            armorSet = PreviewArmorData.armorSet,
        )
    }
}

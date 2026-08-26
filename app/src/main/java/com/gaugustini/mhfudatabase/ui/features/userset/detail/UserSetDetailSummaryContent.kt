package com.gaugustini.mhfudatabase.ui.features.userset.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.model.UserEquipmentSet
import com.gaugustini.mhfudatabase.ui.components.DetailHeader
import com.gaugustini.mhfudatabase.ui.components.SectionHeader
import com.gaugustini.mhfudatabase.ui.components.icons.ArmorSetIcon
import com.gaugustini.mhfudatabase.ui.features.armor.components.ArmorListItem
import com.gaugustini.mhfudatabase.ui.features.armor.components.EquipmentStats
import com.gaugustini.mhfudatabase.ui.features.decoration.components.DecorationListItem
import com.gaugustini.mhfudatabase.ui.features.item.components.ItemQuantityListItem
import com.gaugustini.mhfudatabase.ui.features.skill.components.SkillPointListItem
import com.gaugustini.mhfudatabase.ui.features.userset.components.ActiveSkillListItem
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.ForEachWithDivider
import com.gaugustini.mhfudatabase.util.preview.PreviewUserEquipmentSet

@Composable
fun UserSetDetailSummaryContent(
    equipmentSet: UserEquipmentSet,
    modifier: Modifier = Modifier,
    onArmorClick: (armorId: Int) -> Unit = {},
    onDecorationClick: (decorationId: Int) -> Unit = {},
    onItemClick: (itemId: Int) -> Unit = {},
    onSkillClick: (skillTreeId: Int) -> Unit = {},
) {
    var statsExpanded by rememberSaveable { mutableStateOf(true) }
    var activeSkillsExpanded by rememberSaveable { mutableStateOf(true) }
    var skillsExpanded by rememberSaveable { mutableStateOf(true) }
    var armorsExpanded by rememberSaveable { mutableStateOf(true) }
    var decorationsExpanded by rememberSaveable { mutableStateOf(true) }
    var recipeExpanded by rememberSaveable { mutableStateOf(true) }

    val noneText = @Composable {
        Text(
            text = stringResource(R.string.user_set_none),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = Dimension.Spacing.large,
                    vertical = Dimension.Spacing.medium
                )
        )
    }

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
                    rarity = 0,
                )
            },
            title = equipmentSet.name.ifBlank { stringResource(R.string.user_set_new) },
            modifier = Modifier
                .padding(horizontal = Dimension.Padding.medium)
                .clip(RoundedCornerShape(Dimension.Radius.medium))
                .background(MaterialTheme.colorScheme.surface)
        )

        equipmentSet.armors.let { armors ->
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
                        defense =
                            (armors?.sumOf { it.defense } ?: 0) + (equipmentSet.weapon?.defense ?: 0),
                        maxDefense =
                            (armors?.sumOf { it.maxDefense } ?: 0) + (equipmentSet.weapon?.defense ?: 0),
                        fire = armors?.sumOf { it.fire } ?: 0,
                        water = armors?.sumOf { it.water } ?: 0,
                        thunder = armors?.sumOf { it.thunder } ?: 0,
                        ice = armors?.sumOf { it.ice } ?: 0,
                        dragon = armors?.sumOf { it.dragon } ?: 0,
                    )
                }
            }
        }

        equipmentSet.activeSkills.let { activeSkills ->
            Column(
                modifier = Modifier
                    .padding(horizontal = Dimension.Padding.medium)
                    .clip(RoundedCornerShape(Dimension.Radius.medium))
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                SectionHeader(
                    title = stringResource(R.string.list_active_skills),
                    isExpandable = true,
                    expanded = activeSkillsExpanded,
                    modifier = Modifier.clickable { activeSkillsExpanded = !activeSkillsExpanded }
                )
                AnimatedVisibility(
                    visible = activeSkillsExpanded,
                ) {
                    if (activeSkills?.isEmpty() ?: true) {
                        noneText()
                    } else {
                        Column {
                            activeSkills.ForEachWithDivider { skill ->
                                ActiveSkillListItem(
                                    skill = skill,
                                    onSkillClick = onSkillClick,
                                )
                            }
                        }
                    }
                }
            }
        }

        equipmentSet.skills.let { skillPoints ->
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
                    if (skillPoints?.isEmpty() ?: true) {
                        noneText()
                    } else {
                        Column {
                            skillPoints.ForEachWithDivider { skill ->
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

        equipmentSet.armors.let { armors ->
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
                    if (armors?.isEmpty() ?: true) {
                        noneText()
                    } else {
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

        equipmentSet.decorations.let { decorations ->
            Column(
                modifier = Modifier
                    .padding(horizontal = Dimension.Padding.medium)
                    .clip(RoundedCornerShape(Dimension.Radius.medium))
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                SectionHeader(
                    title = stringResource(R.string.list_decorations),
                    isExpandable = true,
                    expanded = decorationsExpanded,
                    modifier = Modifier.clickable { decorationsExpanded = !decorationsExpanded }
                )
                AnimatedVisibility(
                    visible = decorationsExpanded,
                ) {
                    if (decorations?.isEmpty() ?: true) {
                        noneText()
                    } else {
                        val summarizedDecorations = decorations
                            .groupBy { it.decoration.id }
                            .map { (_, decoration) ->
                                decoration.first().copy(quantity = decoration.sumOf { it.quantity })
                            }
                            .sortedByDescending { it.quantity }
                        Column {
                            summarizedDecorations.forEach { (_, decoration, quantity) ->
                                DecorationListItem(
                                    decoration = decoration,
                                    onDecorationClick = onDecorationClick,
                                    trailingContent = {
                                        Text(
                                            text = quantity.toString(),
                                            style = MaterialTheme.typography.bodyMedium,
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        equipmentSet.recipe.let { materials ->
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
                    if (materials?.isEmpty() ?: true) {
                        noneText()
                    } else {
                        Column {
                            materials.ForEachWithDivider { item ->
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
fun UserSetDetailSummaryContentPreview() {
    Theme {
        UserSetDetailSummaryContent(
            equipmentSet = PreviewUserEquipmentSet.userSet,
        )
    }
}

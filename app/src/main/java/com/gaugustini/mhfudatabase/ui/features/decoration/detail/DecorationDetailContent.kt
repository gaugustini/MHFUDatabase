package com.gaugustini.mhfudatabase.ui.features.decoration.detail

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
import com.gaugustini.mhfudatabase.domain.model.Decoration
import com.gaugustini.mhfudatabase.ui.components.AppHDivider
import com.gaugustini.mhfudatabase.ui.components.DetailHeader
import com.gaugustini.mhfudatabase.ui.components.SectionHeader
import com.gaugustini.mhfudatabase.ui.components.icons.DecorationIcon
import com.gaugustini.mhfudatabase.ui.features.decoration.components.DecorationSummary
import com.gaugustini.mhfudatabase.ui.features.item.components.ItemQuantityListItem
import com.gaugustini.mhfudatabase.ui.features.skill.components.SkillPointListItem
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.ForEachWithDivider
import com.gaugustini.mhfudatabase.util.preview.PreviewDecorationData

@Composable
fun DecorationDetailContent(
    decoration: Decoration,
    modifier: Modifier = Modifier,
    onSkillClick: (skillTreeId: Int) -> Unit = {},
    onItemClick: (itemId: Int) -> Unit = {},
) {
    var skillsExpanded by rememberSaveable { mutableStateOf(true) }
    var recipeAExpanded by rememberSaveable { mutableStateOf(true) }
    var recipeBExpanded by rememberSaveable { mutableStateOf(true) }

    Column(
        verticalArrangement = Arrangement.spacedBy(Dimension.Padding.medium),
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = Dimension.Padding.endContent)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = Dimension.Padding.medium)
                .clip(RoundedCornerShape(Dimension.Radius.medium))
                .background(MaterialTheme.colorScheme.surface)
        ) {
            DetailHeader(
                icon = {
                    DecorationIcon(
                        color = decoration.color,
                    )
                },
                title = decoration.name,
                subtitle = stringResource(R.string.decoration_rarity, decoration.rarity),
                description = decoration.description,
            )
            AppHDivider()
            DecorationSummary(
                decoration = decoration,
            )
        }

        decoration.skills?.let { skills ->
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

        decoration.recipeA?.let { recipeA ->
            if (recipeA.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = Dimension.Padding.medium)
                        .clip(RoundedCornerShape(Dimension.Radius.medium))
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    SectionHeader(
                        title = stringResource(
                            if (decoration.recipeB?.isNotEmpty() ?: false) {
                                R.string.list_recipe_a
                            } else {
                                R.string.list_recipe
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
                            recipeA.ForEachWithDivider { item ->
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

        decoration.recipeB?.let { recipeB ->
            if (recipeB.isNotEmpty()) {
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
                            recipeB.ForEachWithDivider { item ->
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
fun DecorationDetailContentPreview() {
    Theme {
        DecorationDetailContent(
            decoration = PreviewDecorationData.decoration,
        )
    }
}

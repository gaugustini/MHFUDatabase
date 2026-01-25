package com.gaugustini.mhfudatabase.ui.features.decoration.detail

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.model.Decoration
import com.gaugustini.mhfudatabase.ui.components.DetailHeader
import com.gaugustini.mhfudatabase.ui.components.SectionHeader
import com.gaugustini.mhfudatabase.ui.components.icons.DecorationIcon
import com.gaugustini.mhfudatabase.ui.features.decoration.components.DecorationSummary
import com.gaugustini.mhfudatabase.ui.features.item.components.ItemQuantityList
import com.gaugustini.mhfudatabase.ui.features.skill.components.SkillTreePointsList
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewDecorationData

@Composable
fun DecorationDetailContent(
    decoration: Decoration,
    modifier: Modifier = Modifier,
    onSkillClick: (skillTreeId: Int) -> Unit = {},
    onItemClick: (itemId: Int) -> Unit = {},
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(bottom = Dimension.Padding.endContent)
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

        DecorationSummary(
            decoration = decoration,
        )

        decoration.skills?.let { skills ->
            if (skills.isNotEmpty()) {
                SectionHeader(
                    title = stringResource(R.string.list_skills),
                )
                SkillTreePointsList(
                    skills = skills,
                    onSkillClick = onSkillClick,
                )
            }
        }

        decoration.recipeA?.let { recipeA ->
            if (recipeA.isNotEmpty()) {
                SectionHeader(
                    title = stringResource(
                        if (decoration.recipeB?.isNotEmpty() ?: false) {
                            R.string.list_recipe_a
                        } else {
                            R.string.list_recipe
                        }
                    ),
                )
                ItemQuantityList(
                    items = recipeA,
                    onItemClick = onItemClick,
                )
            }
        }

        decoration.recipeB?.let { recipeB ->
            if (recipeB.isNotEmpty()) {
                SectionHeader(
                    title = stringResource(R.string.list_recipe_b),
                )
                ItemQuantityList(
                    items = recipeB,
                    onItemClick = onItemClick,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DecorationDetailContentPreview() {
    Theme {
        DecorationDetailContent(
            decoration = PreviewDecorationData.decoration,
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
        )
    }
}

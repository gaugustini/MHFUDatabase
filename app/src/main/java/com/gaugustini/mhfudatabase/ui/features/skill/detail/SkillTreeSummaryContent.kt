package com.gaugustini.mhfudatabase.ui.features.skill.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.model.Skill
import com.gaugustini.mhfudatabase.domain.model.SkillTree
import com.gaugustini.mhfudatabase.ui.components.ButtonPage
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.ForEachWithDivider
import com.gaugustini.mhfudatabase.util.preview.PreviewSkillData

@Composable
fun SkillTreeSummaryContent(
    skillTree: SkillTree,
    modifier: Modifier = Modifier,
    onChangePage: (SkillTreeDetailPage) -> Unit = {},
) {
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
            skillTree.skills?.ForEachWithDivider { skill ->
                SkillListItem(
                    skill = skill,
                )
            }
        }

        ButtonPage(
            title = stringResource(R.string.skill_tree_equipment),
            onButtonClick = { onChangePage(SkillTreeDetailPage.EQUIPMENT) },
            modifier = Modifier.padding(horizontal = Dimension.Padding.medium)
        )
    }
}

@Composable
fun SkillListItem(
    skill: Skill,
    modifier: Modifier = Modifier,
) {
    ListItemLayout(
        headlineContent = {
            Text(
                text = skill.name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold
            )
        },
        supportingContent = {
            Text(
                text = skill.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        },
        trailingContent = {
            Text(
                text = skill.requiredPoints.toString(),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        modifier = modifier
    )
}

@DevicePreviews
@Composable
fun SkillTreeSummaryContentPreview() {
    Theme {
        SkillTreeSummaryContent(
            skillTree = PreviewSkillData.skillTree,
        )
    }
}

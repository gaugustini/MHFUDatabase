package com.gaugustini.mhfudatabase.ui.features.skill.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gaugustini.mhfudatabase.domain.model.Skill
import com.gaugustini.mhfudatabase.domain.model.SkillTree
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.ForEachWithDivider
import com.gaugustini.mhfudatabase.util.preview.PreviewSkillData

@Composable
fun SkillTreeSummaryContent(
    skillTree: SkillTree,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        skillTree.skills?.ForEachWithDivider { skill ->
            SkillListItem(
                skill = skill,
            )
        }
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

package com.gaugustini.mhfudatabase.ui.skill.detail

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.data.model.Skill
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewSkillData

@Composable
fun SkillTreeSummaryContent(
    skills: List<Skill>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        skills.forEach { skill ->
            SkillListItem(
                skill = skill,
            )
            HorizontalDivider()
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

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SkillTreeSummaryContentPreview() {
    Theme {
        SkillTreeSummaryContent(
            skills = PreviewSkillData.skillList,
        )
    }
}

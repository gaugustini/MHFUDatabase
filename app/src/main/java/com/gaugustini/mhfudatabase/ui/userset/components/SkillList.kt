package com.gaugustini.mhfudatabase.ui.userset.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
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
fun SkillList(
    skills: List<Skill>,
    modifier: Modifier = Modifier,
    onSkillClick: (skillTreeId: Int) -> Unit = {},
) {
    Column(
        modifier = modifier
    ) {
        skills.forEachIndexed { index, skill ->
            SkillListItem(
                skill = skill,
                onSkillClick = onSkillClick,
            )
            if (index != skills.lastIndex) {
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun SkillListItem(
    skill: Skill,
    modifier: Modifier = Modifier,
    onSkillClick: (skillTreeId: Int) -> Unit = {},
) {
    ListItemLayout(
        headlineContent = {
            Text(
                text = skill.name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        trailingContent = {
            Text(
                text = skill.requiredPoints.toString(), // TODO: Add set points
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        modifier = modifier.clickable { onSkillClick(skill.skillTreeId) }
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SkillListPreview() {
    Theme {
        SkillList(PreviewSkillData.skillList)
    }
}

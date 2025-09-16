package com.gaugustini.mhfudatabase.ui.skill.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.data.model.SkillTreePoints
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewSkillData

@Composable
fun SkillTreePointsList(
    skills: List<SkillTreePoints>,
    modifier: Modifier = Modifier,
    onSkillClick: (skillTreeId: Int) -> Unit = {},
) {
    Column(
        modifier = modifier
    ) {
        skills.forEachIndexed { index, skill ->
            SkillTreePointsListItem(
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
fun SkillTreePointsListItem(
    skill: SkillTreePoints,
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
                text = skill.pointValue.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        modifier = modifier.clickable { onSkillClick(skill.id) }
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SkillTreePointsListPreview() {
    Theme {
        SkillTreePointsList(PreviewSkillData.skillTreePointsList)
    }
}

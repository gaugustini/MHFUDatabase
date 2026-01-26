package com.gaugustini.mhfudatabase.ui.features.skill.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.domain.model.SkillTree
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewSkillData

@Composable
fun SkillTreeList(
    skills: List<SkillTree>,
    modifier: Modifier = Modifier,
    onSkillTreeClick: (skillTreeId: Int) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(skills) { skillTree ->
            SkillTreeListItem(
                skillTree = skillTree,
                onSkillTreeClick = onSkillTreeClick,
            )
            HorizontalDivider()
        }
    }
}

@Composable
fun SkillTreeListItem(
    skillTree: SkillTree,
    modifier: Modifier = Modifier,
    onSkillTreeClick: (skillTreeId: Int) -> Unit = {},
) {
    ListItemLayout(
        headlineContent = {
            Text(
                text = skillTree.name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        modifier = modifier.clickable { onSkillTreeClick(skillTree.id) }
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SkillTreeListPreview() {
    Theme {
        SkillTreeList(
            skills = PreviewSkillData.skillTreeList,
        )
    }
}

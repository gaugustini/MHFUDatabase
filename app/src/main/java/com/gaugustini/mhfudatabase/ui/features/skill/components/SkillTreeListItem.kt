package com.gaugustini.mhfudatabase.ui.features.skill.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gaugustini.mhfudatabase.domain.model.SkillTree
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewSkillData

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

@DevicePreviews
@Composable
fun SkillTreeListItemPreview() {
    Theme {
        SkillTreeListItem(
            skillTree = PreviewSkillData.skillTree,
        )
    }
}

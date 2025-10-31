package com.gaugustini.mhfudatabase.ui.skill.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.data.model.SkillPointsDecoration
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.icons.DecorationIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewSkillData

@Composable
fun SkillPointsDecorationListItem(
    decoration: SkillPointsDecoration,
    modifier: Modifier = Modifier,
    onDecorationClick: (decorationId: Int) -> Unit = {},
) {
    ListItemLayout(
        leadingContent = {
            DecorationIcon(
                color = decoration.decorationColor,
                modifier = Modifier.size(Dimension.Size.medium)
            )
        },
        headlineContent = {
            Text(
                text = decoration.decorationName,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        trailingContent = {
            Text(
                text = decoration.pointValue.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        contentPadding = PaddingValues(
            horizontal = Dimension.Spacing.large,
            vertical = Dimension.Spacing.medium
        ),
        modifier = modifier.clickable { onDecorationClick(decoration.decorationId) }
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SkillPointsDecorationListItemPreview() {
    Theme {
        SkillPointsDecorationListItem(
            decoration = PreviewSkillData.skillPointsDecoration,
        )
    }
}

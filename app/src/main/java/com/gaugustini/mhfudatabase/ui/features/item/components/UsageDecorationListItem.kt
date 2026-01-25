package com.gaugustini.mhfudatabase.ui.features.item.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.domain.model.Decoration
import com.gaugustini.mhfudatabase.domain.model.Usage
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.icons.DecorationIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewItemData

@Composable
fun UsageDecorationListItem(
    usage: Usage<Decoration>,
    modifier: Modifier = Modifier,
    onDecorationClick: (decorationId: Int) -> Unit = {},
) {
    ListItemLayout(
        leadingContent = {
            DecorationIcon(
                color = usage.craftable.color,
                modifier = Modifier.size(Dimension.Size.medium)
            )
        },
        headlineContent = {
            Text(
                text = usage.craftable.name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        trailingContent = {
            Text(
                text = usage.quantity.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        contentPadding = PaddingValues(
            horizontal = Dimension.Spacing.large,
            vertical = Dimension.Spacing.medium
        ),
        modifier = modifier.clickable { onDecorationClick(usage.craftable.id) }
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun UsageDecorationListItemPreview() {
    Theme {
        UsageDecorationListItem(
            usage = PreviewItemData.itemUsageDecoration,
        )
    }
}

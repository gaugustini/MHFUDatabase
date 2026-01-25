package com.gaugustini.mhfudatabase.ui.decoration.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.domain.model.Decoration
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.icons.DecorationIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewDecorationData

@Composable
fun DecorationList(
    decorations: List<Decoration>,
    modifier: Modifier = Modifier,
    onDecorationClick: (decorationId: Int) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(decorations) { decoration ->
            DecorationListItem(
                decoration = decoration,
                onDecorationClick = onDecorationClick,
            )
            HorizontalDivider()
        }
    }
}

@Composable
fun DecorationListItem(
    decoration: Decoration,
    modifier: Modifier = Modifier,
    onDecorationClick: (decorationId: Int) -> Unit = {},
) {
    ListItemLayout(
        leadingContent = {
            DecorationIcon(
                color = decoration.color,
                modifier = Modifier.size(Dimension.Size.medium)
            )
        },
        headlineContent = {
            Text(
                text = decoration.name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        contentPadding = PaddingValues(
            horizontal = Dimension.Spacing.large,
            vertical = Dimension.Spacing.medium
        ),
        modifier = modifier.clickable { onDecorationClick(decoration.id) }
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DecorationListPreview() {
    Theme {
        DecorationList(PreviewDecorationData.decorationList)
    }
}

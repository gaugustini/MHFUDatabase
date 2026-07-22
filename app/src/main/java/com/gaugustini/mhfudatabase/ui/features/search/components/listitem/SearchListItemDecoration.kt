package com.gaugustini.mhfudatabase.ui.features.search.components.listitem

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.model.Decoration
import com.gaugustini.mhfudatabase.ui.components.AppHDivider
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.icons.DecorationIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewDecorationData

@Composable
fun SearchListItem(
    decoration: Decoration,
    modifier: Modifier = Modifier,
    onDecorationClick: (decorationId: Int) -> Unit = {},
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(Dimension.Radius.small))
            .background(MaterialTheme.colorScheme.surface)
    ) {
        ListItemLayout(
            leadingContent = {
                DecorationIcon(
                    color = decoration.color,
                    modifier = Modifier.size(SearchListItemDefaults.IconSize)
                )
            },
            headlineContent = {
                Text(
                    text = decoration.name,
                    style = SearchListItemDefaults.HeadlineTextStyle,
                    color = SearchListItemDefaults.HeadlineTextColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            },
            trailingContent = {
                Text(
                    text = stringResource(R.string.search_decoration),
                    style = SearchListItemDefaults.TrailingTextStyle,
                    color = SearchListItemDefaults.TrailingTextColor,
                )
            },
            contentPadding = PaddingValues(
                horizontal = SearchListItemDefaults.HorizontalPadding,
                vertical = SearchListItemDefaults.VerticalPadding,
            ),
            modifier = Modifier.clickable { onDecorationClick(decoration.id) }
        )
        AppHDivider()
    }
}

@DevicePreviews
@Composable
fun SearchListItemDecorationPreview() {
    Theme {
        SearchListItem(
            decoration = PreviewDecorationData.decoration,
        )
    }
}

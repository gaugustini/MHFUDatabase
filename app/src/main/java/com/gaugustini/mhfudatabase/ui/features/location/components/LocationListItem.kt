package com.gaugustini.mhfudatabase.ui.features.location.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gaugustini.mhfudatabase.domain.model.Location
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.icons.LocationIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewLocationData

@Composable
fun LocationListItem(
    location: Location,
    modifier: Modifier = Modifier,
    onLocationClick: (locationId: Int) -> Unit = {},
) {
    ListItemLayout(
        leadingContent = {
            LocationIcon(
                locationId = location.id,
                modifier = Modifier.size(Dimension.Size.extraLarge)
            )
        },
        headlineContent = {
            Text(
                text = location.name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        contentPadding = PaddingValues(
            horizontal = Dimension.Padding.large,
            vertical = Dimension.Padding.medium,
        ),
        modifier = modifier.clickable { onLocationClick(location.id) }
    )
}

@DevicePreviews
@Composable
fun LocationListItemPreview() {
    Theme {
        LocationListItem(
            location = PreviewLocationData.location,
        )
    }
}

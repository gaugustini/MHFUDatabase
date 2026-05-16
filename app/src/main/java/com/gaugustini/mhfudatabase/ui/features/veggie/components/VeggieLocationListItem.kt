package com.gaugustini.mhfudatabase.ui.features.veggie.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.model.VeggieLocation
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.icons.LocationIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewVeggieData

@Composable
fun VeggieLocationListItem(
    veggieLocation: VeggieLocation,
    modifier: Modifier = Modifier,
    onVeggieLocationClick: (tableId: Int) -> Unit = {},
) {
    ListItemLayout(
        leadingContent = {
            LocationIcon(
                locationId = veggieLocation.location.id,
                modifier = Modifier.size(Dimension.Size.extraLarge)
            )
        },
        headlineContent = {
            Text(
                text = veggieLocation.location.name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        supportingContent = {
            Text(
                text = if (veggieLocation.locationArea == 0) {
                    stringResource(R.string.location_base_camp)
                } else {
                    stringResource(R.string.location_area, veggieLocation.locationArea)
                },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        },
        contentPadding = PaddingValues(
            horizontal = Dimension.Padding.large,
            vertical = Dimension.Padding.medium,
        ),
        modifier = modifier.clickable { onVeggieLocationClick(veggieLocation.id) }
    )
}

@DevicePreviews
@Composable
fun VeggieLocationListItemPreview() {
    Theme {
        VeggieLocationListItem(
            veggieLocation = PreviewVeggieData.veggieLocation,
        )
    }
}

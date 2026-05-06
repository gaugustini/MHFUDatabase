package com.gaugustini.mhfudatabase.ui.features.veggie.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gaugustini.mhfudatabase.domain.model.VeggieLocation
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
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
        headlineContent = {
            Text(
                text = veggieLocation.location.name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
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

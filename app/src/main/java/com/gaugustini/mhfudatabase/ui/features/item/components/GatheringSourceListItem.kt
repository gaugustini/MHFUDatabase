package com.gaugustini.mhfudatabase.ui.features.item.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.GatherType
import com.gaugustini.mhfudatabase.domain.model.GatheringSource
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.icons.LocationIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewItemData

@Composable
fun GatheringSourceListItem(
    source: GatheringSource,
    modifier: Modifier = Modifier,
    onLocationClick: (locationId: Int) -> Unit = {},
) {
    ListItemLayout(
        leadingContent = {
            LocationIcon(
                locationId = source.location.id,
                modifier = Modifier.size(Dimension.Size.medium)
            )
        },
        headlineContent = {
            val area = when (source.area) {
                -1 -> stringResource(R.string.location_secret_area)
                0 -> stringResource(R.string.location_base_camp)
                else -> stringResource(R.string.location_area, source.area)
            }

            Text(
                text = area,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        trailingContent = {
            val gatherType = when (source.type) {
                GatherType.COLLECT -> stringResource(R.string.location_gather_collect)
                GatherType.MINE -> stringResource(R.string.location_gather_mine)
                GatherType.BUG -> stringResource(R.string.location_gather_bug)
                GatherType.FISH -> stringResource(R.string.location_gather_fish)
            }

            Text(
                text = gatherType,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        contentPadding = PaddingValues(
            horizontal = Dimension.Padding.large,
            vertical = Dimension.Padding.medium,
        ),
        modifier = modifier.clickable { onLocationClick(source.location.id) }
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun GatheringSourceListItemPreview() {
    Theme {
        GatheringSourceListItem(
            source = PreviewItemData.gatheringSource,
        )
    }
}

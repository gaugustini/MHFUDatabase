package com.gaugustini.mhfudatabase.ui.features.item.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.domain.model.GatheringPoint
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.icons.ItemIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewItemData

@Composable
fun GatheringPointList(
    gatheringPoints: List<GatheringPoint>,
    modifier: Modifier = Modifier,
    onItemClick: (itemId: Int) -> Unit = {},
) {
    Column(
        modifier = modifier
    ) {
        gatheringPoints.forEachIndexed { index, item ->
            GatheringPointListItem(
                gatheringPoint = item,
                onItemClick = onItemClick,
            )
            if (index != gatheringPoints.lastIndex) {
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun GatheringPointListItem(
    gatheringPoint: GatheringPoint,
    modifier: Modifier = Modifier,
    onItemClick: (itemId: Int) -> Unit = {},
) {
    ListItemLayout(
        leadingContent = {
            ItemIcon(
                type = gatheringPoint.item.iconType,
                color = gatheringPoint.item.iconColor,
                modifier = Modifier.size(Dimension.Size.medium)
            )
        },
        headlineContent = {
            Text(
                text = gatheringPoint.item.name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        contentPadding = PaddingValues(
            horizontal = Dimension.Padding.large,
            vertical = Dimension.Padding.medium,
        ),
        modifier = modifier.clickable { onItemClick(gatheringPoint.item.id) }
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun GatheringPointListPreview() {
    Theme {
        GatheringPointList(PreviewItemData.itemLocationList)
    }
}

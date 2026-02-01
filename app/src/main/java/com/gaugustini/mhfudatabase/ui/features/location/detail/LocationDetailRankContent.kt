package com.gaugustini.mhfudatabase.ui.features.location.detail

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.GatherType
import com.gaugustini.mhfudatabase.domain.model.GatheringPoint
import com.gaugustini.mhfudatabase.ui.components.SectionHeader
import com.gaugustini.mhfudatabase.ui.features.location.components.GatheringPointListItem
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewLocationData

@Composable
fun LocationDetailRankContent(
    gatheringPoints: List<GatheringPoint>,
    modifier: Modifier = Modifier,
    onItemClick: (itemId: Int) -> Unit = {},
) {
    val itemsPerArea = gatheringPoints.groupBy { it.area }

    LazyColumn(
        modifier = modifier
    ) {
        itemsPerArea.forEach { (area, items) ->
            stickyHeader {
                SectionHeader(
                    title = when (area) {
                        -1 -> stringResource(R.string.location_secret_area)
                        0 -> stringResource(R.string.location_base_camp)
                        else -> stringResource(R.string.location_area, area)
                    },
                )
            }

            val itemsPerType = items.groupBy { it.type }

            itemsPerType.forEach { (type, items) ->
                item {
                    SectionHeader(
                        title = stringResource(
                            when (type) {
                                GatherType.COLLECT -> R.string.location_gather_collect
                                GatherType.MINE -> R.string.location_gather_mine
                                GatherType.BUG -> R.string.location_gather_bug
                                GatherType.FISH -> R.string.location_gather_fish
                            }
                        ),
                        titleColor = MaterialTheme.colorScheme.primary,
                        backgroundColor = MaterialTheme.colorScheme.surface,
                        modifier = Modifier.padding(start = Dimension.Padding.large)
                    )
                }
                itemsIndexed(items) { index, point ->
                    GatheringPointListItem(
                        gatheringPoint = point,
                        onItemClick = onItemClick,
                    )
                    if (index != items.lastIndex) {
                        HorizontalDivider()
                    }
                }
            }

            item {
                Spacer(Modifier.height(Dimension.Spacing.large))
            }
        }
    }
}

@DevicePreviews
@Composable
fun LocationDetailRankContentPreview() {
    Theme {
        LocationDetailRankContent(
            gatheringPoints = PreviewLocationData.gatheringPointList,
        )
    }
}

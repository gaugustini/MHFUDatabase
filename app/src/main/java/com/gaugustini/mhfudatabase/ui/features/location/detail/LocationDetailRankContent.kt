package com.gaugustini.mhfudatabase.ui.features.location.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.GatherType
import com.gaugustini.mhfudatabase.domain.model.GatheringPoint
import com.gaugustini.mhfudatabase.ui.components.AppHDivider
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
    val itemsPerNode = gatheringPoints.groupBy { it.node }

    LazyColumn(
        contentPadding = PaddingValues(Dimension.Padding.medium),
        modifier = modifier.fillMaxSize()
    ) {
        itemsPerNode.forEach { (node, items) ->
            val firstItem = items.first()

            val nodeType = when (firstItem.type) {
                GatherType.COLLECT -> R.string.location_gather_collect
                GatherType.MINE -> R.string.location_gather_mine
                GatherType.BUG -> R.string.location_gather_bug
                GatherType.FISH -> R.string.location_gather_fish
            }

            val nodeMinMax = when (firstItem.min) {
                -1 -> "∞"
                firstItem.max -> "${firstItem.min}"
                else -> "${firstItem.min}~${firstItem.max}"
            }

            item {
                SectionHeader(
                    title = stringResource(
                        R.string.location_node,
                        node,
                        stringResource(nodeType),
                        nodeMinMax
                    ),
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(
                                topStart = Dimension.Radius.medium,
                                topEnd = Dimension.Radius.medium,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp
                            )
                        )
                )
            }

            itemsIndexed(
                items = items,
                key = { _, point ->
                    "point_${point.rank}_${point.area}_${point.node}_${point.item.id}"
                }
            ) { index, point ->
                val isLastItem = index == items.lastIndex

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                bottomStart = if (isLastItem) Dimension.Radius.medium else 0.dp,
                                bottomEnd = if (isLastItem) Dimension.Radius.medium else 0.dp,
                            )
                        )
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    GatheringPointListItem(
                        gatheringPoint = point,
                        onItemClick = onItemClick,
                    )
                    if (!isLastItem) {
                        AppHDivider()
                    }
                }
                if (isLastItem) {
                    Spacer(modifier = Modifier.height(Dimension.Spacing.medium))
                }
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

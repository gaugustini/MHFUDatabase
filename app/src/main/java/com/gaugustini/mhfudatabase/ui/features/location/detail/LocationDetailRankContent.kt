package com.gaugustini.mhfudatabase.ui.features.location.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.GatherType
import com.gaugustini.mhfudatabase.domain.enums.Rank
import com.gaugustini.mhfudatabase.domain.model.GatheringPoint
import com.gaugustini.mhfudatabase.ui.components.AppHDivider
import com.gaugustini.mhfudatabase.ui.components.SectionHeader
import com.gaugustini.mhfudatabase.ui.features.location.components.GatheringPointListItem
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.animateItemExpandCollapse
import com.gaugustini.mhfudatabase.util.preview.PreviewLocationData

@Composable
fun LocationDetailRankContent(
    gatheringPoints: List<GatheringPoint>,
    modifier: Modifier = Modifier,
    rank: Rank? = null,
    onItemClick: (itemId: Int) -> Unit = {},
) {
    val itemsPerArea = gatheringPoints.groupBy { it.area }
    var expandedAreas by rememberSaveable(rank) {
        mutableStateOf(itemsPerArea.keys.toSet())
    }

    LazyColumn(
        contentPadding = PaddingValues(Dimension.Padding.medium),
        modifier = modifier.fillMaxSize()
    ) {
        itemsPerArea.forEach { (area, items) ->
            val isAreaExpanded = area in expandedAreas

            item {
                SectionHeader(
                    title = when (area) {
                        -1 -> stringResource(R.string.location_secret_area)
                        0 -> stringResource(R.string.location_base_camp)
                        else -> stringResource(R.string.location_area, area)
                    },
                    isExpandable = true,
                    expanded = isAreaExpanded,
                    modifier = Modifier
                        .padding(bottom = if (isAreaExpanded) 0.dp else Dimension.Padding.medium)
                        .clip(
                            RoundedCornerShape(
                                topStart = Dimension.Radius.medium,
                                topEnd = Dimension.Radius.medium,
                                bottomStart = if (isAreaExpanded) 0.dp else Dimension.Radius.medium,
                                bottomEnd = if (isAreaExpanded) 0.dp else Dimension.Radius.medium,
                            )
                        )
                        .clickable {
                            expandedAreas =
                                if (isAreaExpanded) expandedAreas - area else expandedAreas + area
                        }
                )
            }

            if (isAreaExpanded) {
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
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateItemExpandCollapse(this)
                        )
                    }
                    itemsIndexed(
                        items = items,
                        key = { _, point ->
                            "point_${point.rank}_${point.area}_${point.type}_${point.item.id}"
                        }
                    ) { index, point ->
                        val isLastType = type == itemsPerType.keys.last()
                        val isLastItemInType = index == items.lastIndex
                        val isLastItemInArea = isLastItemInType && isLastType

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(
                                    RoundedCornerShape(
                                        bottomStart = if (isLastItemInArea) Dimension.Radius.medium else 0.dp,
                                        bottomEnd = if (isLastItemInArea) Dimension.Radius.medium else 0.dp,
                                    )
                                )
                                .background(MaterialTheme.colorScheme.surface)
                                .animateItemExpandCollapse(this)
                        ) {
                            GatheringPointListItem(
                                gatheringPoint = point,
                                onItemClick = onItemClick,
                            )
                            if (!isLastItemInArea) {
                                AppHDivider()
                            }
                        }
                        if (isLastItemInArea) {
                            Spacer(modifier = Modifier.height(Dimension.Spacing.medium))
                        }
                    }
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

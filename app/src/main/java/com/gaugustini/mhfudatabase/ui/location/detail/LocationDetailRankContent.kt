package com.gaugustini.mhfudatabase.ui.location.detail

import android.content.res.Configuration
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.data.enums.GatherType
import com.gaugustini.mhfudatabase.data.model.ItemLocation
import com.gaugustini.mhfudatabase.ui.components.SectionHeader
import com.gaugustini.mhfudatabase.ui.item.components.ItemLocationList
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewItemData

@Composable
fun LocationDetailRankContent(
    items: List<ItemLocation>,
    modifier: Modifier = Modifier,
    onItemClick: (itemId: Int) -> Unit = {},
) {
    val itemsPerArea = items.groupBy { it.area }

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

            items.groupBy { it.type }.forEach { (type, items) ->
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
                item {
                    ItemLocationList(
                        items = items,
                        onItemClick = onItemClick,
                    )
                }
            }

            item {
                Spacer(Modifier.height(Dimension.Spacing.large))
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LocationDetailRankContentPreview() {
    Theme {
        LocationDetailRankContent(PreviewItemData.itemLocationList)
    }
}

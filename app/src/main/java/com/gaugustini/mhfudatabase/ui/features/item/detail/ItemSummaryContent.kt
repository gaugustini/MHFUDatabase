package com.gaugustini.mhfudatabase.ui.features.item.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.model.Item
import com.gaugustini.mhfudatabase.ui.components.AppHDivider
import com.gaugustini.mhfudatabase.ui.components.ButtonPage
import com.gaugustini.mhfudatabase.ui.components.DetailHeader
import com.gaugustini.mhfudatabase.ui.components.icons.ItemIcon
import com.gaugustini.mhfudatabase.ui.features.item.components.ItemSummary
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewItemData

@Composable
fun ItemSummaryContent(
    item: Item,
    modifier: Modifier = Modifier,
    onChangePage: (ItemDetailPage) -> Unit = {},
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Dimension.Padding.medium),
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = Dimension.Padding.endContent)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = Dimension.Padding.medium)
                .clip(RoundedCornerShape(Dimension.Radius.medium))
                .background(MaterialTheme.colorScheme.surface)
        ) {
            DetailHeader(
                icon = {
                    ItemIcon(
                        type = item.iconType,
                        color = item.iconColor,
                    )
                },
                title = item.name,
                subtitle = stringResource(R.string.item_rarity, item.rarity),
                description = item.description,
            )
            AppHDivider()
            ItemSummary(
                item = item,
            )
        }

        if (item.usages?.isEmpty() == false) {
            ButtonPage(
                title = stringResource(R.string.item_usages),
                onButtonClick = { onChangePage(ItemDetailPage.ITEM_USAGES) },
                modifier = Modifier.padding(horizontal = Dimension.Padding.medium)
            )
        }

        if (item.sources?.isEmpty() == false) {
            ButtonPage(
                title = stringResource(R.string.item_sources),
                onButtonClick = { onChangePage(ItemDetailPage.ITEM_SOURCES) },
                modifier = Modifier.padding(horizontal = Dimension.Padding.medium)
            )
        }
    }
}

@DevicePreviews
@Composable
fun ItemSummaryContentPreview() {
    Theme {
        ItemSummaryContent(
            item = PreviewItemData.item,
        )
    }
}

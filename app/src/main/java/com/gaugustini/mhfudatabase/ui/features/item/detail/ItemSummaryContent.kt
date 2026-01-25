package com.gaugustini.mhfudatabase.ui.features.item.detail

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.model.Item
import com.gaugustini.mhfudatabase.ui.components.DetailHeader
import com.gaugustini.mhfudatabase.ui.components.icons.ItemIcon
import com.gaugustini.mhfudatabase.ui.features.item.components.ItemSummary
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewItemData

@Composable
fun ItemSummaryContent(
    item: Item,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(bottom = Dimension.Padding.endContent)
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

        ItemSummary(
            item = item,
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ItemSummaryContentPreview() {
    Theme {
        ItemSummaryContent(
            item = PreviewItemData.item,
        )
    }
}

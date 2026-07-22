package com.gaugustini.mhfudatabase.ui.features.quest.detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.gaugustini.mhfudatabase.domain.model.QuestSupply
import com.gaugustini.mhfudatabase.ui.features.quest.components.QuestSupplyListItem
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.itemsWithDivider
import com.gaugustini.mhfudatabase.util.preview.PreviewQuestData

@Composable
fun QuestDetailSupplyContent(
    supplies: List<QuestSupply>,
    modifier: Modifier = Modifier,
    onChangePage: (QuestDetailPage) -> Unit = {},
    onItemClick: (itemId: Int) -> Unit = {},
) {
    BackHandler {
        onChangePage(QuestDetailPage.SUMMARY)
    }

    LazyColumn(
        contentPadding = PaddingValues(Dimension.Padding.medium),
        modifier = modifier.fillMaxSize()
    ) {
        itemsWithDivider(
            items = supplies,
            key = { it.boxOrder }
        ) { supply ->
            QuestSupplyListItem(
                supply = supply,
                onItemClick = onItemClick,
                modifier = Modifier
                    .clip(RoundedCornerShape(Dimension.Radius.small))
                    .background(MaterialTheme.colorScheme.surface)
            )
        }
    }
}

@DevicePreviews
@Composable
fun QuestDetailSupplyContentPreview() {
    Theme {
        QuestDetailSupplyContent(
            supplies = PreviewQuestData.questSupplyList,
        )
    }
}

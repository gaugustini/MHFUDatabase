package com.gaugustini.mhfudatabase.ui.features.location.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.model.Location
import com.gaugustini.mhfudatabase.ui.components.ButtonPage
import com.gaugustini.mhfudatabase.ui.features.location.components.LocationMapDialog
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.MHFUIcons
import com.gaugustini.mhfudatabase.util.preview.PreviewLocationData

@Composable
fun LocationSummaryContent(
    location: Location,
    modifier: Modifier = Modifier,
    onChangePage: (LocationDetailPage) -> Unit = {},
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Dimension.Padding.medium),
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = Dimension.Padding.endContent)
    ) {
        LocationMap(
            locationId = location.id,
            modifier = Modifier
                .padding(horizontal = Dimension.Padding.medium)
                .clip(RoundedCornerShape(Dimension.Radius.medium))
                .background(MaterialTheme.colorScheme.surface)
        )

        if (location.gatheringPoints?.isEmpty() == false) {
            ButtonPage(
                title = stringResource(R.string.location_gathering_spots),
                onButtonClick = { onChangePage(LocationDetailPage.LOCATION_GATHERING) },
                modifier = Modifier.padding(horizontal = Dimension.Padding.medium)
            )
        }

        if (location.quests?.isEmpty() == false) {
            ButtonPage(
                title = stringResource(R.string.location_quest),
                onButtonClick = { onChangePage(LocationDetailPage.LOCATION_QUEST) },
                modifier = Modifier.padding(horizontal = Dimension.Padding.medium)
            )
        }
    }
}

@Composable
fun LocationMap(
    locationId: Int,
    modifier: Modifier = Modifier
) {
    var showMapDialog by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable { showMapDialog = true }
    ) {
        Image(
            painter = painterResource(
                id = MHFUIcons.location_maps[locationId] ?: R.drawable.ic_ui_unknown
            ),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimension.Padding.large)
        )
    }

    if (showMapDialog) {
        LocationMapDialog(
            locationId = locationId,
            onDismiss = { showMapDialog = false }
        )
    }
}

@DevicePreviews
@Composable
fun LocationSummaryContentPreview() {
    Theme {
        LocationSummaryContent(
            location = PreviewLocationData.location,
        )
    }
}

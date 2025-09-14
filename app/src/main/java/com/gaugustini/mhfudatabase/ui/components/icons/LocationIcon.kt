package com.gaugustini.mhfudatabase.ui.components.icons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.util.MHFUIcons

@Composable
fun LocationIcon(
    locationId: Int,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        BackgroundDecorator(
            modifier = Modifier.fillMaxSize()
        )
        Image(
            painter = painterResource(
                id = MHFUIcons.locations[locationId] ?: R.drawable.ic_ui_unknown
            ),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(0.8f)
        )
    }
}

@Preview
@Composable
fun LocationIconPreview() {
    LocationIcon(locationId = 1)
}

package com.gaugustini.mhfudatabase.ui.components.icons

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.data.enums.StatusType
import com.gaugustini.mhfudatabase.util.MHFUIcons

@Composable
fun StatusIcon(
    status: StatusType,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(
            id = MHFUIcons.status[status] ?: R.drawable.ic_ui_unknown
        ),
        contentDescription = null,
        modifier = modifier
    )
}

@Preview
@Composable
fun StatusIconPreview() {
    StatusIcon(StatusType.PARALYSIS)
}

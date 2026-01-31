package com.gaugustini.mhfudatabase.ui.components.icons

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.MonsterAilment
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.MHFUIcons

@Composable
fun StatusIcon(
    status: MonsterAilment,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(
            id = MHFUIcons.ailments[status] ?: R.drawable.ic_ui_unknown
        ),
        contentDescription = null,
        modifier = modifier
    )
}

@DevicePreviews
@Composable
fun StatusIconPreview() {
    StatusIcon(MonsterAilment.PARALYSIS)
}

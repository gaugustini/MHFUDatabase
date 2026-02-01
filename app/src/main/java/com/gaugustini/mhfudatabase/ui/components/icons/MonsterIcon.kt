package com.gaugustini.mhfudatabase.ui.components.icons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.MHFUIcons

@Composable
fun MonsterIcon(
    monsterId: Int,
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
                id = MHFUIcons.monsters[monsterId] ?: R.drawable.ic_ui_unknown
            ),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(0.8f)
        )
    }
}

@DevicePreviews
@Composable
fun MonsterIconPreview() {
    MonsterIcon(monsterId = 1)
}

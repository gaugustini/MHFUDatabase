package com.gaugustini.mhfudatabase.ui.components.icons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.MHFUColors

@Composable
fun ArmorSetIcon(
    rarity: Int,
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
            painter = painterResource(id = R.drawable.ic_armor_set),
            contentDescription = null,
            colorFilter = ColorFilter.tint(
                color = MHFUColors.getArmorSetColor(rarity),
                blendMode = BlendMode.Modulate
            ),
            modifier = Modifier.fillMaxSize()
        )
    }
}

@DevicePreviews
@Composable
fun ArmorSetIconPreview() {
    ArmorSetIcon(10)
}

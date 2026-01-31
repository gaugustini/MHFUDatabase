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
import com.gaugustini.mhfudatabase.domain.enums.EquipmentType
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.MHFUColors
import com.gaugustini.mhfudatabase.util.MHFUIcons

@Composable
fun ArmorIcon(
    type: EquipmentType,
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
            painter = painterResource(
                id = MHFUIcons.equipments[type] ?: R.drawable.ic_ui_unknown
            ),
            contentDescription = null,
            colorFilter = ColorFilter.tint(
                color = MHFUColors.getArmorColor(rarity),
                blendMode = BlendMode.Modulate
            ),
            modifier = Modifier.fillMaxSize(0.8f)
        )
    }
}

@DevicePreviews
@Composable
fun ArmorIconPreview() {
    ArmorIcon(
        type = EquipmentType.ARMOR_HEAD,
        rarity = 10
    )
}

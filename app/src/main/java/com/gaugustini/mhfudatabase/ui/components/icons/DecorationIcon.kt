package com.gaugustini.mhfudatabase.ui.components.icons

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gaugustini.mhfudatabase.domain.enums.ItemIconColor
import com.gaugustini.mhfudatabase.domain.enums.ItemIconType
import com.gaugustini.mhfudatabase.util.DevicePreviews

@Composable
fun DecorationIcon(
    color: ItemIconColor,
    modifier: Modifier = Modifier,
) {
    ItemIcon(
        type = ItemIconType.JEWEL,
        color = color,
        modifier = modifier
    )
}

@DevicePreviews
@Composable
fun DecorationIconPreview() {
    DecorationIcon(
        color = ItemIconColor.BLUE,
    )
}

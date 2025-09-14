package com.gaugustini.mhfudatabase.ui.components.icons

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.data.enums.ItemIconColor
import com.gaugustini.mhfudatabase.data.enums.ItemIconType

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

@Preview
@Composable
fun DecorationIconPreview() {
    DecorationIcon(
        color = ItemIconColor.BLUE,
    )
}

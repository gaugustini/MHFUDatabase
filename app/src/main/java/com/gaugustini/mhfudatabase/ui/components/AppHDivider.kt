package com.gaugustini.mhfudatabase.ui.components

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews

@Composable
fun AppHDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = 2.dp,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
) {
    HorizontalDivider(
        thickness = thickness,
        color = backgroundColor,
        modifier = modifier
    )
}

@DevicePreviews
@Composable
fun AppHDividerPreview() {
    Theme {
        AppHDivider()
    }
}

package com.gaugustini.mhfudatabase.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews

@Composable
fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    titleColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    backgroundColor: Color = MaterialTheme.colorScheme.secondaryContainer,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(Dimension.Padding.large)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = titleColor,
        )
    }
}

@DevicePreviews
@Composable
fun SectionHeaderPreview() {
    Theme {
        SectionHeader("Title")
    }
}

package com.gaugustini.mhfudatabase.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews

@Composable
fun ButtonPage(
    title: String,
    modifier: Modifier = Modifier,
    contentColor: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    onButtonClick: () -> Unit = {}
) {
    val shape = RoundedCornerShape(Dimension.Radius.medium)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(2.dp, contentColor, shape)
            .background(backgroundColor, shape)
            .clip(shape)
            .clickable { onButtonClick() }
            .padding(Dimension.Padding.large)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = contentColor,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(Dimension.Size.extraSmall)
        )
    }
}

@DevicePreviews
@Composable
fun ButtonPagePreview() {
    Theme {
        ButtonPage("Title")
    }
}

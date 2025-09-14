package com.gaugustini.mhfudatabase.ui.components.icons

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gaugustini.mhfudatabase.ui.theme.Theme

@Composable
fun BackgroundDecorator(
    modifier: Modifier = Modifier,
) {
    val colors = MaterialTheme.colorScheme

    Box(
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        colors.surfaceContainerLow,
                        colors.surfaceContainerHighest
                    )
                ),
                shape = RoundedCornerShape(4.dp)
            )
            .border(
                width = 1.dp,
                color = colors.outlineVariant,
                shape = RoundedCornerShape(4.dp)
            )
            .aspectRatio(1f)
    )
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BackgroundDecoratorPreview() {
    Theme {
        BackgroundDecorator()
    }
}

package com.gaugustini.mhfudatabase.ui.components.icons

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.gaugustini.mhfudatabase.util.DevicePreviews

@Composable
fun SlotsIcon(
    numberOfSlots: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(3) { index ->
            if (index < numberOfSlots) {
                SlotIcon(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                )
            } else {
                NoSlotIcon(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                )
            }
        }
    }
}

@Composable
fun SlotIcon(
    modifier: Modifier = Modifier,
    filled: Boolean = false,
    color: Color = MaterialTheme.colorScheme.onSurface,
) {
    Canvas(
        modifier = modifier
    ) {
        val radius = (size.minDimension / 2f) - 3.dp.toPx()

        if (filled) {
            drawCircle(
                color = color,
                radius = radius + 1.dp.toPx(),
            )
        } else {
            drawCircle(
                color = color,
                radius = radius,
                style = Stroke(width = 1.dp.toPx())
            )
        }
    }
}

@Composable
fun NoSlotIcon(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface,
) {
    Canvas(
        modifier = modifier
    ) {
        drawRect(
            color = color,
            topLeft = Offset(
                x = 0f + 2.dp.toPx(),
                y = size.height / 2
            ),
            size = Size(
                width = size.width - 4.dp.toPx(),
                height = 1.dp.toPx()
            ),
        )
    }
}

@DevicePreviews
@Composable
fun SlotsIconPreview() {
    SlotsIcon(2)
}

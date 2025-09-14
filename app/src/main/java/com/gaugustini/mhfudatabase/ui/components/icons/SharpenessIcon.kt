package com.gaugustini.mhfudatabase.ui.components.icons

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gaugustini.mhfudatabase.util.MHFUColors
import com.gaugustini.mhfudatabase.util.preview.PreviewWeaponData

@Composable
fun SharpnessIcon(
    sharpness: String, // TODO: Create some data instead of using a string
    height: Dp,
    width: Dp,
) {
    val levels = sharpness.split("-").map { it.toIntOrNull() ?: 0 }

    val red = levels.getOrElse(0) { 0 }.coerceAtLeast(0)
    val orange = levels.getOrElse(1) { 0 }.coerceAtLeast(0)
    val yellow = levels.getOrElse(2) { 0 }.coerceAtLeast(0)
    val green = levels.getOrElse(3) { 0 }.coerceAtLeast(0)
    val blue = levels.getOrElse(4) { 0 }.coerceAtLeast(0)
    val white = levels.getOrElse(5) { 0 }.coerceAtLeast(0)
    val purple = levels.getOrElse(6) { 0 }.coerceAtLeast(0)
    val none = (45 - (red + orange + yellow + green + blue + white + purple)).coerceAtLeast(0)

    val segments = listOf(
        MHFUColors.Sharpness.Red to red,
        MHFUColors.Sharpness.Orange to orange,
        MHFUColors.Sharpness.Yellow to yellow,
        MHFUColors.Sharpness.Green to green,
        MHFUColors.Sharpness.Blue to blue,
        MHFUColors.Sharpness.White to white,
        MHFUColors.Sharpness.Purple to purple,
        MHFUColors.Sharpness.None to none,
    )

    Canvas(
        modifier = Modifier
            .width(width)
            .height(height)
    ) {
        val total = segments.sumOf { it.second }

        if (total == 0) return@Canvas

        var currentX = 0f

        segments.forEach { (color, value) ->
            if (value > 0) {
                val segmentWidth = size.width * (value / total.toFloat())

                drawRect(
                    color = color,
                    topLeft = Offset(currentX, 0f),
                    size = Size(segmentWidth, size.height)
                )

                currentX += segmentWidth
            }
        }

        drawRect(
            color = Color.Black,
            topLeft = Offset.Zero,
            size = size,
            style = Stroke(width = 1.dp.toPx())
        )
    }
}

@Preview
@Composable
fun SharpnessIconPreview() {
    SharpnessIcon(
        sharpness = PreviewWeaponData.weaponGS.sharpnessPlus!!,
        height = 25.dp,
        width = 300.dp
    )
}

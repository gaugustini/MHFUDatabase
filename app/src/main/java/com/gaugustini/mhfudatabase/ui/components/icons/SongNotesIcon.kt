package com.gaugustini.mhfudatabase.ui.components.icons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.util.MHFUColors

@Composable
fun SongNotesIcon(
    songNotes: String,
    modifier: Modifier = Modifier,
) {
    val notes = songNotes.toCharArray()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        notes.forEach { note ->
            Image(
                painter = painterResource(R.drawable.ic_ui_song_note),
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                    color = when (note) {
                        'A' -> MHFUColors.SongNotes.A
                        'B' -> MHFUColors.SongNotes.B
                        'G' -> MHFUColors.SongNotes.G
                        'P' -> MHFUColors.SongNotes.P
                        'R' -> MHFUColors.SongNotes.R
                        'W' -> MHFUColors.SongNotes.W
                        'Y' -> MHFUColors.SongNotes.Y
                        else -> Color.Transparent
                    },
                    blendMode = BlendMode.Modulate
                ),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview
@Composable
fun SongNotesIconPreview() {
    SongNotesIcon("PGR")
}

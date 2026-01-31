package com.gaugustini.mhfudatabase.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.gaugustini.mhfudatabase.ui.components.icons.ArmorSetIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews

@Composable
fun DetailHeader(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    description: String? = null,
    icon: @Composable (() -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimension.Padding.large)
        ) {
            if (icon != null) {
                Box(
                    modifier = Modifier.size(Dimension.Size.extraLarge)
                ) {
                    icon()
                }
                Spacer(modifier = Modifier.width(Dimension.Spacing.large))
            }
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                if (subtitle != null) {
                    Spacer(modifier = Modifier.height(Dimension.Spacing.small))
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        }
        if (description != null) {
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(
                    start = Dimension.Padding.large,
                    end = Dimension.Padding.large,
                    bottom = Dimension.Padding.large,
                )
            )
        }
        HorizontalDivider()
    }
}

@DevicePreviews
@Composable
fun DetailHeaderPreview() {
    Theme {
        DetailHeader(
            icon = { ArmorSetIcon(10) },
            title = "Title",
            subtitle = "Subtitle",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
                    "Duis ultrices ornare nisl, quis cursus augue facilisis non.",
        )
    }
}

@DevicePreviews
@Composable
fun DetailHeaderOnlyIconAndTitlePreview() {
    Theme {
        DetailHeader(
            icon = { ArmorSetIcon(10) },
            title = "Title",
        )
    }
}

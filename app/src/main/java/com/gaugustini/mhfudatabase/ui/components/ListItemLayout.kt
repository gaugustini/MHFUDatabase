package com.gaugustini.mhfudatabase.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.domain.enums.WeaponType
import com.gaugustini.mhfudatabase.ui.components.icons.WeaponIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme

@Composable
fun ListItemLayout(
    headlineContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    supportingContent: @Composable (() -> Unit)? = null,
    leadingContent: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues(Dimension.Spacing.large),
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(contentPadding)
    ) {
        if (leadingContent != null) {
            leadingContent()
            Spacer(modifier = Modifier.width(Dimension.Spacing.large))
        }
        Column(
            modifier = Modifier.weight(1f)
        ) {
            headlineContent()
            if (supportingContent != null) {
                Spacer(modifier = Modifier.height(Dimension.Spacing.small))
                supportingContent()
            }
        }
        if (trailingContent != null) {
            Spacer(modifier = Modifier.width(Dimension.Spacing.large))
            trailingContent()
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ListItemLayoutPreview() {
    Theme {
        ListItemLayout(
            leadingContent = {
                WeaponIcon(
                    type = WeaponType.GREAT_SWORD,
                    rarity = 1,
                    modifier = Modifier.size(Dimension.Size.large)
                )
            },
            headlineContent = {
                Text(
                    text = "Headline",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            },
            supportingContent = {
                Text(
                    text = "Supporting content",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,

                    )
            },
            trailingContent = {
                Text(
                    text = "Trailing content",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            },
        )
    }
}

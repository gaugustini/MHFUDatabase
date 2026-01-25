package com.gaugustini.mhfudatabase.ui.features.decoration.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.ItemIconColor
import com.gaugustini.mhfudatabase.domain.model.Decoration
import com.gaugustini.mhfudatabase.ui.components.icons.SlotIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.LocalIsDarkTheme
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.MHFUColors
import com.gaugustini.mhfudatabase.util.preview.PreviewDecorationData

@Composable
fun DecorationSummary(
    decoration: Decoration,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(Dimension.Padding.large)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(Dimension.Padding.small),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                repeat(decoration.requiredSlots) {
                    SlotIcon(
                        filled = true,
                        color = if (!LocalIsDarkTheme.current && decoration.color == ItemIconColor.WHITE) {
                            Color.Gray
                        } else {
                            MHFUColors.getItemColor(decoration.color)
                        },
                        modifier = Modifier.size(Dimension.Size.extraSmall)
                    )
                }
            }
            Text(
                text = stringResource(R.string.decoration_required_slots, decoration.requiredSlots),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(Dimension.Padding.small),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "${decoration.buyPrice}z",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = stringResource(R.string.decoration_buy_price),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(Dimension.Padding.small),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "${decoration.sellPrice}z",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = stringResource(R.string.decoration_sell_price),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DecorationSummaryPreview() {
    Theme {
        DecorationSummary(PreviewDecorationData.decoration)
    }
}

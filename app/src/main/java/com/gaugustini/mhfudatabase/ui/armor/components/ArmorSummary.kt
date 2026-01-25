package com.gaugustini.mhfudatabase.ui.armor.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.ItemIconColor
import com.gaugustini.mhfudatabase.domain.enums.WeaponElement
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.icons.ElementIcon
import com.gaugustini.mhfudatabase.ui.components.icons.SlotsIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.MHFUColors

@Composable
fun ArmorSummary(
    defense: Int,
    numberOfSlots: Int?,
    fire: Int,
    water: Int,
    thunder: Int,
    ice: Int,
    dragon: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        ListItemLayout(
            leadingContent = {
                Image(
                    painter = painterResource(id = R.drawable.ic_ui_defense),
                    contentDescription = null,
                    modifier = Modifier.size(Dimension.Size.extraSmall)
                )
            },
            headlineContent = {
                Text(
                    text = stringResource(R.string.armor_defense),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            },
            trailingContent = {
                Text(
                    text = defense.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            },
            contentPadding = PaddingValues(
                horizontal = Dimension.Spacing.large,
                vertical = Dimension.Spacing.medium
            ),
        )
        HorizontalDivider()
        if (numberOfSlots != null) {
            ListItemLayout(
                leadingContent = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_ui_slots),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(
                            color = MHFUColors.getItemColor(ItemIconColor.BLUE),
                            blendMode = BlendMode.Modulate
                        ),
                        modifier = Modifier.size(Dimension.Size.extraSmall)
                    )
                },
                headlineContent = {
                    Text(
                        text = stringResource(R.string.armor_number_of_slots),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                },
                trailingContent = {
                    SlotsIcon(
                        numberOfSlots = numberOfSlots,
                        modifier = Modifier.size(
                            height = Dimension.Size.tiny,
                            width = Dimension.Size.tiny * 3,
                        )
                    )
                },
                contentPadding = PaddingValues(
                    horizontal = Dimension.Spacing.large,
                    vertical = Dimension.Spacing.medium
                ),
            )
            HorizontalDivider()
        }
        ListItemLayout(
            leadingContent = {
                ElementIcon(
                    element = WeaponElement.FIRE,
                    modifier = Modifier.size(Dimension.Size.extraSmall)
                )
            },
            headlineContent = {
                Text(
                    text = stringResource(R.string.armor_fire_resistance),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            },
            trailingContent = {
                Text(
                    text = fire.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            },
            contentPadding = PaddingValues(
                horizontal = Dimension.Spacing.large,
                vertical = Dimension.Spacing.medium
            ),
        )
        HorizontalDivider()
        ListItemLayout(
            leadingContent = {
                ElementIcon(
                    element = WeaponElement.WATER,
                    modifier = Modifier.size(Dimension.Size.extraSmall)
                )
            },
            headlineContent = {
                Text(
                    text = stringResource(R.string.armor_water_resistance),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            },
            trailingContent = {
                Text(
                    text = water.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            },
            contentPadding = PaddingValues(
                horizontal = Dimension.Spacing.large,
                vertical = Dimension.Spacing.medium
            ),
        )
        HorizontalDivider()
        ListItemLayout(
            leadingContent = {
                ElementIcon(
                    element = WeaponElement.THUNDER,
                    modifier = Modifier.size(Dimension.Size.extraSmall)
                )
            },
            headlineContent = {
                Text(
                    text = stringResource(R.string.armor_thunder_resistance),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            },
            trailingContent = {
                Text(
                    text = thunder.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            },
            contentPadding = PaddingValues(
                horizontal = Dimension.Spacing.large,
                vertical = Dimension.Spacing.medium
            ),
        )
        HorizontalDivider()
        ListItemLayout(
            leadingContent = {
                ElementIcon(
                    element = WeaponElement.ICE,
                    modifier = Modifier.size(Dimension.Size.extraSmall)
                )
            },
            headlineContent = {
                Text(
                    text = stringResource(R.string.armor_ice_resistance),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            },
            trailingContent = {
                Text(
                    text = ice.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            },
            contentPadding = PaddingValues(
                horizontal = Dimension.Spacing.large,
                vertical = Dimension.Spacing.medium
            ),
        )
        HorizontalDivider()
        ListItemLayout(
            leadingContent = {
                ElementIcon(
                    element = WeaponElement.DRAGON,
                    modifier = Modifier.size(Dimension.Size.extraSmall)
                )
            },
            headlineContent = {
                Text(
                    text = stringResource(R.string.armor_dragon_resistance),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            },
            trailingContent = {
                Text(
                    text = dragon.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            },
            contentPadding = PaddingValues(
                horizontal = Dimension.Spacing.large,
                vertical = Dimension.Spacing.medium
            ),
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ArmorSummaryPreview() {
    Theme {
        ArmorSummary(
            defense = 100,
            numberOfSlots = 3,
            fire = 10,
            water = 10,
            thunder = 10,
            ice = 10,
            dragon = 10,
        )
    }
}

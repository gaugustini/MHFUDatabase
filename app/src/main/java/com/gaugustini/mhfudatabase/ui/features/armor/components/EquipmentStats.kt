package com.gaugustini.mhfudatabase.ui.features.armor.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.ItemIconColor
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.icons.SlotsIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.ForEachWithDivider
import com.gaugustini.mhfudatabase.util.MHFUColors

private data class Stats(
    @get:StringRes val name: Int,
    val value: Int,
    @get:DrawableRes val icon: Int,
)

@Composable
fun EquipmentStats(
    numberOfSlots: Int?,
    defense: Int,
    fire: Int,
    water: Int,
    thunder: Int,
    ice: Int,
    dragon: Int,
    modifier: Modifier = Modifier,
) {
    val stats = listOf(
        Stats(R.string.armor_defense, defense, R.drawable.ic_ui_defense),
        Stats(R.string.armor_fire_resistance, fire, R.drawable.ic_element_fire),
        Stats(R.string.armor_water_resistance, water, R.drawable.ic_element_water),
        Stats(R.string.armor_thunder_resistance, thunder, R.drawable.ic_element_thunder),
        Stats(R.string.armor_ice_resistance, ice, R.drawable.ic_element_ice),
        Stats(R.string.armor_dragon_resistance, dragon, R.drawable.ic_element_dragon),
    )

    Column(
        modifier = modifier
    ) {
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

        stats.ForEachWithDivider { stat ->
            ListItemLayout(
                leadingContent = {
                    Image(
                        painter = painterResource(stat.icon),
                        contentDescription = null,
                        modifier = Modifier.size(Dimension.Size.extraSmall)
                    )
                },
                headlineContent = {
                    Text(
                        text = stringResource(stat.name),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                },
                trailingContent = {
                    Text(
                        text = stat.value.toString(),
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
}

@DevicePreviews
@Composable
fun EquipmentStatsPreview() {
    Theme {
        EquipmentStats(
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

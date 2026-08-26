package com.gaugustini.mhfudatabase.ui.features.monster.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.WeaponElement
import com.gaugustini.mhfudatabase.domain.model.MonsterDamageStats
import com.gaugustini.mhfudatabase.ui.components.icons.ElementIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.ForEachWithDivider
import com.gaugustini.mhfudatabase.util.preview.PreviewMonsterData
import kotlinx.coroutines.launch

@Composable
fun MonsterDamageElemental(
    damage: List<MonsterDamageStats>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        DamageElementalHeader()

        damage.ForEachWithDivider { hitzone ->
            DamageElementalListItem(
                hitzoneName = hitzone.name,
                fireDamage = hitzone.fire,
                waterDamage = hitzone.water,
                thunderDamage = hitzone.thunder,
                iceDamage = hitzone.ice,
                dragonDamage = hitzone.dragon,
            )
        }
    }
}

@Composable
fun DamageElementalHeader(
    modifier: Modifier = Modifier,
) {
    val scope = androidx.compose.runtime.rememberCoroutineScope()
    val elements = listOf(
        WeaponElement.FIRE to R.string.monster_fire_damage,
        WeaponElement.WATER to R.string.monster_water_damage,
        WeaponElement.THUNDER to R.string.monster_thunder_damage,
        WeaponElement.ICE to R.string.monster_ice_damage,
        WeaponElement.DRAGON to R.string.monster_dragon_damage
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
            .padding(Dimension.Padding.large)
    ) {
        Text(
            text = stringResource(R.string.monster_elemental_damage),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier.weight(5f)
        )

        elements.forEach { (element, stringRes) ->
            val tooltipState = rememberTooltipState()

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .clickable(
                        interactionSource = null,
                        indication = null
                    ) {
                        scope.launch { tooltipState.show() }
                    }
            ) {
                TooltipBox(
                    positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
                        TooltipAnchorPosition.Above
                    ),
                    tooltip = {
                        PlainTooltip {
                            Text(stringResource(stringRes))
                        }
                    },
                    state = tooltipState
                ) {
                    ElementIcon(
                        element = element,
                        modifier = Modifier.size(Dimension.Size.extraSmall)
                    )
                }
            }
        }
    }
}

@Composable
fun DamageElementalListItem(
    hitzoneName: String,
    fireDamage: Int,
    waterDamage: Int,
    thunderDamage: Int,
    iceDamage: Int,
    dragonDamage: Int,
    modifier: Modifier = Modifier,
) {
    val values = listOf(
        fireDamage,
        waterDamage,
        thunderDamage,
        iceDamage,
        dragonDamage
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(
                horizontal = Dimension.Padding.large,
                vertical = Dimension.Padding.medium
            )
    ) {
        Text(
            text = hitzoneName,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(5f)
        )

        values.forEach { value ->
            Text(
                text = if (value == 0) "-" else value.toString(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@DevicePreviews
@Composable
fun MonsterDamageElementalPreview() {
    Theme {
        MonsterDamageElemental(
            damage = PreviewMonsterData.monsterDamageStatsList,
        )
    }
}

package com.gaugustini.mhfudatabase.ui.features.monster.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.WeaponElement
import com.gaugustini.mhfudatabase.domain.model.MonsterDamageStats
import com.gaugustini.mhfudatabase.ui.components.icons.ElementIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewMonsterData

@Composable
fun MonsterDamageElemental(
    damage: List<MonsterDamageStats>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        DamageElementalHeader()

        damage.forEach { hitzone ->
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
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.weight(1f)
        ) {
            ElementIcon(
                element = WeaponElement.FIRE,
                modifier = Modifier.size(Dimension.Size.extraSmall)
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.weight(1f)
        ) {
            ElementIcon(
                element = WeaponElement.WATER,
                modifier = Modifier.size(Dimension.Size.extraSmall)
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.weight(1f)
        ) {
            ElementIcon(
                element = WeaponElement.THUNDER,
                modifier = Modifier.size(Dimension.Size.extraSmall)
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.weight(1f)
        ) {
            ElementIcon(
                element = WeaponElement.ICE,
                modifier = Modifier.size(Dimension.Size.extraSmall)
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.weight(1f)
        ) {
            ElementIcon(
                element = WeaponElement.DRAGON,
                modifier = Modifier.size(Dimension.Size.extraSmall)
            )
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
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(
                horizontal = Dimension.Padding.large,
                vertical = Dimension.Padding.small
            )
    ) {
        Text(
            text = hitzoneName,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(5f)
        )
        Text(
            text = if (fireDamage == 0) "-" else fireDamage.toString(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = if (waterDamage == 0) "-" else waterDamage.toString(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = if (thunderDamage == 0) "-" else thunderDamage.toString(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = if (iceDamage == 0) "-" else iceDamage.toString(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = if (dragonDamage == 0) "-" else dragonDamage.toString(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
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

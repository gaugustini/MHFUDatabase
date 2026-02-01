package com.gaugustini.mhfudatabase.ui.features.monster.components

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.model.MonsterDamageStats
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewMonsterData

@Composable
fun MonsterDamagePhysical(
    damage: List<MonsterDamageStats>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        DamagePhysicalHeader()

        damage.forEach { hitzone ->
            DamagePhysicalListItem(
                hitzoneName = hitzone.name,
                cutDamage = hitzone.cut,
                impactDamage = hitzone.impact,
                shotDamage = hitzone.shot,
            )
        }
    }
}

@Composable
fun DamagePhysicalHeader(
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
            text = stringResource(R.string.monster_physical_damage),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier.weight(5f)
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.weight(1f)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_weapon_great_sword),
                contentDescription = null,
                modifier = Modifier.size(Dimension.Size.extraSmall)
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.weight(1f)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_weapon_hammer),
                contentDescription = null,
                modifier = Modifier.size(Dimension.Size.extraSmall)
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.weight(1f)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_item_shell),
                contentDescription = null,
                modifier = Modifier.size(Dimension.Size.extraSmall)
            )
        }
    }
}

@Composable
fun DamagePhysicalListItem(
    hitzoneName: String,
    cutDamage: Int,
    impactDamage: Int,
    shotDamage: Int,
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
            text = cutDamage.toString(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = impactDamage.toString(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = shotDamage.toString(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
    }
}

@DevicePreviews
@Composable
fun MonsterDamagePhysicalPreview() {
    Theme {
        MonsterDamagePhysical(
            damage = PreviewMonsterData.monsterDamageStatsList,
        )
    }
}

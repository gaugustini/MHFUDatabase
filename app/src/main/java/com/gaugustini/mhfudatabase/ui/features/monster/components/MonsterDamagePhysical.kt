package com.gaugustini.mhfudatabase.ui.features.monster.components

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.model.MonsterDamageStats
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.ForEachWithDivider
import com.gaugustini.mhfudatabase.util.preview.PreviewMonsterData
import kotlinx.coroutines.launch

@Composable
fun MonsterDamagePhysical(
    damage: List<MonsterDamageStats>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        DamagePhysicalHeader()

        damage.ForEachWithDivider { hitzone ->
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
    val scope = androidx.compose.runtime.rememberCoroutineScope()
    val damageTypes = listOf(
        R.drawable.ic_weapon_great_sword to R.string.monster_cut_damage,
        R.drawable.ic_weapon_hammer to R.string.monster_impact_damage,
        R.drawable.ic_item_shell to R.string.monster_shot_damage
    )

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

        damageTypes.forEach { (iconRes, stringRes) ->
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
                    Image(
                        painter = painterResource(iconRes),
                        contentDescription = null,
                        modifier = Modifier.size(Dimension.Size.extraSmall)
                    )
                }
            }
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
    val values = listOf(
        cutDamage,
        impactDamage,
        shotDamage
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
                text = value.toString(),
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
fun MonsterDamagePhysicalPreview() {
    Theme {
        MonsterDamagePhysical(
            damage = PreviewMonsterData.monsterDamageStatsList,
        )
    }
}

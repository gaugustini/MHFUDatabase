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
import androidx.compose.ui.text.style.TextOverflow
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.model.MonsterAilmentStats
import com.gaugustini.mhfudatabase.ui.components.icons.StatusIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.ForEachWithDivider
import com.gaugustini.mhfudatabase.util.preview.PreviewMonsterData
import kotlinx.coroutines.launch

@Composable
fun MonsterAilments(
    ailments: List<MonsterAilmentStats>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        AilmentStatusHeader()

        ailments.ForEachWithDivider { ailment ->
            AilmentStatusListItem(
                ailment = ailment,
            )
        }
    }
}

@Composable
fun AilmentStatusHeader(
    modifier: Modifier = Modifier,
) {
    val scope = androidx.compose.runtime.rememberCoroutineScope()
    val headers = listOf(
        R.string.monster_ailment_status,
        R.string.monster_ailment_initial,
        R.string.monster_ailment_increase,
        R.string.monster_ailment_max,
        R.string.monster_ailment_duration,
        R.string.monster_ailment_damage
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
            .padding(Dimension.Padding.large)
    ) {
        headers.forEach { header ->
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
                    },
            ) {
                TooltipBox(
                    positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
                        TooltipAnchorPosition.Above
                    ),
                    tooltip = {
                        PlainTooltip {
                            Text(stringResource(header))
                        }
                    },
                    state = tooltipState
                ) {
                    Text(
                        text = stringResource(header),
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                    )
                }
            }
        }
    }
}

@Composable
fun AilmentStatusListItem(
    ailment: MonsterAilmentStats,
    modifier: Modifier = Modifier,
) {
    val values = listOf(
        ailment.initial.toString(),
        ailment.increase.toString(),
        ailment.max.toString(),
        "${ailment.duration}s",
        if (ailment.damage == 0) "-" else ailment.damage.toString(),
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
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.weight(1f)
        ) {
            StatusIcon(
                status = ailment.type,
                modifier = Modifier.size(Dimension.Size.extraSmall)
            )
        }

        values.forEach { value ->
            Text(
                text = value,
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
fun MonsterAilmentsPreview() {
    Theme {
        MonsterAilments(
            ailments = PreviewMonsterData.monsterAilmentStatsList,
        )
    }
}

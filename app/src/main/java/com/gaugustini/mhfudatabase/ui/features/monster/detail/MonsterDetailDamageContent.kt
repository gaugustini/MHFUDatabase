package com.gaugustini.mhfudatabase.ui.features.monster.detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.gaugustini.mhfudatabase.domain.model.MonsterAilmentStats
import com.gaugustini.mhfudatabase.domain.model.MonsterDamageStats
import com.gaugustini.mhfudatabase.ui.features.monster.components.MonsterAilments
import com.gaugustini.mhfudatabase.ui.features.monster.components.MonsterDamageElemental
import com.gaugustini.mhfudatabase.ui.features.monster.components.MonsterDamagePhysical
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewMonsterData

@Composable
fun MonsterDetailDamageContent(
    damage: List<MonsterDamageStats>,
    ailments: List<MonsterAilmentStats>,
    modifier: Modifier = Modifier,
    onChangePage: (MonsterDetailPage) -> Unit = {},
) {
    BackHandler {
        onChangePage(MonsterDetailPage.SUMMARY)
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(Dimension.Padding.medium),
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = Dimension.Padding.endContent)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = Dimension.Padding.medium)
                .clip(RoundedCornerShape(Dimension.Radius.medium))
                .background(MaterialTheme.colorScheme.surface)
        ) {
            MonsterDamagePhysical(
                damage = damage,
            )
        }

        Column(
            modifier = Modifier
                .padding(horizontal = Dimension.Padding.medium)
                .clip(RoundedCornerShape(Dimension.Radius.medium))
                .background(MaterialTheme.colorScheme.surface)
        ) {
            MonsterDamageElemental(
                damage = damage,
            )
        }

        if (ailments.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .padding(horizontal = Dimension.Padding.medium)
                    .clip(RoundedCornerShape(Dimension.Radius.medium))
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                MonsterAilments(
                    ailments = ailments,
                )
            }
        }
    }
}

@DevicePreviews
@Composable
fun MonsterDetailDamageContentPreview() {
    Theme {
        MonsterDetailDamageContent(
            damage = PreviewMonsterData.monsterDamageStatsList,
            ailments = PreviewMonsterData.monsterAilmentStatsList,
        )
    }
}

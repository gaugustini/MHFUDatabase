package com.gaugustini.mhfudatabase.ui.features.monster.detail

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
import androidx.compose.ui.res.stringResource
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.model.Monster
import com.gaugustini.mhfudatabase.ui.components.ButtonPage
import com.gaugustini.mhfudatabase.ui.components.DetailHeader
import com.gaugustini.mhfudatabase.ui.components.icons.MonsterIcon
import com.gaugustini.mhfudatabase.ui.features.monster.components.MonsterItemEffectivenessSummary
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewMonsterData

@Composable
fun MonsterDetailSummaryContent(
    monster: Monster,
    modifier: Modifier = Modifier,
    onChangePage: (MonsterDetailPage) -> Unit = {},
) {
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
            DetailHeader(
                icon = {
                    MonsterIcon(
                        monsterId = monster.id,
                    )
                },
                title = monster.name,
                subtitle = monster.ecology,
                description = monster.description,
            )
        }

        if (monster.damageStats?.isEmpty() == false) {
            ButtonPage(
                title = stringResource(R.string.monster_damage_stats),
                onButtonClick = { onChangePage(MonsterDetailPage.DAMAGE) },
                modifier = Modifier.padding(horizontal = Dimension.Padding.medium)
            )
        }

        if (monster.rewards?.isEmpty() == false) {
            ButtonPage(
                title = stringResource(R.string.monster_reward),
                onButtonClick = { onChangePage(MonsterDetailPage.REWARD) },
                modifier = Modifier.padding(horizontal = Dimension.Padding.medium)
            )
        }

        if (monster.quests?.isEmpty() == false) {
            ButtonPage(
                title = stringResource(R.string.monster_quest),
                onButtonClick = { onChangePage(MonsterDetailPage.QUEST) },
                modifier = Modifier.padding(horizontal = Dimension.Padding.medium)
            )
        }

        monster.itemEffectiveness?.let { item ->
            MonsterItemEffectivenessSummary(
                item = item,
                modifier = Modifier
                    .padding(horizontal = Dimension.Padding.medium)
                    .clip(RoundedCornerShape(Dimension.Radius.medium))
                    .background(MaterialTheme.colorScheme.surface)
            )
        }
    }
}

@DevicePreviews
@Composable
fun MonsterDetailSummaryContentPreview() {
    Theme {
        MonsterDetailSummaryContent(
            monster = PreviewMonsterData.monster,
        )
    }
}

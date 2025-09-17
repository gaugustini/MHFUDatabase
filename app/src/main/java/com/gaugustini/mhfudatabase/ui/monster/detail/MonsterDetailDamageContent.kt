package com.gaugustini.mhfudatabase.ui.monster.detail

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.data.model.AilmentStatus
import com.gaugustini.mhfudatabase.data.model.Hitzone
import com.gaugustini.mhfudatabase.ui.monster.components.MonsterAilments
import com.gaugustini.mhfudatabase.ui.monster.components.MonsterDamageElemental
import com.gaugustini.mhfudatabase.ui.monster.components.MonsterDamagePhysical
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewMonsterData

@Composable
fun MonsterDetailDamageContent(
    damage: List<Hitzone>,
    ailments: List<AilmentStatus>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(bottom = Dimension.Padding.endContent)
    ) {
        MonsterDamagePhysical(
            damage = damage,
            modifier = Modifier.padding(bottom = Dimension.Padding.large)
        )

        MonsterDamageElemental(
            damage = damage,
            modifier = Modifier.padding(bottom = Dimension.Padding.large)
        )

        if (ailments.isNotEmpty()) {
            MonsterAilments(
                ailments = ailments,
                modifier = Modifier.padding(bottom = Dimension.Padding.large)
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MonsterDetailDamageContentPreview() {
    Theme {
        MonsterDetailDamageContent(
            damage = PreviewMonsterData.monsterHitzoneList,
            ailments = PreviewMonsterData.monsterAilmentStatusList,
        )
    }
}

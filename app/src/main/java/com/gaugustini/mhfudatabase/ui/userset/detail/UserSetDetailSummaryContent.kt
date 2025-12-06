package com.gaugustini.mhfudatabase.ui.userset.detail

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.data.model.Armor
import com.gaugustini.mhfudatabase.data.model.UserEquipmentSet
import com.gaugustini.mhfudatabase.data.model.Weapon
import com.gaugustini.mhfudatabase.ui.armor.components.ArmorSummary
import com.gaugustini.mhfudatabase.ui.components.DetailHeader
import com.gaugustini.mhfudatabase.ui.components.SectionHeader
import com.gaugustini.mhfudatabase.ui.components.icons.ArmorSetIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewArmorData
import com.gaugustini.mhfudatabase.util.preview.PreviewUserEquipmentSet
import com.gaugustini.mhfudatabase.util.preview.PreviewWeaponData

@Composable
fun UserSetDetailSummaryContent(
    set: UserEquipmentSet?,
    weapon: Weapon?,
    armors: List<Armor>,
    modifier: Modifier = Modifier,
    onItemClick: (itemId: Int) -> Unit = {},
    onSkillClick: (skillTreeId: Int) -> Unit = {},
    onSkillTreeClick: (skillTreeId: Int) -> Unit = {},
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(bottom = Dimension.Padding.endContent)
    ) {
        DetailHeader(
            icon = {
                ArmorSetIcon(
                    rarity = 0,
                )
            },
            title = set?.name ?: stringResource(R.string.user_set_new),
        )

        ArmorSummary(
            defense = armors.sumOf { it.defense } + (weapon?.defense ?: 0),
            numberOfSlots = null,
            fire = armors.sumOf { it.fire },
            water = armors.sumOf { it.water },
            thunder = armors.sumOf { it.thunder },
            ice = armors.sumOf { it.ice },
            dragon = armors.sumOf { it.dragon },
        )

        SectionHeader(
            title = stringResource(R.string.list_active_skills),
        )
        // TODO: Add active skills
        Text(
            text = stringResource(R.string.user_set_none),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = Dimension.Spacing.large,
                    vertical = Dimension.Spacing.medium
                )
        )

        SectionHeader(
            title = stringResource(R.string.list_skills),
        )
        // TODO: Add skill tree points
        Text(
            text = stringResource(R.string.user_set_none),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = Dimension.Spacing.large,
                    vertical = Dimension.Spacing.medium
                )
        )

        SectionHeader(
            title = stringResource(R.string.list_recipe),
        )
        // TODO: Add recipe
        Text(
            text = stringResource(R.string.user_set_none),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = Dimension.Spacing.large,
                    vertical = Dimension.Spacing.medium
                )
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun UserSetDetailSummaryContentPreview() {
    Theme {
        UserSetDetailSummaryContent(
            set = PreviewUserEquipmentSet.userSet,
            weapon = PreviewWeaponData.weapon,
            armors = PreviewArmorData.armorList,
        )
    }
}

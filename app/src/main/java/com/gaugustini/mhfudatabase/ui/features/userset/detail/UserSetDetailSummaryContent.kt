package com.gaugustini.mhfudatabase.ui.features.userset.detail

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
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.model.UserEquipmentSet
import com.gaugustini.mhfudatabase.ui.components.DetailHeader
import com.gaugustini.mhfudatabase.ui.components.SectionHeader
import com.gaugustini.mhfudatabase.ui.components.icons.ArmorSetIcon
import com.gaugustini.mhfudatabase.ui.features.armor.components.EquipmentStats
import com.gaugustini.mhfudatabase.ui.features.item.components.ItemQuantityListItem
import com.gaugustini.mhfudatabase.ui.features.skill.components.SkillPointListItem
import com.gaugustini.mhfudatabase.ui.features.userset.components.ActiveSkillListItem
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.ForEachWithDivider
import com.gaugustini.mhfudatabase.util.preview.PreviewUserEquipmentSet

@Composable
fun UserSetDetailSummaryContent(
    equipmentSet: UserEquipmentSet,
    modifier: Modifier = Modifier,
    onItemClick: (itemId: Int) -> Unit = {},
    onSkillClick: (skillTreeId: Int) -> Unit = {},
) {
    val noneText = @Composable {
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
            title = equipmentSet.name,
        )

        equipmentSet.armors.let { armors ->
            EquipmentStats(
                defense = (armors?.sumOf { it.defense } ?: 0) + (equipmentSet.weapon?.defense ?: 0),
                numberOfSlots = null,
                fire = armors?.sumOf { it.fire } ?: 0,
                water = armors?.sumOf { it.water } ?: 0,
                thunder = armors?.sumOf { it.thunder } ?: 0,
                ice = armors?.sumOf { it.ice } ?: 0,
                dragon = armors?.sumOf { it.dragon } ?: 0,
            )
        }

        SectionHeader(
            title = stringResource(R.string.list_active_skills),
        )
        equipmentSet.activeSkills.let { activeSkills ->
            if (activeSkills?.isEmpty() ?: true) {
                noneText()
            } else {
                Column {
                    activeSkills.ForEachWithDivider { skill ->
                        ActiveSkillListItem(
                            skill = skill,
                            onSkillClick = onSkillClick,
                        )
                    }
                }
            }
        }

        SectionHeader(
            title = stringResource(R.string.list_skills),
        )
        equipmentSet.skills.let { skillPoints ->
            if (skillPoints?.isEmpty() ?: true) {
                noneText()
            } else {
                Column {
                    skillPoints.ForEachWithDivider { skill ->
                        SkillPointListItem(
                            skill = skill,
                            onSkillClick = onSkillClick,
                        )
                    }
                }
            }
        }

        SectionHeader(
            title = stringResource(R.string.list_recipe),
        )
        equipmentSet.recipe.let { materials ->
            if (materials?.isEmpty() ?: true) {
                noneText()
            } else {
                Column {
                    materials.ForEachWithDivider { item ->
                        ItemQuantityListItem(
                            item = item,
                            onItemClick = onItemClick,
                        )
                    }
                }
            }
        }
    }
}

@DevicePreviews
@Composable
fun UserSetDetailSummaryContentPreview() {
    Theme {
        UserSetDetailSummaryContent(
            equipmentSet = PreviewUserEquipmentSet.userSet,
        )
    }
}

package com.gaugustini.mhfudatabase.ui.features.armor.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.model.ArmorSet
import com.gaugustini.mhfudatabase.ui.components.DetailHeader
import com.gaugustini.mhfudatabase.ui.components.SectionHeader
import com.gaugustini.mhfudatabase.ui.components.icons.ArmorSetIcon
import com.gaugustini.mhfudatabase.ui.features.armor.components.ArmorListItem
import com.gaugustini.mhfudatabase.ui.features.armor.components.EquipmentStats
import com.gaugustini.mhfudatabase.ui.features.item.components.ItemQuantityListItem
import com.gaugustini.mhfudatabase.ui.features.skill.components.SkillTreePointsList
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.ForEachWithDivider
import com.gaugustini.mhfudatabase.util.preview.PreviewArmorData

@Composable
fun ArmorSetDetailContent(
    armorSet: ArmorSet,
    modifier: Modifier = Modifier,
    onArmorClick: (armorId: Int) -> Unit = {},
    onSkillClick: (skillTreeId: Int) -> Unit = {},
    onItemClick: (itemId: Int) -> Unit = {},
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(bottom = Dimension.Padding.endContent)
    ) {
        DetailHeader(
            icon = {
                ArmorSetIcon(
                    rarity = armorSet.rarity,
                )
            },
            title = armorSet.name,
            subtitle = stringResource(R.string.armor_rarity, armorSet.rarity),
        )

        EquipmentStats(
            numberOfSlots = null,
            defense = armorSet.defense,
            fire = armorSet.fire,
            water = armorSet.water,
            thunder = armorSet.thunder,
            ice = armorSet.ice,
            dragon = armorSet.dragon,
        )

        armorSet.armors?.let { armors ->
            if (armors.isNotEmpty()) {
                SectionHeader(
                    title = stringResource(R.string.list_armors),
                )
                Column {
                    armors.ForEachWithDivider { armor ->
                        ArmorListItem(
                            armor = armor,
                            onArmorClick = onArmorClick,
                        )
                    }
                }
            }
        }

        armorSet.skills?.let { skills ->
            if (skills.isNotEmpty()) {
                SectionHeader(
                    title = stringResource(R.string.list_skills),
                )
                SkillTreePointsList(
                    skills = skills,
                    onSkillClick = onSkillClick,
                )
            }
        }

        armorSet.recipe?.let { recipe ->
            if (recipe.isNotEmpty()) {
                SectionHeader(
                    title = stringResource(R.string.list_recipe),
                )
                Column {
                    recipe.ForEachWithDivider { item ->
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
fun ArmorSetDetailContentPreview() {
    Theme {
        ArmorSetDetailContent(
            armorSet = PreviewArmorData.armorSet,
        )
    }
}

package com.gaugustini.mhfudatabase.ui.features.armor.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.model.Armor
import com.gaugustini.mhfudatabase.ui.components.DetailHeader
import com.gaugustini.mhfudatabase.ui.components.SectionHeader
import com.gaugustini.mhfudatabase.ui.components.icons.ArmorIcon
import com.gaugustini.mhfudatabase.ui.features.armor.components.EquipmentStats
import com.gaugustini.mhfudatabase.ui.features.item.components.ItemQuantityListItem
import com.gaugustini.mhfudatabase.ui.features.skill.components.SkillPointListItem
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.ForEachWithDivider
import com.gaugustini.mhfudatabase.util.preview.PreviewArmorData

@Composable
fun ArmorDetailContent(
    armor: Armor,
    modifier: Modifier = Modifier,
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
                ArmorIcon(
                    type = armor.type,
                    rarity = armor.rarity,
                )
            },
            title = armor.name,
            subtitle = stringResource(R.string.armor_rarity, armor.rarity),
            description = armor.description,
        )

        EquipmentStats(
            numberOfSlots = armor.numberOfSlots,
            defense = armor.defense,
            fire = armor.fire,
            water = armor.water,
            thunder = armor.thunder,
            ice = armor.ice,
            dragon = armor.dragon,
        )

        armor.skills?.let { skills ->
            if (skills.isNotEmpty()) {
                SectionHeader(
                    title = stringResource(R.string.list_skills),
                )
                Column {
                    skills.ForEachWithDivider { skill ->
                        SkillPointListItem(
                            skill = skill,
                            onSkillClick = onSkillClick,
                        )
                    }
                }
            }
        }

        armor.recipe?.let { recipe ->
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
fun ArmorDetailContentPreview() {
    Theme {
        ArmorDetailContent(
            armor = PreviewArmorData.armor,
        )
    }
}

package com.gaugustini.mhfudatabase.ui.armor.detail

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.data.model.Armor
import com.gaugustini.mhfudatabase.data.model.ItemQuantity
import com.gaugustini.mhfudatabase.data.model.SkillTreePoints
import com.gaugustini.mhfudatabase.ui.armor.components.ArmorSummary
import com.gaugustini.mhfudatabase.ui.components.DetailHeader
import com.gaugustini.mhfudatabase.ui.components.SectionHeader
import com.gaugustini.mhfudatabase.ui.components.icons.ArmorIcon
import com.gaugustini.mhfudatabase.ui.item.components.ItemQuantityList
import com.gaugustini.mhfudatabase.ui.skill.components.SkillTreePointsList
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewArmorData
import com.gaugustini.mhfudatabase.util.preview.PreviewItemData
import com.gaugustini.mhfudatabase.util.preview.PreviewSkillData

@Composable
fun ArmorDetailContent(
    armor: Armor,
    skills: List<SkillTreePoints>,
    recipe: List<ItemQuantity>,
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

        ArmorSummary(
            defense = armor.defense,
            numberOfSlots = armor.numberOfSlots,
            fire = armor.fire,
            water = armor.water,
            thunder = armor.thunder,
            ice = armor.ice,
            dragon = armor.dragon,
        )

        if (skills.isNotEmpty()) {
            SectionHeader(
                title = stringResource(R.string.list_skills),
            )
            SkillTreePointsList(
                skills = skills,
                onSkillClick = onSkillClick,
            )
        }
        if (recipe.isNotEmpty()) {
            SectionHeader(
                title = stringResource(R.string.list_recipe),
            )
            ItemQuantityList(
                items = recipe,
                onItemClick = onItemClick,
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ArmorDetailContentPreview() {
    Theme {
        ArmorDetailContent(
            armor = PreviewArmorData.armor,
            skills = PreviewSkillData.skillTreePointsList,
            recipe = PreviewItemData.itemQuantityList,
        )
    }
}

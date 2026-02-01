package com.gaugustini.mhfudatabase.ui.features.skill.detail

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.model.Armor
import com.gaugustini.mhfudatabase.domain.model.Decoration
import com.gaugustini.mhfudatabase.ui.components.SectionHeader
import com.gaugustini.mhfudatabase.ui.features.skill.components.SkillPointArmorListItem
import com.gaugustini.mhfudatabase.ui.features.skill.components.SkillPointDecorationListItem
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewArmorData
import com.gaugustini.mhfudatabase.util.preview.PreviewDecorationData

@Composable
fun SkillTreeEquipmentContent(
    decorations: List<Decoration>,
    armors: List<Armor>,
    modifier: Modifier = Modifier,
    onArmorClick: (armorId: Int) -> Unit = {},
    onDecorationClick: (decorationId: Int) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier
    ) {
        item {
            SectionHeader(
                title = stringResource(R.string.skill_decoration_list),
            )
        }
        if (decorations.isEmpty()) {
            item {
                Text(
                    text = stringResource(R.string.skill_empty_list),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimension.Padding.large)
                )
            }
        } else {
            itemsIndexed(decorations) { index, decoration ->
                SkillPointDecorationListItem(
                    decoration = decoration,
                    onDecorationClick = onDecorationClick,
                )
                if (index != decorations.lastIndex) {
                    HorizontalDivider()
                }
            }
        }

        item {
            SectionHeader(
                title = stringResource(R.string.skill_armor_list),
            )
        }
        if (armors.isEmpty()) {
            item {
                Text(
                    text = stringResource(R.string.skill_empty_list),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimension.Padding.large)
                )
            }
        } else {
            itemsIndexed(armors) { index, armor ->
                SkillPointArmorListItem(
                    armor = armor,
                    onArmorClick = onArmorClick,
                )
                if (index != armors.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@DevicePreviews
@Composable
fun SkillTreeEquipmentContentPreview() {
    Theme {
        SkillTreeEquipmentContent(
            decorations = PreviewDecorationData.decorationList,
            armors = PreviewArmorData.armorList,
        )
    }
}

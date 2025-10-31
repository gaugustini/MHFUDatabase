package com.gaugustini.mhfudatabase.ui.skill.detail

import android.content.res.Configuration
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
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.data.model.SkillPointsArmor
import com.gaugustini.mhfudatabase.data.model.SkillPointsDecoration
import com.gaugustini.mhfudatabase.ui.components.SectionHeader
import com.gaugustini.mhfudatabase.ui.skill.components.SkillPointsArmorListItem
import com.gaugustini.mhfudatabase.ui.skill.components.SkillPointsDecorationListItem
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewSkillData

@Composable
fun SkillTreeEquipmentContent(
    decorations: List<SkillPointsDecoration>,
    armors: List<SkillPointsArmor>,
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
                SkillPointsDecorationListItem(
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
                SkillPointsArmorListItem(
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

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SkillTreeEquipmentContentPreview() {
    Theme {
        SkillTreeEquipmentContent(
            armors = PreviewSkillData.skillPointsArmorList,
            decorations = PreviewSkillData.skillPointsDecorationList,
        )
    }
}

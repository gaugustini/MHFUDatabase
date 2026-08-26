package com.gaugustini.mhfudatabase.ui.features.skill.detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.model.Armor
import com.gaugustini.mhfudatabase.domain.model.Decoration
import com.gaugustini.mhfudatabase.ui.components.AppHDivider
import com.gaugustini.mhfudatabase.ui.components.SectionHeader
import com.gaugustini.mhfudatabase.ui.features.skill.components.SkillPointArmorListItem
import com.gaugustini.mhfudatabase.ui.features.skill.components.SkillPointDecorationListItem
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.animateItemExpandCollapse
import com.gaugustini.mhfudatabase.util.preview.PreviewArmorData
import com.gaugustini.mhfudatabase.util.preview.PreviewDecorationData

@Composable
fun SkillTreeEquipmentContent(
    decorations: List<Decoration>,
    armors: List<Armor>,
    modifier: Modifier = Modifier,
    onChangePage: (SkillTreeDetailPage) -> Unit = {},
    onArmorClick: (armorId: Int) -> Unit = {},
    onDecorationClick: (decorationId: Int) -> Unit = {},
) {
    var decorationsExpanded by rememberSaveable { mutableStateOf(true) }
    var armorsExpanded by rememberSaveable { mutableStateOf(true) }

    BackHandler {
        onChangePage(SkillTreeDetailPage.SUMMARY)
    }

    LazyColumn(
        contentPadding = PaddingValues(Dimension.Padding.medium),
        modifier = modifier.fillMaxSize()
    ) {
        item {
            SectionHeader(
                title = stringResource(R.string.skill_decoration_list),
                isExpandable = true,
                expanded = decorationsExpanded,
                modifier = Modifier
                    .padding(bottom = if (decorationsExpanded) 0.dp else Dimension.Padding.medium)
                    .clip(
                        RoundedCornerShape(
                            topStart = Dimension.Radius.medium,
                            topEnd = Dimension.Radius.medium,
                            bottomStart = if (decorationsExpanded) 0.dp else Dimension.Radius.medium,
                            bottomEnd = if (decorationsExpanded) 0.dp else Dimension.Radius.medium,
                        )
                    )
                    .clickable { decorationsExpanded = !decorationsExpanded }
            )
        }
        if (decorationsExpanded) {
            if (decorations.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                RoundedCornerShape(
                                    bottomStart = Dimension.Radius.medium,
                                    bottomEnd = Dimension.Radius.medium,
                                )
                            )
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(Dimension.Padding.large)
                    ) {
                        Text(
                            text = stringResource(R.string.skill_empty_list),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                    Spacer(modifier = Modifier.height(Dimension.Spacing.medium))
                }
            } else {
                itemsIndexed(
                    items = decorations,
                    key = { _, decoration -> "dec_${decoration.id}" }
                ) { index, decoration ->
                    val isLastDecoration = index == decorations.lastIndex

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                RoundedCornerShape(
                                    bottomStart = if (isLastDecoration) Dimension.Radius.medium else 0.dp,
                                    bottomEnd = if (isLastDecoration) Dimension.Radius.medium else 0.dp,
                                )
                            )
                            .background(MaterialTheme.colorScheme.surface)
                            .animateItemExpandCollapse(this)
                    ) {
                        SkillPointDecorationListItem(
                            decoration = decoration,
                            onDecorationClick = onDecorationClick,
                        )
                        if (!isLastDecoration) {
                            AppHDivider()
                        }
                    }
                    if (isLastDecoration) {
                        Spacer(modifier = Modifier.height(Dimension.Spacing.medium))
                    }
                }
            }
        }

        item {
            SectionHeader(
                title = stringResource(R.string.skill_armor_list),
                isExpandable = true,
                expanded = armorsExpanded,
                modifier = Modifier
                    .padding(bottom = if (armorsExpanded) 0.dp else Dimension.Padding.medium)
                    .clip(
                        RoundedCornerShape(
                            topStart = Dimension.Radius.medium,
                            topEnd = Dimension.Radius.medium,
                            bottomStart = if (armorsExpanded) 0.dp else Dimension.Radius.medium,
                            bottomEnd = if (armorsExpanded) 0.dp else Dimension.Radius.medium,
                        )
                    )
                    .clickable { armorsExpanded = !armorsExpanded }
            )
        }
        if (armorsExpanded) {
            if (armors.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                RoundedCornerShape(
                                    bottomStart = Dimension.Radius.medium,
                                    bottomEnd = Dimension.Radius.medium,
                                )
                            )
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(Dimension.Padding.large)
                    ) {
                        Text(
                            text = stringResource(R.string.skill_empty_list),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                    Spacer(modifier = Modifier.height(Dimension.Spacing.medium))
                }
            } else {
                itemsIndexed(
                    items = armors,
                    key = { _, armor -> "armor_${armor.id}" }
                ) { index, armor ->
                    val isLastArmor = index == armors.lastIndex

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                RoundedCornerShape(
                                    bottomStart = if (isLastArmor) Dimension.Radius.medium else 0.dp,
                                    bottomEnd = if (isLastArmor) Dimension.Radius.medium else 0.dp,
                                )
                            )
                            .background(MaterialTheme.colorScheme.surface)
                            .animateItemExpandCollapse(this)
                    ) {
                        SkillPointArmorListItem(
                            armor = armor,
                            onArmorClick = onArmorClick,
                        )
                        if (!isLastArmor) {
                            AppHDivider()
                        }
                    }
                    if (isLastArmor) {
                        Spacer(modifier = Modifier.height(Dimension.Spacing.medium))
                    }
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

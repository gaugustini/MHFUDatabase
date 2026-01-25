package com.gaugustini.mhfudatabase.ui.search.components.listitem

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.HunterType
import com.gaugustini.mhfudatabase.domain.model.Armor
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.icons.ArmorIcon
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewArmorData

@Composable
fun SearchListItem(
    armor: Armor,
    modifier: Modifier = Modifier,
    onArmorClick: (armorId: Int) -> Unit = {},
) {
    Column(
        modifier = modifier.clickable { onArmorClick(armor.id) }
    ) {
        ListItemLayout(
            leadingContent = {
                ArmorIcon(
                    type = armor.type,
                    rarity = armor.rarity,
                    modifier = Modifier.size(SearchListItemDefaults.IconSize)
                )
            },
            headlineContent = {
                Text(
                    text = armor.name,
                    style = SearchListItemDefaults.HeadlineTextStyle,
                    color = SearchListItemDefaults.HeadlineTextColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            },
            trailingContent = {
                Text(
                    text = stringResource(
                        when (armor.hunterType) {
                            HunterType.BOTH -> R.string.search_armor_both
                            HunterType.BLADE -> R.string.search_armor_blade
                            HunterType.GUNNER -> R.string.search_armor_gunner
                        }
                    ),
                    style = SearchListItemDefaults.TrailingTextStyle,
                    color = SearchListItemDefaults.TrailingTextColor,
                )
            },
            contentPadding = PaddingValues(
                horizontal = SearchListItemDefaults.HorizontalPadding,
                vertical = SearchListItemDefaults.VerticalPadding,
            ),
        )
        HorizontalDivider()
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SearchListItemArmorPreview() {
    Theme {
        SearchListItem(PreviewArmorData.armor)
    }
}

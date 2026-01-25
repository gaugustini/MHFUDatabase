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
import com.gaugustini.mhfudatabase.domain.model.Weapon
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.icons.WeaponIcon
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewWeaponData

@Composable
fun SearchListItem(
    weapon: Weapon,
    modifier: Modifier = Modifier,
    onWeaponClick: (weaponId: Int) -> Unit = {},
) {
    Column(
        modifier = modifier.clickable { onWeaponClick(weapon.id) }
    ) {
        ListItemLayout(
            leadingContent = {
                WeaponIcon(
                    type = weapon.type,
                    rarity = weapon.rarity,
                    modifier = Modifier.size(SearchListItemDefaults.IconSize)
                )
            },
            headlineContent = {
                Text(
                    text = weapon.name,
                    style = SearchListItemDefaults.HeadlineTextStyle,
                    color = SearchListItemDefaults.HeadlineTextColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            },
            trailingContent = {
                Text(
                    text = stringResource(R.string.search_weapon),
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
fun SearchListItemWeaponPreview() {
    Theme {
        SearchListItem(PreviewWeaponData.weapon)
    }
}

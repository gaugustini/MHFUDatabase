package com.gaugustini.mhfudatabase.ui.userset.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.data.model.UserEquipmentSet
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.icons.ArmorSetIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewUserEquipmentSet

@Composable
fun UserSetList(
    sets: List<UserEquipmentSet>,
    modifier: Modifier = Modifier,
    onSetClick: (setId: Int) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(sets) { set ->
            UserSetListItem(
                set = set,
                onSetClick = onSetClick,
            )
        }
    }
}

@Composable
fun UserSetListItem(
    set: UserEquipmentSet,
    modifier: Modifier = Modifier,
    onSetClick: (setId: Int) -> Unit = {},
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
        modifier = modifier
            .padding(
                start = Dimension.Padding.large,
                end = Dimension.Padding.large,
                top = Dimension.Padding.medium,
            )
            .clickable { onSetClick(set.id) }
    ) {
        ListItemLayout(
            leadingContent = {
                ArmorSetIcon(
                    rarity = 1,
                    modifier = Modifier.size(Dimension.Size.large)
                )
            },
            headlineContent = {
                Text(
                    text = set.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            },
            backgroundColor = Color.Transparent,
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun UserSetListPreview() {
    Theme {
        UserSetList(PreviewUserEquipmentSet.userSetList)
    }
}

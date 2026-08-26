package com.gaugustini.mhfudatabase.ui.features.userset.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.model.UserEquipmentSet
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.icons.ArmorSetIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewUserEquipmentSet

@Composable
fun UserSetListItem(
    equipmentSet: UserEquipmentSet,
    modifier: Modifier = Modifier,
    onEquipmentSetClick: (setId: Int) -> Unit = {},
) {
    ListItemLayout(
        leadingContent = {
            ArmorSetIcon(
                rarity = 0,
                modifier = Modifier.size(Dimension.Size.large)
            )
        },
        headlineContent = {
            Text(
                text = equipmentSet.name.ifBlank { stringResource(R.string.user_set_new) },
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        modifier = modifier.clickable { onEquipmentSetClick(equipmentSet.id) }
    )
}

@DevicePreviews
@Composable
fun UserSetListItemPreview() {
    Theme {
        UserSetListItem(
            equipmentSet = PreviewUserEquipmentSet.userSet,
        )
    }
}

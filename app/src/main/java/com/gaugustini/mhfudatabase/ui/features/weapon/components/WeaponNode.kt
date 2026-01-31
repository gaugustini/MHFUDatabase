package com.gaugustini.mhfudatabase.ui.features.weapon.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gaugustini.mhfudatabase.domain.model.WeaponNode
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewWeaponData

@Composable
fun WeaponNode(
    node: WeaponNode,
    modifier: Modifier = Modifier,
    onWeaponClick: (weaponId: Int) -> Unit = {},
) {
    Column(
        modifier = modifier.padding(start = Dimension.Padding.small)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Spacer(modifier = Modifier.width(Dimension.Spacing.small))
            WeaponListItem(
                weapon = node.weapon,
                onWeaponClick = { onWeaponClick(node.weapon.id) },
                modifier = Modifier.weight(1f)
            )
        }
        HorizontalDivider()

        node.children.forEach { child ->
            WeaponNode(
                node = child,
                onWeaponClick = onWeaponClick,
            )
        }
    }
}

@DevicePreviews
@Composable
fun WeaponNodePreview() {
    Theme {
        WeaponNode(
            node = PreviewWeaponData.weaponNodeList.first(),
        )
    }
}

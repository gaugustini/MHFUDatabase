package com.gaugustini.mhfudatabase.ui.weapon.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.data.model.WeaponNode
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewWeaponData

@Composable
fun WeaponGraph(
    graph: List<WeaponNode>,
    modifier: Modifier = Modifier,
    onWeaponClick: (weaponId: Int) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(graph) { root ->
            WeaponNode(
                node = root,
                onWeaponClick = onWeaponClick,
            )
        }
    }
}

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

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun WeaponGraphPreview() {
    Theme {
        WeaponGraph(
            PreviewWeaponData.graph,
        )
    }
}

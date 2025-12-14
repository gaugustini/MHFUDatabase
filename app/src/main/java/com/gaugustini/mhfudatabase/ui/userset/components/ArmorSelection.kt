package com.gaugustini.mhfudatabase.ui.userset.components

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.data.model.Armor
import com.gaugustini.mhfudatabase.ui.armor.components.ArmorListItem
import com.gaugustini.mhfudatabase.ui.components.NavigationType
import com.gaugustini.mhfudatabase.ui.components.TopBar
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewArmorData

@Composable
fun ArmorSelection(
    armors: List<Armor>,
    onArmorClick: (armorId: Int) -> Unit = {},
    onBack: () -> Unit = {},
) {
    BackHandler { onBack() }

    Scaffold(
        topBar = { // TODO: Change top bar for filters
            TopBar(
                title = stringResource(R.string.screen_user_set_list),
                navigationType = NavigationType.BACK,
                navigation = onBack,
                openSearch = {},
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(armors) { armor ->
                ArmorListItem(
                    armor = armor,
                    onArmorClick = onArmorClick
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ArmorSelectionPreview(
) {
    Theme {
        ArmorSelection(
            armors = PreviewArmorData.armorList,
        )
    }
}

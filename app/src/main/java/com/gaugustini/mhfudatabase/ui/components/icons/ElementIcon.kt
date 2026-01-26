package com.gaugustini.mhfudatabase.ui.components.icons

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.WeaponElement
import com.gaugustini.mhfudatabase.util.MHFUIcons

@Composable
fun ElementIcon(
    element: WeaponElement,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(
            id = MHFUIcons.elements[element] ?: R.drawable.ic_ui_unknown
        ),
        contentDescription = null,
        modifier = modifier
    )
}

@Preview
@Composable
fun ElementIconPreview() {
    ElementIcon(WeaponElement.FIRE)
}

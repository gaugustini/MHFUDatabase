package com.gaugustini.mhfudatabase.ui.search.components.listitem

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import com.gaugustini.mhfudatabase.ui.theme.Dimension

object SearchListItemDefaults {

    val IconSize = Dimension.Size.small

    val HeadlineTextStyle: TextStyle @Composable get() = MaterialTheme.typography.bodyMedium

    val HeadlineTextColor @Composable get() = MaterialTheme.colorScheme.onSurface

    val TrailingTextStyle: TextStyle @Composable get() = MaterialTheme.typography.bodyMedium

    val TrailingTextColor @Composable get() = MaterialTheme.colorScheme.onSurfaceVariant

    val HorizontalPadding = Dimension.Padding.large

    val VerticalPadding = Dimension.Padding.medium

}

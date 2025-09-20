package com.gaugustini.mhfudatabase.ui.about

import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import com.gaugustini.mhfudatabase.BuildConfig
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.SectionHeader
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme

private data class AboutItem(
    val icon: Int,
    val title: String,
    val uri: String,
)

@Composable
fun AboutContent(
    modifier: Modifier = Modifier,
) {
    val links = listOf(
        AboutItem(
            icon = R.drawable.ic_item_book,
            title = "Gathering Hall Studios",
            uri = "https://github.com/gatheringhallstudios",
        ),
        AboutItem(
            icon = R.drawable.ic_item_book,
            title = "MHFU-DB",
            uri = "https://github.com/Kolyn090/mhfu-db",
        ),
        AboutItem(
            icon = R.drawable.ic_item_book,
            title = "MHFU Blacksmith",
            uri = "https://mhfu.vallode.com/",
        ),
        AboutItem(
            icon = R.drawable.ic_item_book,
            title = "MHFU Wiki",
            uri = "https://monsterhunter.fandom.com/wiki/Monster_Hunter_Freedom_Unite",
        ),
        AboutItem(
            icon = R.drawable.ic_item_book,
            title = "MHP2G Wiki",
            uri = "https://w.atwiki.jp/mhp2g/",
        ),
        AboutItem(
            icon = R.drawable.ic_item_book,
            title = "GameFAQs",
            uri = "https://gamefaqs.gamespot.com/psp/943356-monster-hunter-freedom-unite",
        ),
        AboutItem(
            icon = R.drawable.ic_item_book,
            title = "Neoseeker",
            uri = "https://monsterhunter.neoseeker.com/wiki/Monster_Hunter_Freedom_Unite_(PSP)",
        ),
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(Dimension.Padding.large),
        )
        Text(
            text = stringResource(R.string.about_version, BuildConfig.VERSION_NAME),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(
                horizontal = Dimension.Padding.large,
                vertical = Dimension.Padding.medium,
            ),
        )
        Text(
            text = stringResource(R.string.about_description),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(Dimension.Padding.large),
        )
        LinkItem(
            icon = R.drawable.ic_item_book,
            title = "GitHub",
            uri = "https://github.com/gaugustini/MHFUDatabase",
        )

        SectionHeader(
            title = stringResource(R.string.about_credit_resources),
        )
        links.forEach { link ->
            LinkItem(
                icon = link.icon,
                title = link.title,
                uri = link.uri,
            )
            HorizontalDivider()
        }
    }
}

@Composable
fun LinkItem(
    icon: Int,
    title: String,
    uri: String,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    ListItemLayout(
        leadingContent = {
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(Dimension.Size.small)
            )
        },
        headlineContent = {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        contentPadding = PaddingValues(
            horizontal = Dimension.Padding.large,
            vertical = Dimension.Padding.medium,
        ),
        modifier = modifier
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, uri.toUri())
                context.startActivity(intent)
            }
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AboutContentPreview() {
    Theme {
        AboutContent()
    }
}

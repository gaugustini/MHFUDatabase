package com.gaugustini.mhfudatabase.ui.features.about

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.core.net.toUri
import com.gaugustini.mhfudatabase.BuildConfig
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.NavigationType
import com.gaugustini.mhfudatabase.ui.components.SectionHeader
import com.gaugustini.mhfudatabase.ui.components.TopBar
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.LocalIsDarkTheme
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.ForEachWithDivider

private data class AboutItem(
    val icon: Int,
    val title: String,
    val uri: String,
    val author: String? = null,
)

@Composable
fun AboutRoute(
    navigateBack: () -> Unit,
    openSearch: () -> Unit,
) {
    AboutScreen(
        navigateBack = navigateBack,
        openSearch = openSearch,
    )
}

@Composable
fun AboutScreen(
    navigateBack: () -> Unit = {},
    openSearch: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.screen_about),
                navigationType = NavigationType.BACK,
                navigation = navigateBack,
                openSearch = openSearch,
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            AppDescription()
            CreditsAndResourcesList()
        }
    }
}

@Composable
fun AppDescription(
    modifier: Modifier = Modifier,
) {
    val isDarkTheme = LocalIsDarkTheme.current

    Column(
        modifier = modifier
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
            modifier = Modifier.padding(
                horizontal = Dimension.Padding.large,
                vertical = Dimension.Padding.medium,
            ),
        )
        Text(
            text = stringResource(R.string.about_visit_project),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(
                horizontal = Dimension.Padding.large,
                vertical = Dimension.Padding.medium,
            ),
        )
        ExternalLinkItem(
            icon = if (isDarkTheme) R.drawable.ic_github_white else R.drawable.ic_github_black,
            title = "GitHub",
            author = null,
            uri = "https://github.com/gaugustini/MHFUDatabase",
        )
    }
}

@Composable
fun CreditsAndResourcesList(
    modifier: Modifier = Modifier,
) {
    val isDarkTheme = LocalIsDarkTheme.current

    val links = listOf(
        AboutItem(
            icon = if (isDarkTheme) R.drawable.ic_github_white else R.drawable.ic_github_black,
            title = "Gathering Hall Studios",
            uri = "https://github.com/gatheringhallstudios",
        ),
        AboutItem(
            icon = if (isDarkTheme) R.drawable.ic_github_white else R.drawable.ic_github_black,
            title = "MHFU-DB",
            uri = "https://github.com/Kolyn090/mhfu-db",
            author = "Kolyn090",
        ),
        AboutItem(
            icon = R.drawable.ic_weapon_great_sword,
            title = "MHFU Blacksmith",
            uri = "https://mhfu.vallode.com/",
            author = "vallode",
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
            author = "ryin77, ZeoKnight, Boldrin",
        ),
        AboutItem(
            icon = R.drawable.ic_item_book,
            title = "Neoseeker",
            uri = "https://monsterhunter.neoseeker.com/wiki/Monster_Hunter_Freedom_Unite_(PSP)",
        ),
        AboutItem(
            icon = if (isDarkTheme) R.drawable.ic_github_white else R.drawable.ic_github_black,
            title = "Monster Hunter DB",
            uri = "https://github.com/CrimsonNynja/monster-hunter-DB",
            author = "CrimsonNynja",
        )
    )

    Column(
        modifier = modifier
    ) {
        SectionHeader(
            title = stringResource(R.string.about_credit_resources),
        )
        links.ForEachWithDivider { link ->
            ExternalLinkItem(
                icon = link.icon,
                title = link.title,
                author = link.author,
                uri = link.uri,
            )
        }
    }
}

@Composable
fun ExternalLinkItem(
    icon: Int,
    title: String,
    author: String?,
    uri: String,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val intent = Intent(Intent.ACTION_VIEW, uri.toUri())

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
        trailingContent = {
            if (author != null) {
                Text(
                    text = author,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        },
        contentPadding = PaddingValues(
            horizontal = Dimension.Padding.large,
            vertical = Dimension.Padding.medium,
        ),
        modifier = modifier.clickable {
            context.startActivity(intent)
        }
    )
}

@DevicePreviews
@Composable
fun AboutScreenPreview() {
    Theme {
        AboutScreen()
    }
}

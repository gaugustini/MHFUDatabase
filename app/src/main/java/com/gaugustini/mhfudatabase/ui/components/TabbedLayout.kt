package com.gaugustini.mhfudatabase.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gaugustini.mhfudatabase.ui.theme.Theme
import kotlinx.coroutines.launch

@Composable
fun TabbedLayout(
    pagerState: PagerState,
    tabTitles: List<String>,
    modifier: Modifier = Modifier,
    scrollableTabs: Boolean = tabTitles.size > 3,
    topBar: @Composable () -> Unit = {},
    content: @Composable PagerScope.(Int) -> Unit = {}
) {
    val animationScope = rememberCoroutineScope()

    Scaffold(
        topBar = topBar,
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (scrollableTabs) {
                ScrollableTabRow(
                    selectedTabIndex = pagerState.currentPage,
                    edgePadding = 0.dp,
                ) {
                    tabTitles.forEachIndexed { index, title ->
                        Tab(
                            text = { Text(title) },
                            selected = index == pagerState.currentPage,
                            onClick = {
                                animationScope.launch { pagerState.animateScrollToPage(index) }
                            },
                        )
                    }
                }
            } else {
                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                ) {
                    tabTitles.forEachIndexed { index, title ->
                        Tab(
                            text = { Text(title) },
                            selected = index == pagerState.currentPage,
                            onClick = {
                                animationScope.launch { pagerState.animateScrollToPage(index) }
                            },
                        )
                    }
                }
            }
            HorizontalPager(
                state = pagerState,
                verticalAlignment = Alignment.Top,
                pageContent = content,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TabbedLayoutPreview() {
    Theme {
        TabbedLayout(
            pagerState = rememberPagerState(pageCount = { 3 }),
            tabTitles = listOf("Tab 1", "Tab 2", "Tab 3"),
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TabbedLayoutScrollablePreview() {
    Theme {
        TabbedLayout(
            pagerState = rememberPagerState(pageCount = { 5 }),
            tabTitles = listOf("Tab 1", "Tab 2", "Tab 3", "Tab 4", "Tab 5"),
            scrollableTabs = true,
        )
    }
}

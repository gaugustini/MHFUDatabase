package com.gaugustini.mhfudatabase.ui.quest.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.data.model.Quest
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewQuestData

@Composable
fun QuestSummary(
    quest: Quest,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(Dimension.Padding.large)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(Dimension.Padding.small),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "${quest.reward}z",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = stringResource(R.string.quest_reward),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(Dimension.Padding.small),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "${quest.fee}z",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = stringResource(R.string.quest_fee),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(Dimension.Padding.small),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "${quest.timeLimit}m",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = stringResource(R.string.quest_time_limit),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun QuestDetailsPreview() {
    Theme {
        QuestSummary(
            quest = PreviewQuestData.quest,
        )
    }
}

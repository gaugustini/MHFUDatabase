package com.gaugustini.mhfudatabase.ui.components.icons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.data.enums.QuestGoalType
import com.gaugustini.mhfudatabase.util.MHFUColors

@Composable
fun QuestIcon(
    goalType: QuestGoalType,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        BackgroundDecorator(
            modifier = Modifier.fillMaxSize()
        )
        Image(
            painter = painterResource(id = R.drawable.ic_quest),
            contentDescription = null,
            colorFilter = ColorFilter.tint(
                color = MHFUColors.getQuestColor(goalType),
                blendMode = BlendMode.Modulate
            ),
            modifier = Modifier.fillMaxSize(0.8f)
        )
    }
}

@Preview
@Composable
fun QuestIconPreview() {
    QuestIcon(QuestGoalType.HUNT)
}

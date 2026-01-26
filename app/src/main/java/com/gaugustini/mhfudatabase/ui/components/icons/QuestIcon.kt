package com.gaugustini.mhfudatabase.ui.components.icons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.QuestGoal
import com.gaugustini.mhfudatabase.util.MHFUColors

@Composable
fun QuestIcon(
    goalType: QuestGoal,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(id = R.drawable.ic_quest),
        contentDescription = null,
        colorFilter = ColorFilter.tint(
            color = MHFUColors.getQuestColor(goalType),
            blendMode = BlendMode.Modulate
        ),
        modifier = modifier.fillMaxSize()
    )
}

@Preview
@Composable
fun QuestIconPreview() {
    QuestIcon(QuestGoal.HUNT)
}

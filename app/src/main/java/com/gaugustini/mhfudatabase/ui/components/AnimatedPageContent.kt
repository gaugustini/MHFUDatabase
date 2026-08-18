package com.gaugustini.mhfudatabase.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun <T> AnimatedPageContent(
    targetState: T,
    indexMapper: (T) -> Int,
    modifier: Modifier = Modifier,
    content: @Composable (T) -> Unit
) {
    AnimatedContent(
        targetState = targetState,
        transitionSpec = {
            val initialIndex = indexMapper(initialState)
            val targetIndex = indexMapper(targetState)

            if (initialIndex == targetIndex) {
                return@AnimatedContent EnterTransition.None togetherWith ExitTransition.None
            }

            val isForward = targetIndex > initialIndex

            val direction = if (isForward) {
                AnimatedContentTransitionScope.SlideDirection.Start
            } else {
                AnimatedContentTransitionScope.SlideDirection.End
            }

            slideIntoContainer(
                towards = direction,
                animationSpec = tween(durationMillis = 300, easing = EaseOut)
            ).togetherWith(
                slideOutOfContainer(
                    towards = direction,
                    animationSpec = tween(durationMillis = 300, easing = EaseIn)
                )
            ).apply {
                targetContentZIndex = if (isForward) 1f else -1f
            }
        },
        modifier = modifier,
    ) { state ->
        content(state)
    }
}

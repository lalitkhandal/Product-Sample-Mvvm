package com.lalit.clean.util

import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

/**
 * A custom extension function for [NavGraphBuilder] that adds a composable screen
 * with a slide-in and slide-out animation to the navigation graph.
 *
 * @param T The type of the composable destination. This is a reified type, meaning
 *          it can be accessed at runtime and used for type-specific operations.
 * @param content A composable function that is shown within the [AnimatedVisibilityScope].
 *
 * Usage:
 * ```kotlin
 *     composableWithSlideAnimation<Screen.Feed> { backStackEntry ->
 *         Screen(backStackEntry)
 *     }
 * ```
 *
 * This function provides an easy way to integrate slide animations in your Compose navigation
 * flow.
 */
inline fun <reified T : Any> NavGraphBuilder.composableWithSlideAnimation(
    noinline content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable<T>(
        enterTransition = {
            slideIntoContainer(towards = SlideDirection.Start, animationSpec = tween(350))
        },
        exitTransition = {
            slideOutOfContainer(towards = SlideDirection.Start, animationSpec = tween(350))
        },
        popEnterTransition = {
            slideIntoContainer(towards = SlideDirection.End, animationSpec = tween(350))
        },
        popExitTransition = {
            slideOutOfContainer(towards = SlideDirection.End, animationSpec = tween(350))
        },
        content = content
    )
}
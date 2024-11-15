package com.lalit.clean.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * A composable function to implement pull-down-to-refresh functionality.
 *
 * This function wraps the provided content and provides the ability to refresh the content
 * when the user performs a pull-to-refresh gesture. The `PullRefreshState` is used to control
 * the refresh state and trigger the refresh action.
 *
 * @param state The [PullRefreshState] that tracks the current refresh state.
 * @param refresh A boolean that indicates if the content is currently refreshing. Default is `false`.
 * @param content The composable content that will be displayed within the refreshable area.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PullDownToRefresh(
    state: PullRefreshState,
    refresh: Boolean = false,
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.pullRefresh(state)) {
        content()
        PullRefreshIndicator(refresh, state, Modifier.align(Alignment.TopCenter))
    }
}

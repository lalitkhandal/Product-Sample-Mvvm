package com.lalit.clean.ui.feed

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.lalit.clean.domain.entities.ProductEntity
import com.lalit.clean.ui.component.ErrorFullView
import com.lalit.clean.ui.component.ProductCard
import com.lalit.clean.ui.component.ProgressView
import com.lalit.clean.ui.component.PullDownToRefresh
import com.lalit.clean.ui.feed.state.ResultUiState
import com.lalit.clean.ui.graph.RootRouter

/**
 * Composable function to display the feed screen of the app.
 *
 *
 * @param rootRouter The [RootRouter] used for navigation actions.
 * @param viewModel The [FeedViewModel] used to manage the UI state and business logic for the feed screen.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FeedScreen(rootRouter: RootRouter, viewModel: FeedViewModel) {
    val state = viewModel.resultUiState.collectAsState()
    val pullToRefreshState = rememberPullRefreshState(state.value is ResultUiState.Loading, {
        viewModel.fetchProducts()
    })

    // Call the API only one time to avoid multiple calls at recomposition time
    LaunchedEffect(Unit) {
        viewModel.fetchProducts()
    }

    PullDownToRefresh(state = pullToRefreshState, refresh = state.value is ResultUiState.Loading) {
        FeedScreenContent(state.value, onRetry = {
            viewModel.fetchProducts(true)
        }, onCardClick = { productId ->
            rootRouter.navigateToFeedDetails(productId)
        })
    }
}

@Suppress("UNCHECKED_CAST")
@Composable
private fun FeedScreenContent(
    state: ResultUiState,
    onRetry: () -> Unit,
    onCardClick: (productId: Int) -> Unit
) {
    when (state) {
        is ResultUiState.Success -> {
            LazyColumn {
                items(state.data as List<ProductEntity>) { product ->
                    ProductCard(product, onCardClick)
                }
            }
        }

        is ResultUiState.Loading -> ProgressView()
        is ResultUiState.Error -> FeedScreenErrorView(
            exception = state.exception.message,
            onRetry = onRetry
        )
    }
}

@Composable
private fun FeedScreenErrorView(exception: String?, onRetry: () -> Unit) {
    exception?.let {
        ErrorFullView(errorMessage = it) {
            onRetry()
        }
    }
}
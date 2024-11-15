package com.lalit.clean.ui.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.lalit.clean.R
import com.lalit.clean.domain.entities.ProductEntity
import com.lalit.clean.ui.component.ErrorNoMatch
import com.lalit.clean.ui.component.ProductCard
import com.lalit.clean.ui.component.ProgressView
import com.lalit.clean.ui.component.debounced
import com.lalit.clean.ui.feed.state.ResultUiState
import com.lalit.clean.ui.graph.RootRouter
import com.lalit.clean.ui.theme.AppTheme
import com.lalit.clean.ui.theme.Dimension
import com.lalit.clean.util.showToast

@Composable
fun CartScreen(rootRouter: RootRouter, viewModel: CartViewModel) {
    val state = viewModel.cartState.collectAsState()

    // Call the API only one time to avoid multiple calls at recomposition time
    LaunchedEffect(Unit) {
        viewModel.fetchCartData()
    }

    CartScreenContent(state.value, onCardClick = { productId ->
        rootRouter.navigateToFeedDetails(productId)
    })
}

@Suppress("UNCHECKED_CAST")
@Composable
private fun CartScreenContent(
    state: ResultUiState,
    onCardClick: (productId: Int) -> Unit
) {
    val context = LocalContext.current
    when (state) {
        is ResultUiState.Success -> {
            Column(
                modifier = Modifier.fillMaxSize(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                LazyColumn(modifier = Modifier.weight(1f, false)) {
                    items(state.data as List<ProductEntity>) { product ->
                        ProductCard(product, onCardClick)
                    }
                }
                val checkoutClickable = debounced(onClick = {
                    context.showToast("Upcoming feature")
                })
                Button(
                    onClick = checkoutClickable,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimension.Padding.padding16)
                ) {
                    Text(
                        text = stringResource(R.string.checkout),
                        style = AppTheme.typography.labelLarge,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        is ResultUiState.Loading -> ProgressView()

        is ResultUiState.Error -> {
            ErrorNoMatch(
                modifier = Modifier.padding(Dimension.Padding.padding16),
                message = state.exception.message
            )
        }
    }
}
package com.lalit.clean.ui.feeddetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import com.lalit.clean.R
import com.lalit.clean.domain.entities.ProductEntity
import com.lalit.clean.ui.component.ErrorFullView
import com.lalit.clean.ui.component.ProgressView
import com.lalit.clean.ui.component.TopBar
import com.lalit.clean.ui.component.debounced
import com.lalit.clean.ui.feed.state.ResultUiState
import com.lalit.clean.ui.theme.AppTheme
import com.lalit.clean.ui.theme.Dimension
import com.lalit.clean.util.showToast
import kotlinx.coroutines.delay

@Composable
fun FeedDetailsScreen(rootNavController: NavHostController, viewModel: FeedDetailsViewModel) {
    val context = LocalContext.current
    val state by viewModel.resultUiState.collectAsState()

    // Call the API only one time to avoid multiple calls at recomposition time
    LaunchedEffect(Unit) {
        viewModel.fetchProduct()
    }

    FeedDetailsContent(state, onBackClick = {
        rootNavController.navigateUp()
    }, onAddToCart = { entity ->
        viewModel.saveCartProduct(product = entity) {
            context.showToast("Product is added to cart")
        }
    }, onRetry = {
        viewModel.fetchProduct()
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedDetailsContent(
    state: ResultUiState,
    onBackClick: () -> Unit,
    onAddToCart: (ProductEntity) -> Unit,
    onRetry: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.product_details),
                onNavigationIconClick = onBackClick
            )
        },
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (state) {
                is ResultUiState.Success -> DetailsContent(
                    data = state.data as ProductEntity,
                    onAddToCart
                )

                is ResultUiState.Loading -> ProgressView()
                is ResultUiState.Error -> {
                    state.exception.message?.let {
                        ErrorFullView(errorMessage = it) {
                            onRetry()
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun DetailsContent(data: ProductEntity, onAddToCart: (ProductEntity) -> Unit) {
    var animationVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(100)
        animationVisible = true
    }

    Column(
        modifier = Modifier.fillMaxSize(1f),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxHeight()
                .weight(1f, false)
        ) {
            SubcomposeAsyncImage(
                model = data.thumbnail,
                loading = { Placeholder() },
                error = { Placeholder() },
                contentDescription = "Product Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(Dimension.Size.size250)
                    .fillMaxWidth()
            )

            AnimatedVisibility(visible = animationVisible, enter = fadeIn()) {
                Text(
                    text = data.title,
                    style = AppTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(Dimension.Padding.padding16)
                        .fillMaxWidth(),
                )
            }

            AnimatedVisibility(visible = animationVisible) {
                TextContent(data.description)
            }

            AnimatedVisibility(visible = animationVisible) {
                TextContent(data.category)
            }

            AnimatedVisibility(visible = animationVisible) {
                TextContent(stringResource(R.string.formatted_price, data.price))
            }
        }

        AnimatedVisibility(visible = animationVisible) {
            val addToCartClickable = debounced(onClick = {
                onAddToCart(data)
            })
            Button(
                onClick = addToCartClickable,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimension.Padding.padding16)
            ) {
                Text(
                    text = stringResource(R.string.add_to_cart),
                    style = AppTheme.typography.labelLarge,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun TextContent(text: String) {
    Text(
        text = text,
        style = AppTheme.typography.bodyMedium,
        modifier = Modifier
            .padding(
                horizontal = Dimension.Padding.padding16,
                vertical = Dimension.Padding.padding4
            )
            .fillMaxWidth(),
    )
}

@Composable
private fun Placeholder() {
    Image(
        painter = painterResource(id = R.drawable.bg_place_holder),
        contentDescription = "",
        contentScale = ContentScale.Crop,
    )
}
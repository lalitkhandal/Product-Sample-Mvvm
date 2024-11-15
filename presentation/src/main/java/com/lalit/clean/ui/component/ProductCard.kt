package com.lalit.clean.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.lalit.clean.R
import com.lalit.clean.domain.entities.ProductEntity
import com.lalit.clean.ui.theme.AppTheme
import com.lalit.clean.ui.theme.Dimension

/**
 * Composable function to display a product card.
 *
 * This card displays product details and triggers a callback when the card is clicked.
 *
 * @param product The [ProductEntity] object containing product data to be displayed.
 * @param onCardClick A lambda function that gets invoked when the card is clicked.
 */
@Composable
fun ProductCard(product: ProductEntity, onCardClick: (productId: Int) -> Unit) {
    OutlinedCard(
        onClick = {
            onCardClick(product.id)
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimension.Padding.defaultPadding),
        elevation = CardDefaults.outlinedCardElevation(defaultElevation = Dimension.Elevation.default)
    ) {
        Row {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(product.thumbnail).placeholder(R.drawable.bg_place_holder)
                        .build()
                ),
                contentDescription = product.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.Companion
                    .size(Dimension.Size.size120)
            )
            Column(modifier = Modifier.padding(horizontal = Dimension.Padding.padding16)) {
                Text(
                    text = product.title,
                    style = AppTheme.typography.titleSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = Dimension.Padding.paddingTop)
                )
                Text(
                    text = product.category,
                    style = AppTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = Dimension.Padding.padding4)
                )
                Text(
                    text = stringResource(R.string.formatted_price, product.price),
                    style = AppTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = Dimension.Padding.padding4)
                )

                if (product.cartQuantity > 0) {
                    Text(
                        text = stringResource(R.string.added_quantity, product.cartQuantity),
                        style = AppTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = Dimension.Padding.padding4)
                    )
                }
            }
        }
    }

}

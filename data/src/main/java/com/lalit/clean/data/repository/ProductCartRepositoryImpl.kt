package com.lalit.clean.data.repository

import com.lalit.clean.data.datasource.ProductCartDataSource
import com.lalit.clean.domain.entities.ProductEntity
import com.lalit.clean.domain.repository.ProductCartRepository
import com.lalit.clean.domain.util.Result

/**
 * Implementation of the [ProductCartRepository] interface for managing cart-related operations.
 * This class interacts with the [ProductCartDataSource] to perform actions on the cart.
 *
 * @param productCartDataSource The [ProductCartDataSource] used for fetching and updating cart data.
 */
class ProductCartRepositoryImpl(private val productCartDataSource: ProductCartDataSource) :
    ProductCartRepository {
    override suspend fun getCartProducts(): Result<List<ProductEntity>> {
        return productCartDataSource.getCartProducts()
    }

    override suspend fun saveCartProduct(product: ProductEntity) {
        productCartDataSource.saveCartProduct(product)
    }

    override suspend fun removeCartProduct(productId: Int) {
        productCartDataSource.removeCartProduct(productId)
    }
}
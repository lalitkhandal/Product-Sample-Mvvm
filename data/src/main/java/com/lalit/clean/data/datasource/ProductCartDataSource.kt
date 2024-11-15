package com.lalit.clean.data.datasource

import com.lalit.clean.domain.entities.ProductEntity
import com.lalit.clean.domain.util.Result

/**
 * Data source interface for managing products in a shopping cart.
 */
interface ProductCartDataSource {
    /**
     * Fetches the list of products in the cart.
     *
     * @return Result containing a list of [ProductEntity] on success, or an error on failure.
     */
    suspend fun getCartProducts(): Result<List<ProductEntity>>

    /**
     * Saves a product to the cart.
     *
     * @param product The [ProductEntity] to be saved in the cart.
     */
    suspend fun saveCartProduct(product: ProductEntity)

    /**
     * Removes a product from the cart.
     *
     * @param productId The ID of the product to be removed.
     */
    suspend fun removeCartProduct(productId: Int)
}
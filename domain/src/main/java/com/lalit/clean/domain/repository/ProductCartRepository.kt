package com.lalit.clean.domain.repository

import com.lalit.clean.domain.entities.ProductEntity
import com.lalit.clean.domain.util.Result

/**
 * Interface for accessing product data.
 *
 * This interface defines the contract for retrieving product information.
 */
interface ProductCartRepository {
    suspend fun getCartProducts(): Result<List<ProductEntity>>
    suspend fun saveCartProduct(product: ProductEntity)
    suspend fun removeCartProduct(productId: Int)
}
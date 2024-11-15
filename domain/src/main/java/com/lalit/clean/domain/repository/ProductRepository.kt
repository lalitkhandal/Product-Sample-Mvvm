package com.lalit.clean.domain.repository

import com.lalit.clean.domain.entities.ProductEntity
import com.lalit.clean.domain.util.Result

/**
 * Interface for accessing product data.
 *
 * This interface defines the contract for retrieving product information.
 */
interface ProductRepository {
    suspend fun getProducts(isForceRefresh: Boolean): Result<List<ProductEntity>>
    suspend fun getProduct(productId: Int): Result<ProductEntity>
}
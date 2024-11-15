package com.lalit.clean.domain.usecase

import com.lalit.clean.domain.entities.ProductEntity
import com.lalit.clean.domain.repository.ProductCartRepository

/**
 * Use case for retrieving products.
 *
 * @property productCartRepository The repository responsible for fetching product data from cart.
 * @see ProductCartRepository for the repository implementation that retrieves products.
 */
class CartUseCase(private val productCartRepository: ProductCartRepository) {
    suspend fun getCartProducts() = productCartRepository.getCartProducts()

    suspend fun saveCartProduct(product: ProductEntity) =
        productCartRepository.saveCartProduct(product)

    suspend fun removeCartProduct(productId: Int) =
        productCartRepository.removeCartProduct(productId)
}
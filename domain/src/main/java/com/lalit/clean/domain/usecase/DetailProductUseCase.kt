package com.lalit.clean.domain.usecase

import com.lalit.clean.domain.repository.ProductRepository

/**
 * Use case for product details.
 *
 * @property productRepository The repository responsible for fetching product data.
 * @see ProductRepository for the repository implementation that retrieves products.
 */
class DetailProductUseCase(private val productRepository: ProductRepository) {
    /**
     * Retrieves a product from the repository.
     *
     * @return A [Result] containing a  [ProductEntity] on success, or an error on failure.
     */
    suspend fun invoke(productId: Int) = productRepository.getProduct(productId)
}
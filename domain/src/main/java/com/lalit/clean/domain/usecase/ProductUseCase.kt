package com.lalit.clean.domain.usecase

import com.lalit.clean.domain.repository.ProductRepository

/**
 * Use case for retrieving products.
 *
 * @property productRepository The repository responsible for fetching product data.
 * @see ProductRepository for the repository implementation that retrieves products.
 */
class ProductUseCase(private val productRepository: ProductRepository) {
    /**
     * Retrieves a list of products from the repository.
     * @param isForceRefresh A bool variable that inform repository to force the data from API
     * @return A [Result] containing a list of [ProductEntity] on success, or an error on failure.
     */
    suspend fun getProducts(isForceRefresh: Boolean) = productRepository.getProducts(isForceRefresh)
}
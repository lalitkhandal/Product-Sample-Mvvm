package com.lalit.clean.data.repository

import com.lalit.clean.data.datasource.ProductDataSource
import com.lalit.clean.domain.entities.ProductEntity
import com.lalit.clean.domain.repository.ProductRepository
import com.lalit.clean.domain.util.Result
import com.lalit.clean.domain.util.onSuccess

/**
 * Implementation of the [ProductRepository] interface for managing product data.
 * This class combines remote and local data sources to fetch and store product information.
 *
 * @param remoteSource The [ProductDataSource.Remote] for fetching product data from a remote API.
 * @param localSource The [ProductDataSource.Local] for managing product data in local storage.
 */
class ProductRepositoryImpl(
    private val remoteSource: ProductDataSource.Remote,
    private val localSource: ProductDataSource.Local,
) : ProductRepository {
    override suspend fun getProducts(isForceRefresh: Boolean): Result<List<ProductEntity>> {
        val result =
            when (val localResult = if (isForceRefresh.not()) localSource.getProducts() else null) {
                is Result.Success -> localResult
                else -> remoteSource.getProducts().onSuccess { products ->
                    if (isForceRefresh)
                        localSource.clearProducts()

                    localSource.saveProducts(products)
                    products
                }
            }
        return result
    }

    override suspend fun getProduct(productId: Int): Result<ProductEntity> {
        val result =
            when (val localResult = localSource.getProduct(productId)) {
                is Result.Success -> localResult
                else -> remoteSource.getProduct(productId)
            }
        return result
    }
}
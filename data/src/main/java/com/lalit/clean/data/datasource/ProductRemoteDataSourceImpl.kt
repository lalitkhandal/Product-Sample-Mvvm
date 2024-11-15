package com.lalit.clean.data.datasource

import com.lalit.clean.data.api.ProductApi
import com.lalit.clean.data.mapper.toDomain
import com.lalit.clean.data.util.processRequestResult
import com.lalit.clean.domain.entities.ProductEntity
import com.lalit.clean.domain.util.Result

/**
 * Implementation of [ProductDataSource.Remote] for fetching product data from a remote API.
 *
 * @param productApi The [ProductApi] instance used to interact with the remote API.
 */
class ProductRemoteDataSourceImpl(private val productApi: ProductApi) : ProductDataSource.Remote {
    override suspend fun getProducts(): Result<List<ProductEntity>> {
        return processRequestResult {
            productApi.getProducts().toDomain()
        }
    }

    override suspend fun getProduct(productId: Int): Result<ProductEntity> {
        return processRequestResult {
            productApi.getProduct(productId).toDomain()
        }
    }
}
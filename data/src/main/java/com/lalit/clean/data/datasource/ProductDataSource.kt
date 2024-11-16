package com.lalit.clean.data.datasource

import com.lalit.clean.domain.entities.ProductEntity
import com.lalit.clean.domain.util.Result

/**
 * Data source interface for managing products, with remote and local data sources.
 */
interface ProductDataSource {
    /**
     * Remote data source interface for fetching products from an external service.
     */
    interface Remote {
        suspend fun getProducts(): Result<List<ProductEntity>>
        suspend fun getProduct(productId: Int): Result<ProductEntity>
    }

    /**
     * Local data source interface for managing product data in database
     */
    interface Local {
        suspend fun getProducts(): Result<List<ProductEntity>>
        suspend fun getProduct(productId: Int): Result<ProductEntity>
        suspend fun saveProducts(products: List<ProductEntity>)
        suspend fun clearProducts()
    }
}
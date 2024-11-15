package com.lalit.clean.data.datasource

import com.lalit.clean.data.db.dao.ProductDao
import com.lalit.clean.data.mapper.toDomain
import com.lalit.clean.data.mapper.toProductDb
import com.lalit.clean.domain.entities.ProductEntity
import com.lalit.clean.domain.util.Result

/**
 * Implementation of [ProductDataSource.Local] for managing product data in local storage.
 *
 * @param productDao The [ProductDao] instance used to interact with the local product database.
 */
class ProductLocalDataSourceImpl(private val productDao: ProductDao) : ProductDataSource.Local {
    override suspend fun getProducts(): Result<List<ProductEntity>> {
        val products = productDao.getProducts()
        return if (products.isNotEmpty()) {
            Result.Success(products.toDomain())
        } else {
            Result.Error(Exception("Data Not Available"))
        }
    }

    override suspend fun getProduct(productId: Int): Result<ProductEntity> {
        val product = productDao.getProduct(productId)
        return if (product != null) {
            Result.Success(product.toDomain())
        } else {
            Result.Error(Exception("Data Not Available"))
        }
    }

    override suspend fun saveProducts(products: List<ProductEntity>) {
        productDao.saveProducts(products.toProductDb())
    }

    override suspend fun clearProducts() {
        productDao.clearAll()
    }
}
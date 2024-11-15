package com.lalit.clean.data.datasource

import com.lalit.clean.data.db.dao.CartDao
import com.lalit.clean.data.mapper.toCartDb
import com.lalit.clean.data.mapper.toDomain
import com.lalit.clean.domain.entities.ProductEntity
import com.lalit.clean.domain.util.Result

/**
 * Implementation of [ProductCartDataSource] using [CartDao] for local data operations.
 *
 * @param cartDao The [CartDao] instance used to interact with the local database.
 */
class ProductCartDataSourceImpl(private val cartDao: CartDao) : ProductCartDataSource {
    /**
     * Fetches the list of products in the cart from the local database.
     *
     * @return Result containing a list of [ProductEntity] on success, or an error on failure.
     */
    override suspend fun getCartProducts(): Result<List<ProductEntity>> {
        val products = cartDao.getCartProducts()
        return if (products.isNotEmpty()) {
            Result.Success(products.toDomain())
        } else {
            Result.Error(Exception("Cart is Empty, Please go to order details page to add product in the cart."))
        }
    }

    /**
     * Saves a product to the cart in the local database.
     *
     * @param product The [ProductEntity] to be saved in the cart.
     */
    override suspend fun saveCartProduct(product: ProductEntity) {
        cartDao.getCartProduct(product.id)?.let { existProduct ->
            cartDao.updateCartProduct(existProduct.productId, existProduct.quantity + 1)
        } ?: run {
            val cartDbEntity = product.toCartDb()
            cartDao.saveCartProduct(cartDbEntity)
        }
    }

    /**
     * Removes a product from the cart in the local database.
     *
     * @param productId The ID of the product to be removed.
     */
    override suspend fun removeCartProduct(productId: Int) {
        cartDao.removeCartProduct(productId)
    }
}
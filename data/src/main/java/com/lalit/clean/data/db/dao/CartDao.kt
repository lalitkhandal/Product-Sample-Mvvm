package com.lalit.clean.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lalit.clean.data.db.entities.CartDbEntity

/**
 * Data Access Object interface for managing cart-related database operations.
 */
@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCartProduct(product: CartDbEntity)

    @Query("UPDATE cart SET quantity = :quantity WHERE productId = :productId ")
    suspend fun updateCartProduct(productId: Int, quantity: Int)

    @Query("SELECT * FROM cart")
    suspend fun getCartProducts(): List<CartDbEntity>

    @Query("SELECT * FROM cart WHERE productId = :productId")
    suspend fun getCartProduct(productId: Int): CartDbEntity?

    @Query("DELETE FROM cart WHERE productId = :productId")
    suspend fun removeCartProduct(productId: Int)
}
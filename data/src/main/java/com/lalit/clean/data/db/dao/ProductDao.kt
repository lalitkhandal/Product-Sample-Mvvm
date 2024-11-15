package com.lalit.clean.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lalit.clean.data.db.entities.ProductDbEntity

/**
 * Data Access Object interface for managing product-related database operations.
 */
@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProducts(products: List<ProductDbEntity>)

    @Query("SELECT * FROM products ORDER BY category")
    suspend fun getProducts(): List<ProductDbEntity>

    @Query("SELECT * FROM products WHERE id = :productId")
    suspend fun getProduct(productId: Int): ProductDbEntity?

    @Query("DELETE FROM products")
    suspend fun clearAll()
}
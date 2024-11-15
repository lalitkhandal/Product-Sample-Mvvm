package com.lalit.clean.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lalit.clean.data.db.dao.CartDao
import com.lalit.clean.data.db.dao.ProductDao
import com.lalit.clean.data.db.entities.CartDbEntity
import com.lalit.clean.data.db.entities.ProductDbEntity

/**
 * Abstract class representing the Room database for the application.
 *
 * This class provides access to the DAOs for interacting with the product and cart databases.
 */
@Database(
    entities = [ProductDbEntity::class, CartDbEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    /**
     * Provides access to the [ProductDao] for managing product-related operations.
     *
     * @return The [ProductDao] instance.
     */
    abstract fun productDao(): ProductDao

    /**
     * Provides access to the [CartDao] for managing cart-related operations.
     *
     * @return The [CartDao] instance.
     */
    abstract fun cartDao(): CartDao
}
package com.lalit.clean.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class representing a product entity in the cart database.
 */
@Entity(tableName = "cart")
data class CartDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val productId: Int,
    val category: String,
    val description: String,
    val thumbnail: String,
    val price: Double,
    val title: String,
    var quantity: Int = 1
)
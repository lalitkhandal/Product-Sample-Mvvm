package com.lalit.clean.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class representing a product entity in the product database.
 */
@Entity(tableName = "products")
data class ProductDbEntity(
    @PrimaryKey val id: Int,
    val category: String,
    val description: String,
    val thumbnail: String,
    val price: Double,
    val title: String
)
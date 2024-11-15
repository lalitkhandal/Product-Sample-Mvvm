package com.lalit.clean.domain.entities

/**
 * Data class representing a product entity in the application.
 *
 * This class holds the raw data for a product entity.
 */

data class ProductEntity(
    val category: String,
    val description: String,
    val id: Int,
    val thumbnail: String,
    val price: Double,
    val title: String,
    val cartQuantity: Int = 0
)
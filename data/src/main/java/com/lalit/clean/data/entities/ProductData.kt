package com.lalit.clean.data.entities

/**
 * Data class representing a product entity in the application.
 *
 * This class holds the raw data for a product, typically used to map network responses.
 */

data class ProductData(
    val products: List<ProductDataItem>,
    val total: Int,
    val skip: Int,
    val limit: Int
)

data class ProductDataItem(
    val category: String,
    val description: String,
    val id: Int,
    val thumbnail: String,
    val price: Double,
    val rating: Double,
    val title: String
)


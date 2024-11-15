package com.lalit.clean.data.mapper

import com.lalit.clean.data.db.entities.CartDbEntity
import com.lalit.clean.data.db.entities.ProductDbEntity
import com.lalit.clean.domain.entities.ProductEntity

/**
 * A class responsible for mapping data between different layers of the application.
 */

fun List<ProductEntity>.toProductDb(): List<ProductDbEntity> =
    this.map { it.toProductDb() } // End of function

fun ProductEntity.toProductDb() = ProductDbEntity(
    category = category,
    description = description,
    id = id,
    thumbnail = thumbnail,
    price = price,
    title = title
) // End of function

fun ProductEntity.toCartDb() = CartDbEntity(
    category = category,
    description = description,
    productId = id,
    thumbnail = thumbnail,
    price = price,
    title = title
) // End of function
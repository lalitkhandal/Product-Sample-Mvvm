package com.lalit.clean.data.mapper

import com.lalit.clean.data.db.entities.CartDbEntity
import com.lalit.clean.data.db.entities.ProductDbEntity
import com.lalit.clean.data.entities.ProductData
import com.lalit.clean.data.entities.ProductDataItem
import com.lalit.clean.domain.entities.ProductEntity

/**
 * A class responsible for mapping data between different layers of the application.
 */

fun ProductData.toDomain(): List<ProductEntity> =
    this.products.map { it.toDomain() } // End of function

fun ProductDataItem.toDomain() = ProductEntity(
    category = category,
    description = description,
    id = id,
    thumbnail = thumbnail,
    price = price,
    title = title
) // End of function


fun List<ProductDbEntity>.toDomain(): List<ProductEntity> =
    this.map { it.toDomain() } // End of function

fun ProductDbEntity.toDomain() = ProductEntity(
    category = category,
    description = description,
    id = id,
    thumbnail = thumbnail,
    price = price,
    title = title
) // End of function


@JvmName("cartDbEntityToProductEntityList")
fun List<CartDbEntity>.toDomain(): List<ProductEntity> =
    this.map { it.toDomain() } // End of function

@JvmName("cartDbEntityToProductEntity")
fun CartDbEntity.toDomain() = ProductEntity(
    category = category,
    description = description,
    id = productId,
    thumbnail = thumbnail,
    price = price,
    title = title,
    cartQuantity = quantity
) // End of function

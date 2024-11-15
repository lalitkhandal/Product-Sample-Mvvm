package com.lalit.clean

import com.lalit.clean.domain.entities.ProductEntity
import kotlin.random.Random

val productEntityList = List(4) {
    ProductEntity(
        id = Random.nextInt(),
        title = "Product ${it + 1}",
        description = "Description ${it + 1}",
        category = "Category ${it + 1}",
        price = 10.0 * (it + 1),
        thumbnail = "thumb${it + 1}.jpg"
    )
}

val productEntity = ProductEntity(
    id = 1,
    title = "Test Product",
    description = "Test Description",
    category = "Test Category",
    price = 99.99,
    thumbnail = "https://example.com/thumbnail.jpg"
)
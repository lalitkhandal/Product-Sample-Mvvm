package com.lalit.clean.ui

import com.lalit.clean.domain.entities.ProductEntity
import kotlin.random.Random

val productEntityList = List(5) {
    ProductEntity(
        id = Random.nextInt(),
        title = "Product ${it + 1}",
        description = "Description ${it + 1}",
        category = "Category ${it + 1}",
        price = 10.0 * (it + 1),
        thumbnail = "thumb${it + 1}.jpg"
    )
}
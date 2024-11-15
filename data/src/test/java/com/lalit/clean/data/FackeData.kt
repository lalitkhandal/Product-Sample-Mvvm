package com.lalit.clean.data

import com.lalit.clean.data.entities.ProductData
import com.lalit.clean.data.entities.ProductDataItem
import com.lalit.clean.domain.entities.ProductEntity

val mockProductDataItem = ProductDataItem(
    id = 1,
    title = "Product 1",
    description = "Description 1",
    category = "Category 1",
    price = 10.99,
    thumbnail = "thumb_1.jpg",
    rating = 5.99
)

val mockProductEntity = ProductEntity(
    id = 1,
    title = "Product 1",
    description = "Description 1",
    category = "Category 1",
    price = 10.99,
    thumbnail = "thumb_1.jpg"
)

val mockProductData = ProductData(products = listOf(mockProductDataItem), 1, 0, 1)

val mockProductEntityList = listOf(mockProductEntity)
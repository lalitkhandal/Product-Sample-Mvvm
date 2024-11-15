package com.lalit.clean.data

import com.lalit.clean.data.entities.ProductData
import com.lalit.clean.data.entities.ProductDataItem
import com.lalit.clean.domain.entities.ProductEntity

val mockProduct = ProductDataItem(
    category = "category",
    description = "description",
    id = 1,
    thumbnail = "image",
    price = 10.0,
    rating = 5.0,
    title = "title",
)

val mockProductEntity = ProductEntity(
    category = "category",
    description = "description",
    id = 1,
    thumbnail = "image",
    price = 10.0,
    title = "title"
)

val mockProductData = ProductData(products = listOf(mockProduct), 1, 0, 1)

val mockProductEntityList = listOf(mockProductEntity)
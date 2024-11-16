package com.lalit.clean.data.mapper

import com.lalit.clean.data.db.entities.CartDbEntity
import com.lalit.clean.data.db.entities.ProductDbEntity
import com.lalit.clean.data.entities.ProductData
import com.lalit.clean.data.entities.ProductDataItem
import com.lalit.clean.data.mockProductDataItem
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit test class for ProductMapper, responsible for testing the mapping functionality
 */
class ProductMapperTest {
    // Test for `ProductData.toDomain()`
    @Test
    fun `test ProductData toDomain transformation`() {
        // Given
        val productData = ProductData(
            products = listOf(
                mockProductDataItem,
                ProductDataItem(
                    id = 2,
                    title = "Product 2",
                    description = "Description 2",
                    category = "Category 2",
                    price = 20.99,
                    thumbnail = "thumb_2.jpg",
                    rating = 5.99
                )
            ),
            1, 10, 1
        )

        // When
        val productEntities = productData.toDomain()

        // Then
        assertEquals(2, productEntities.size)
        assertEquals(1, productEntities[0].id)
        assertEquals("Product 1", productEntities[0].title)
        assertEquals("Description 1", productEntities[0].description)
        assertEquals("Category 1", productEntities[0].category)
        assertEquals(10.99, productEntities[0].price, 0.0001)
        assertEquals("thumb_1.jpg", productEntities[0].thumbnail)

        assertEquals(2, productEntities[1].id)
        assertEquals("Product 2", productEntities[1].title)
        assertEquals("Description 2", productEntities[1].description)
        assertEquals("Category 2", productEntities[1].category)
        assertEquals(20.99, productEntities[1].price, 0.0001)
        assertEquals("thumb_2.jpg", productEntities[1].thumbnail)
    }

    // Test for `ProductDbEntity.toDomain()`
    @Test
    fun `test ProductDbEntity toDomain transformation`() {
        // Given
        val productDbEntity = ProductDbEntity(
            id = 1,
            title = "Product 1",
            description = "Description 1",
            category = "Category 1",
            price = 10.99,
            thumbnail = "thumb_1.jpg"
        )

        // When
        val productEntity = productDbEntity.toDomain()

        // Then
        assertEquals(1, productEntity.id)
        assertEquals("Product 1", productEntity.title)
        assertEquals("Description 1", productEntity.description)
        assertEquals("Category 1", productEntity.category)
        assertEquals(10.99, productEntity.price, 0.0001)
        assertEquals("thumb_1.jpg", productEntity.thumbnail)
    }

    // Test for `List<ProductDbEntity>.toDomain()`
    @Test
    fun `test list of ProductDbEntity toDomain transformation`() {
        // Given
        val productDbEntities = listOf(
            ProductDbEntity(
                id = 1,
                title = "Product 1",
                description = "Description 1",
                category = "Category 1",
                price = 10.99,
                thumbnail = "thumb_1.jpg"
            ),
            ProductDbEntity(
                id = 2,
                title = "Product 2",
                description = "Description 2",
                category = "Category 2",
                price = 20.99,
                thumbnail = "thumb_2.jpg"
            )
        )

        // When
        val productEntities = productDbEntities.toDomain()

        // Then
        assertEquals(2, productEntities.size)
        assertEquals(1, productEntities[0].id)
        assertEquals("Product 1", productEntities[0].title)
        assertEquals("Description 1", productEntities[0].description)
        assertEquals("Category 1", productEntities[0].category)
        assertEquals(10.99, productEntities[0].price, 0.0001)
        assertEquals("thumb_1.jpg", productEntities[0].thumbnail)

        assertEquals(2, productEntities[1].id)
        assertEquals("Product 2", productEntities[1].title)
        assertEquals("Description 2", productEntities[1].description)
        assertEquals("Category 2", productEntities[1].category)
        assertEquals(20.99, productEntities[1].price, 0.0001)
        assertEquals("thumb_2.jpg", productEntities[1].thumbnail)
    }

    // Test for `CartDbEntity.toDomain()`
    @Test
    fun `test CartDbEntity toDomain transformation`() {
        // Given
        val cartDbEntity = CartDbEntity(
            productId = 1,
            title = "Product 1",
            description = "Description 1",
            category = "Category 1",
            price = 10.99,
            thumbnail = "thumb_1.jpg",
            quantity = 2
        )

        // When
        val productEntity = cartDbEntity.toDomain()

        // Then
        assertEquals(1, productEntity.id)
        assertEquals("Product 1", productEntity.title)
        assertEquals("Description 1", productEntity.description)
        assertEquals("Category 1", productEntity.category)
        assertEquals(10.99, productEntity.price, 0.0001)
        assertEquals("thumb_1.jpg", productEntity.thumbnail)
        assertEquals(2, productEntity.cartQuantity)
    }

    // Test for `List<CartDbEntity>.toDomain()`
    @Test
    fun `test list of CartDbEntity toDomain transformation`() {
        // Given
        val cartDbEntities = listOf(
            CartDbEntity(
                productId = 1,
                title = "Product 1",
                description = "Description 1",
                category = "Category 1",
                price = 10.99,
                thumbnail = "thumb_1.jpg",
                quantity = 2
            ),
            CartDbEntity(
                productId = 2,
                title = "Product 2",
                description = "Description 2",
                category = "Category 2",
                price = 20.99,
                thumbnail = "thumb_2.jpg",
                quantity = 1
            )
        )

        // When
        val productEntities = cartDbEntities.toDomain()

        // Then
        assertEquals(2, productEntities.size)

        assertEquals(1, productEntities[0].id)
        assertEquals("Product 1", productEntities[0].title)
        assertEquals("Description 1", productEntities[0].description)
        assertEquals("Category 1", productEntities[0].category)
        assertEquals(10.99, productEntities[0].price, 0.0001)
        assertEquals("thumb_1.jpg", productEntities[0].thumbnail)
        assertEquals(2, productEntities[0].cartQuantity)

        assertEquals(2, productEntities[1].id)
        assertEquals("Product 2", productEntities[1].title)
        assertEquals("Description 2", productEntities[1].description)
        assertEquals("Category 2", productEntities[1].category)
        assertEquals(20.99, productEntities[1].price, 0.0001)
        assertEquals("thumb_2.jpg", productEntities[1].thumbnail)
        assertEquals(1, productEntities[1].cartQuantity)
    }
}
package com.lalit.clean.data.mapper

import com.lalit.clean.domain.entities.ProductEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class ProductDbMapperTest {
    @Test
    fun `test toProductDb transformation`() {
        // Given
        val productEntity = ProductEntity(
            id = 1,
            title = "Test Product",
            description = "Test description",
            category = "Test Category",
            price = 19.99,
            thumbnail = "test_thumbnail.jpg"
        )

        // When
        val productDbEntity = productEntity.toProductDb()

        // Then
        assertEquals(1, productDbEntity.id)
        assertEquals("Test Product", productDbEntity.title)
        assertEquals("Test description", productDbEntity.description)
        assertEquals("Test Category", productDbEntity.category)
        // Use delta for floating-point comparison
        assertEquals(19.99, productDbEntity.price, 0.0001)
        assertEquals("test_thumbnail.jpg", productDbEntity.thumbnail)
    }

    @Test
    fun `test toCartDb transformation`() {
        // Given
        val productEntity = ProductEntity(
            id = 2,
            title = "Test Product",
            description = "Test description",
            category = "Test Category",
            price = 19.99,
            thumbnail = "test_thumbnail.jpg"
        )

        // When
        val cartDbEntity = productEntity.toCartDb()

        // Then
        assertEquals(2, cartDbEntity.productId)
        assertEquals("Test Product", cartDbEntity.title)
        assertEquals("Test description", cartDbEntity.description)
        assertEquals("Test Category", cartDbEntity.category)
        // Use delta for floating-point comparison
        assertEquals(19.99, cartDbEntity.price, 0.0001)
        assertEquals("test_thumbnail.jpg", cartDbEntity.thumbnail)
    }

    @Test
    fun `test toProductDb list transformation`() {
        // Given
        val productEntities = listOf(
            ProductEntity(
                id = 1,
                title = "Product 1",
                description = "Description 1",
                category = "Category 1",
                price = 10.99,
                thumbnail = "thumb_1.jpg"
            ),
            ProductEntity(
                id = 2,
                title = "Product 2",
                description = "Description 2",
                category = "Category 2",
                price = 20.99,
                thumbnail = "thumb_2.jpg"
            )
        )

        // When
        val productDbEntities = productEntities.toProductDb()

        // Then
        assertEquals(2, productDbEntities.size)
        assertEquals(1, productDbEntities[0].id)
        assertEquals("Product 1", productDbEntities[0].title)
        assertEquals("Description 1", productDbEntities[0].description)
        assertEquals("Category 1", productDbEntities[0].category)
        assertEquals(10.99, productDbEntities[0].price, 0.0001)  // Use delta
        assertEquals("thumb_1.jpg", productDbEntities[0].thumbnail)

        assertEquals(2, productDbEntities[1].id)
        assertEquals("Product 2", productDbEntities[1].title)
        assertEquals("Description 2", productDbEntities[1].description)
        assertEquals("Category 2", productDbEntities[1].category)
        assertEquals(20.99, productDbEntities[1].price, 0.0001)  // Use delta
        assertEquals("thumb_2.jpg", productDbEntities[1].thumbnail)
    }
}
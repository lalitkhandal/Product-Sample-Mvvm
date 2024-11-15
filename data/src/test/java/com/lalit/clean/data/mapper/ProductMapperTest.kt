package com.lalit.clean.data.mapper

import com.lalit.clean.data.mockProduct
import com.lalit.clean.data.mockProductData
import com.lalit.clean.data.mockProductEntity
import com.lalit.clean.data.mockProductEntityList
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit test class for [ProductMapper], responsible for testing the mapping functionality
 */
class ProductMapperTest {

    @Test
    fun `test Product List Mapper when List of data provide to returns valid list of ProductEntity`() {
        val given = mockProductData
        val expected = mockProductEntityList

        val result = given.toDomain()

        assertEquals(result, expected)
    }

    @Test
    fun `test Product Mapper when data provide to returns valid ProductEntity`() {
        val given = mockProduct
        val expected = mockProductEntity

        val result = given.toDomain()

        assertEquals(result, expected)
    }
}
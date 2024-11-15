package com.lalit.clean.data.repository

import com.lalit.clean.data.datasource.ProductCartDataSource
import com.lalit.clean.data.mockProductEntity
import com.lalit.clean.data.mockProductEntityList
import com.lalit.clean.domain.repository.ProductCartRepository
import com.lalit.clean.domain.util.Result
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

class ProductCartRepositoryImplTest {
    private lateinit var productCartDataSource: ProductCartDataSource
    private lateinit var productCartRepository: ProductCartRepository

    @Before
    fun setUp() {
        // Mock the data source
        productCartDataSource = mockk()
        // Create the repository instance with the mocked data source
        productCartRepository = ProductCartRepositoryImpl(productCartDataSource)
    }

    @Test
    fun `test getCartProducts returns products from data source`() = runTest {

        // Mock the behavior of getCartProducts
        coEvery { productCartDataSource.getCartProducts() } returns Result.Success(
            mockProductEntityList
        )

        // When
        val result = productCartRepository.getCartProducts()

        // Then
        assertTrue(result is Result.Success)
        assertEquals(mockProductEntityList, (result as Result.Success).data)
        coVerify { productCartDataSource.getCartProducts() }
    }

    @Test
    fun `test saveCartProduct calls data source saveCartProduct`() = runTest {
        // Given
        val product = mockProductEntity

        // Mock the behavior of saveCartProduct
        coJustRun { productCartDataSource.saveCartProduct(product) }

        // When
        productCartRepository.saveCartProduct(product)

        // Then
        coVerify { productCartDataSource.saveCartProduct(product) }
    }

    @Test
    fun `test removeCartProduct calls data source removeCartProduct`() = runTest {
        // Given
        val productId = Random.nextInt()
        // Mock the behavior of saveCartProduct
        coJustRun { productCartDataSource.removeCartProduct(productId) }

        // When
        productCartRepository.removeCartProduct(productId)

        // Then
        coVerify { productCartDataSource.removeCartProduct(productId) }
    }

    @Test
    fun `test getCartProducts returns failure when data source fails`() = runTest {
        // Given
        val expectedError = Exception("Data source error")

        // Mock the behavior of getCartProducts to return a failure
        coEvery { productCartDataSource.getCartProducts() } returns Result.Error(expectedError)

        // When
        val result = productCartRepository.getCartProducts()

        // Then
        assertTrue(result is Result.Error)
        assertEquals(expectedError, (result as Result.Error).error)
        coVerify { productCartDataSource.getCartProducts() }
    }
}
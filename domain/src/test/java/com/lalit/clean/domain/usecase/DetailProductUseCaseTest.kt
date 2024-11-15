package com.lalit.clean.domain.usecase

import com.lalit.clean.domain.entities.ProductEntity
import com.lalit.clean.domain.repository.ProductRepository
import com.lalit.clean.domain.util.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

class DetailProductUseCaseTest {
    private lateinit var productRepository: ProductRepository
    private lateinit var detailProductUseCase: DetailProductUseCase

    @Before
    fun setUp() {
        // Mock the ProductRepository
        productRepository = mockk()
        // Create the use case with the mocked repository
        detailProductUseCase = DetailProductUseCase(productRepository)
    }

    @Test
    fun `test invoke retrieves product successfully`() = runTest {
        // Given
        val productId = Random.nextInt()
        val expectedProduct = ProductEntity(
            id = productId,
            title = "Product Title",
            description = "Product Description",
            category = "Product Category",
            price = 99.99,
            thumbnail = "product_thumb.jpg"
        )

        // Mock the repository to return a successful result
        coEvery { productRepository.getProduct(productId) } returns Result.Success(expectedProduct)

        // When
        val result = detailProductUseCase.invoke(productId)

        // Then
        assertTrue(result is Result.Success)
        assertEquals(expectedProduct, (result as Result.Success).data)
        coVerify { productRepository.getProduct(productId) }
    }

    @Test
    fun `test invoke returns failure when product not found`() = runTest {
        // Given
        val productId = Random.nextInt()
        val expectedError = Exception("Product not found")

        // Mock the repository to return a failure result
        coEvery { productRepository.getProduct(productId) } returns Result.Error(expectedError)

        // When
        val result = detailProductUseCase.invoke(productId)

        // Then
        assertTrue(result is Result.Error)
        assertEquals(expectedError, (result as Result.Error).error)
        coVerify { productRepository.getProduct(productId) }
    }
}
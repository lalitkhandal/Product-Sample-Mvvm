package com.lalit.clean.domain.usecase

import com.lalit.clean.domain.entities.ProductEntity
import com.lalit.clean.domain.repository.ProductCartRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import com.lalit.clean.domain.util.Result
import io.mockk.coEvery
import io.mockk.coJustRun

class CartUseCaseTest {

    private lateinit var productCartRepository: ProductCartRepository
    private lateinit var cartUseCase: CartUseCase

    @Before
    fun setUp() {
        // Mock the ProductCartRepository
        productCartRepository = mockk()
        // Create the CartUseCase with the mocked repository
        cartUseCase = CartUseCase(productCartRepository)
    }

    @Test
    fun `test getCartProducts calls repository`() = runBlocking {
        // Given
        val expectedProducts = listOf(
            ProductEntity(
                id = 1,
                title = "Product 1",
                description = "Desc 1",
                category = "Cat 1",
                price = 10.0,
                thumbnail = "thumb1.jpg"
            ),
            ProductEntity(
                id = 2,
                title = "Product 2",
                description = "Desc 2",
                category = "Cat 2",
                price = 20.0,
                thumbnail = "thumb2.jpg"
            )
        )
        // Mock the repository to return a successful result
        coEvery { productCartRepository.getCartProducts() } returns Result.Success(expectedProducts)

        // When
        val result = cartUseCase.getCartProducts()

        // Then
        assertTrue(result is Result.Success)
        assertEquals(expectedProducts, (result as Result.Success).data)
        coVerify { productCartRepository.getCartProducts() }
    }

    @Test
    fun `test saveCartProduct calls repository`() = runBlocking {
        // Given
        val product = ProductEntity(
            id = 1,
            title = "Product 1",
            description = "Desc 1",
            category = "Cat 1",
            price = 10.0,
            thumbnail = "thumb1.jpg"
        )

        coJustRun { productCartRepository.saveCartProduct(product) }

        // When
        cartUseCase.saveCartProduct(product)

        // Then
        coVerify { productCartRepository.saveCartProduct(product) }
    }

    @Test
    fun `test removeCartProduct calls repository`() = runBlocking {
        // Given
        val productId = 1

        coJustRun { productCartRepository.removeCartProduct(productId) }

        // When
        cartUseCase.removeCartProduct(productId)

        // Then
        coVerify { productCartRepository.removeCartProduct(productId) }
    }

    @Test
    fun `test getCartProducts returns failure when repository fails`() = runBlocking {
        // Given
        val expectedError = Exception("Error fetching products")
        // Mock the repository to return a failure
        coEvery { productCartRepository.getCartProducts() } returns Result.Error(expectedError)

        // When
        val result = cartUseCase.getCartProducts()

        // Then
        assertTrue(result is Result.Error)
        assertEquals(expectedError, (result as Result.Error).error)
        coVerify { productCartRepository.getCartProducts() }
    }
}

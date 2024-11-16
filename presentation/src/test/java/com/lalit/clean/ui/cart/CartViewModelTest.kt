package com.lalit.clean.ui.cart

import com.lalit.clean.domain.entities.ProductEntity
import com.lalit.clean.domain.usecase.CartUseCase
import com.lalit.clean.domain.util.Result
import com.lalit.clean.sharedtest.CoroutineTestRule
import com.lalit.clean.ui.feed.state.ResultUiState
import com.lalit.clean.ui.productEntityList
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Unit test class for [CartViewModel]
 */
@ExperimentalCoroutinesApi
class CartViewModelTest {
    @get:Rule
    val testCoroutineRule = CoroutineTestRule()

    @MockK
    private lateinit var cartUseCase: CartUseCase
    private lateinit var cartViewModel: CartViewModel


    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        // Create the CartViewModel
        cartViewModel = CartViewModel(cartUseCase = cartUseCase)
    }

    @Test
    fun `test fetchCartData with success`() = runTest {
        // Given
        val productList = productEntityList
        val successResponse = Result.Success(productList)

        // Mock the use case to return the success response
        coEvery { cartUseCase.getCartProducts() } returns successResponse

        // When
        cartViewModel.fetchCartData()

        advanceUntilIdle()

        // Then
        assertTrue(cartViewModel.cartState.value is ResultUiState.Success)
        val successState = cartViewModel.cartState.value as ResultUiState.Success
        assertEquals(productList, successState.data)
        coVerify { cartUseCase.getCartProducts() }
    }

    @Test
    fun `test fetchCartData with error response`() = runTest {
        // Given
        val error = Exception("Error fetching cart data")
        val errorResponse = Result.Error<List<ProductEntity>>(error)

        // Mock the use case to return the error response
        coEvery { cartUseCase.getCartProducts() } returns errorResponse

        // When
        cartViewModel.fetchCartData()

        advanceUntilIdle()

        // Then
        assertTrue(cartViewModel.cartState.value is ResultUiState.Error)
        val errorState = cartViewModel.cartState.value as ResultUiState.Error
        assertEquals(error, errorState.exception)
        coVerify { cartUseCase.getCartProducts() }
    }

    @Test
    fun `test fetchCartData with empty cart`() = runTest {
        // Given
        val emptyCart = emptyList<ProductEntity>()
        val successResponse = Result.Success(emptyCart)

        // Mock the use case to return an empty list
        coEvery { cartUseCase.getCartProducts() } returns successResponse

        // When
        cartViewModel.fetchCartData()

        advanceUntilIdle()

        // Then
        assertTrue(cartViewModel.cartState.value is ResultUiState.Success)
        val successState = cartViewModel.cartState.value as ResultUiState.Success
        assertTrue((successState.data as List<*>).isEmpty())
        coVerify { cartUseCase.getCartProducts() }
    }

    @Test
    fun `test fetchCartData multiple times`() = runTest {
        // Given
        val productList = productEntityList
        val successResponse = Result.Success(productList)

        // Mock the use case to return the success response
        coEvery { cartUseCase.getCartProducts() } returns successResponse

        // When
        cartViewModel.fetchCartData()
        cartViewModel.fetchCartData() // Fetching again

        advanceUntilIdle()

        // Then
        assertTrue(cartViewModel.cartState.value is ResultUiState.Success)
        val successState = cartViewModel.cartState.value as ResultUiState.Success
        assertEquals(productList, successState.data)

        coVerify(exactly = 2) { cartUseCase.getCartProducts() } // Should have been called twice
    }
}

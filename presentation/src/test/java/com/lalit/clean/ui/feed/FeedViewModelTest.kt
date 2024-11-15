package com.lalit.clean.ui.feed

import com.lalit.clean.domain.entities.ProductEntity
import com.lalit.clean.domain.usecase.ProductUseCase
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

@ExperimentalCoroutinesApi
class FeedViewModelTest {
    @get:Rule
    val testCoroutineRule = CoroutineTestRule()

    @MockK
    private lateinit var productUseCase: ProductUseCase
    private lateinit var feedViewModel: FeedViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        // Create the ViewModel
        feedViewModel = FeedViewModel(productUseCase = productUseCase)
    }

    @Test
    fun `test fetchProducts with success`() = runTest {
        // Given
        val productList = productEntityList
        val successResponse = Result.Success(productList)

        // Mock the use case to return the success response
        coEvery { productUseCase.getProducts(any()) } returns successResponse

        // When
        feedViewModel.fetchProducts()

        advanceUntilIdle()

        // Then
        assertTrue(feedViewModel.resultUiState.value is ResultUiState.Success)
        val successState = feedViewModel.resultUiState.value as ResultUiState.Success
        assertEquals(productList, successState.data)
        coVerify { productUseCase.getProducts(true) }
    }

    @Test
    fun `test fetchProducts with error response`() = runTest {
        // Given
        val error = Exception("Error fetching products")
        val errorResponse = Result.Error<List<ProductEntity>>(error)

        // Mock the use case to return the error response
        coEvery { productUseCase.getProducts(any()) } returns errorResponse

        // When
        feedViewModel.fetchProducts()

        advanceUntilIdle()

        // Then
        assertTrue(feedViewModel.resultUiState.value is ResultUiState.Error)
        val errorState = feedViewModel.resultUiState.value as ResultUiState.Error
        assertEquals(error, errorState.exception)
        coVerify { productUseCase.getProducts(true) }
    }

    @Test
    fun `test fetchProducts with force refresh`() = runTest {
        // Given
        val productList = productEntityList
        val successResponse = Result.Success(productList)

        // Mock the use case to return the success response
        coEvery { productUseCase.getProducts(any()) } returns successResponse

        // When
        feedViewModel.fetchProducts(isForceRefresh = true)

        advanceUntilIdle()

        // Then
        assertTrue(feedViewModel.resultUiState.value is ResultUiState.Success)
        val successState = feedViewModel.resultUiState.value as ResultUiState.Success
        assertEquals(productList, successState.data)

        // Ensure that the fetchProducts method with the force refresh flag was called with `true`
        coVerify { productUseCase.getProducts(true) }
    }

    @Test
    fun `test fetchProducts with no force refresh`() = runTest {
        // Given
        val productList = productEntityList
        val successResponse = Result.Success(productList)

        // Mock the use case to return the success response
        coEvery { productUseCase.getProducts(any()) } returns successResponse

        // When
        feedViewModel.fetchProducts(isForceRefresh = false)

        advanceUntilIdle()

        // Then
        assertTrue(feedViewModel.resultUiState.value is ResultUiState.Success)
        val successState = feedViewModel.resultUiState.value as ResultUiState.Success
        assertEquals(productList, successState.data)

        // Ensure that the fetchProducts method was called with `true` (the default value for forceRefresh)
        coVerify { productUseCase.getProducts(true) }
    }
}

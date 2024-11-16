package com.lalit.clean.ui.feeddetails

import com.lalit.clean.domain.entities.ProductEntity
import com.lalit.clean.domain.usecase.CartUseCase
import com.lalit.clean.domain.usecase.DetailProductUseCase
import com.lalit.clean.domain.util.Result
import com.lalit.clean.sharedtest.CoroutineTestRule
import com.lalit.clean.ui.feed.state.ResultUiState
import com.lalit.clean.ui.productEntityList
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Exception

/**
 * Unit test class for [FeedDetailsViewModel]
 */
@ExperimentalCoroutinesApi
class FeedDetailsViewModelTest {
    @get:Rule
    val testCoroutineRule = CoroutineTestRule()

    // Mock dependencies
    @MockK
    private lateinit var detailProductUseCase: DetailProductUseCase

    @MockK
    private lateinit var cartUseCase: CartUseCase

    @MockK
    private lateinit var productDetailsBundle: ProductDetailsBundle

    private lateinit var feedDetailsViewModel: FeedDetailsViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        // Mock productDetailsBundle to return a productId
        every { productDetailsBundle.productId } returns 1

        // Create the ViewModel
        feedDetailsViewModel = FeedDetailsViewModel(
            detailProductUseCase = detailProductUseCase,
            cartUseCase = cartUseCase,
            productDetailsBundle = productDetailsBundle
        )
    }

    @Test
    fun `test fetchProduct handles successful response`() = runTest {
        // Given
        val product = productEntityList.first()
        val result = Result.Success(product)

        // Mock the use case to return the result
        coEvery { detailProductUseCase.invoke(1) } returns result

        // When
        feedDetailsViewModel.fetchProduct()

        advanceUntilIdle()

        // Then
        assertTrue(feedDetailsViewModel.resultUiState.value is ResultUiState.Success)
        val successState = feedDetailsViewModel.resultUiState.value as ResultUiState.Success
        assertEquals(product, successState.data)

        coVerify { detailProductUseCase.invoke(1) }
    }

    @Test
    fun `test fetchProduct handles error response`() = runTest {
        // Given
        val error = Exception("Error fetching product")
        val result = Result.Error<ProductEntity>(error)

        // Mock the use case to return the error
        coEvery { detailProductUseCase.invoke(1) } returns result

        // When
        feedDetailsViewModel.fetchProduct()

        advanceUntilIdle()

        // Then
        assertTrue(feedDetailsViewModel.resultUiState.value is ResultUiState.Error)
        val errorState = feedDetailsViewModel.resultUiState.value as ResultUiState.Error
        assertEquals(error, errorState.exception)

        coVerify { detailProductUseCase.invoke(1) }
    }

    @Test
    fun `test saveCartProduct successfully saves product`() = runTest {
        // Given
        val product = productEntityList.first()

        coJustRun { cartUseCase.saveCartProduct(product) }

        val onResponse: () -> Unit = mockk(relaxed = true)

        // When
        feedDetailsViewModel.saveCartProduct(product, onResponse)

        advanceUntilIdle()

        // Then
        coVerify { cartUseCase.saveCartProduct(product) }
        verify { onResponse() }
    }
}

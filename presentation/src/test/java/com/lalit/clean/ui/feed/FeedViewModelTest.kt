package com.lalit.clean.ui.feed

import com.lalit.clean.domain.entities.ProductEntity
import com.lalit.clean.domain.usecase.ProductUseCase
import com.lalit.clean.domain.util.Result
import com.lalit.clean.sharedtest.CoroutineTestRule
import com.lalit.clean.ui.feed.state.ResultUiState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
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
    fun setup() {
        MockKAnnotations.init(this)
        feedViewModel = FeedViewModel(productUseCase)
    }

    @Test
    fun `verify success with list of products when api request is successful`() = runTest {
        val expectedListOfProducts = listOf(productEntityData)
        coEvery { productUseCase.getProducts(any()) } returns Result.Success(data = expectedListOfProducts)

        feedViewModel.fetchProducts()
        val result = feedViewModel.resultUiState

        assertEquals(ResultUiState.Loading, result.value)

        advanceUntilIdle()

        assertEquals(
            productEntityData,
            ((feedViewModel.resultUiState.value as ResultUiState.Success).data as List<*>).first()
        )
    }

    @Test
    fun `verify error with exception when api request is unsuccessful`() = runTest {
        val error = Exception()
        coEvery { productUseCase.getProducts(any()) } returns Result.Error(error)

        feedViewModel.fetchProducts()
        val result = feedViewModel.resultUiState

        assertEquals(ResultUiState.Loading, result.value)

        advanceUntilIdle()

        assertEquals(error, (feedViewModel.resultUiState.value as ResultUiState.Error).exception)
    }

    val productEntityData: ProductEntity
        get() = ProductEntity(
            category = "category",
            description = "description",
            id = 1,
            thumbnail = "image",
            price = 10.0,
            title = "title"
        )
}
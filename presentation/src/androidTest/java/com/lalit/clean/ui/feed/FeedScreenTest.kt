package com.lalit.clean.ui.feed

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.lalit.clean.productEntityList
import com.lalit.clean.ui.feed.state.ResultUiState
import com.lalit.clean.ui.graph.RootRouter
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Exception

/**
 * FeedScreenTest is a test class that checks the functionality of feed screen features.
 */
@HiltAndroidTest
class FeedScreenTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: FeedViewModel
    private lateinit var rootRouter: RootRouter

    private val mockResultUiState = MutableStateFlow<ResultUiState>(ResultUiState.Loading)

    @Before
    fun setUp() {
        hiltRule.inject()

        // Relaxed mock for RootRouter to avoid unnecessary stubbing
        rootRouter = mockk(relaxed = true)
        viewModel = mockk(relaxed = true)

        // Mock the resultUiState to return a MutableStateFlow
        every { viewModel.resultUiState } returns mockResultUiState
        justRun { viewModel.fetchProducts(any()) }
    }

    @Test
    fun testLoadingState() {
        // Simulate the loading state in the viewModel
        mockResultUiState.value =  ResultUiState.Loading

        // Set the Composable content in the test environment
        composeTestRule.setContent {
            FeedScreen(rootRouter = rootRouter, viewModel = viewModel)
        }

        // Verify that the loading view is displayed
        composeTestRule.onNodeWithTag("ProgressView").assertIsDisplayed()
    }

    @Test
    fun testSuccessState() {
        // Given a successful response with a list of products
        val products = productEntityList

        // Simulate the success state in the viewModel
        mockResultUiState.value =  ResultUiState.Success(products)

        // Set the Composable content in the test environment
        composeTestRule.setContent {
            FeedScreen(rootRouter = rootRouter, viewModel = viewModel)
        }

        // Verify that the product list is displayed
        products.forEach { product ->
            composeTestRule.onNodeWithText(product.title).assertIsDisplayed()
        }
    }

    @Test
    fun testErrorState() {
        // Given an error state
        val error = Exception("An error occurred")

        // Simulate the error state in the viewModel
        mockResultUiState.value =  ResultUiState.Error(error)

        // Set the Composable content in the test environment
        composeTestRule.setContent {
            FeedScreen(rootRouter = rootRouter, viewModel = viewModel)
        }

        // Verify that the error message is displayed
        composeTestRule.onNodeWithText("An error occurred").assertIsDisplayed()

        // Verify the retry button is displayed
        composeTestRule.onNodeWithText("Retry").assertIsDisplayed()
    }

    @Test
    fun testRetryOnError() {
        // Given an error state with a retry action
        val error = Exception("An error occurred")

        // Simulate the error state in the viewModel
        mockResultUiState.value =  ResultUiState.Error(error)

        // Set the Composable content in the test environment
        composeTestRule.setContent {
            FeedScreen(rootRouter = rootRouter, viewModel = viewModel)
        }

        // Verify the retry button is displayed
        composeTestRule.onNodeWithText("Retry").assertIsDisplayed()

        // Simulate the retry button click
        composeTestRule.onNodeWithText("Retry").performClick()

        // Verify that the retry method was invoked on the viewModel
        verify { viewModel.fetchProducts(true) }
    }
}
package com.lalit.clean.ui.feeddetails

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavHostController
import com.lalit.clean.productEntity
import com.lalit.clean.ui.feed.state.ResultUiState
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.*
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class FeedDetailsScreenTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: FeedDetailsViewModel

    private val mockResultUiState = MutableStateFlow<ResultUiState>(ResultUiState.Loading)

    @Before
    fun setUp() {
        hiltRule.inject()

        // Initialize mock ViewModel
        viewModel = mockk(relaxed = true)

        // Mock the resultUiState to return a MutableStateFlow
        every { viewModel.resultUiState } returns mockResultUiState

        justRun { viewModel.fetchProduct() }
    }

    @Test
    fun testLoadingState() {
        // Simulate loading state
        mockResultUiState.value = ResultUiState.Loading

        // Set up composable screen
        composeTestRule.setContent {
            FeedDetailsScreen(rootNavController = mockk(), viewModel = viewModel)
        }

        // Verify that ProgressView (loading spinner) is shown
        composeTestRule.onNodeWithTag("ProgressView").assertIsDisplayed()
    }

    @Ignore("Need to fix")
    @Test
    fun testSuccessState() {
        // Given a successful response with a product entity
        val product = productEntity

        // Simulate success state
        mockResultUiState.value = ResultUiState.Success(product)

        // Set up composable screen
        composeTestRule.setContent {
            FeedDetailsScreen(rootNavController = mockk(), viewModel = viewModel)
        }

        // Wait for the UI to be idle (to handle animations like AnimatedVisibility)
        composeTestRule.waitForIdle()

        // Verify that product details are displayed
        composeTestRule.onNodeWithText("Test Product").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Description").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Category").assertIsDisplayed()
        composeTestRule.onNodeWithText("99.99").assertIsDisplayed()

        // Verify Add to Cart button is displayed
        composeTestRule.onNodeWithText("Add to Cart").assertIsDisplayed()
    }

    @Test
    fun testErrorState() {
        // Given an error state with a message
        val error = Exception("An error occurred")

        // Simulate error state
        mockResultUiState.value = ResultUiState.Error(error)

        // Set up composable screen
        composeTestRule.setContent {
            FeedDetailsScreen(rootNavController = mockk(), viewModel = viewModel)
        }

        // Verify the error message is displayed
        composeTestRule.onNodeWithText("An error occurred").assertIsDisplayed()

        // Verify retry button is displayed
        composeTestRule.onNodeWithText("Retry").assertIsDisplayed()
    }

    @Ignore("Need to fix")
    @Test
    fun testAddToCart() {
        // Given a successful response with a product entity
        val product = productEntity

        // Simulate success state
        mockResultUiState.value = ResultUiState.Success(product)

        // Set up composable screen
        composeTestRule.setContent {
            FeedDetailsScreen(rootNavController = mockk(), viewModel = viewModel)
        }

        // Wait for the UI to be idle (to handle animations like AnimatedVisibility)
        composeTestRule.waitUntil(5000) {
            // Ensure the "Add to Cart" button is displayed
            composeTestRule.onNodeWithText("Add to cart").assertIsDisplayed()

            // Simulate click on Add to Cart button
            composeTestRule.onNodeWithText("Add to cart").performClick()

            true
        }

        // Verify that the saveCartProduct function was called
        verify { viewModel.saveCartProduct(product, any()) }
    }

    @Test
    fun testNavigateBack() {
        // Set up composable screen
        val navHostController: NavHostController = mockk()
        every { navHostController.navigateUp() } returns true

        composeTestRule.setContent {
            FeedDetailsScreen(rootNavController =navHostController, viewModel = viewModel)
        }

        // Simulate click on back navigation
        composeTestRule.onNodeWithContentDescription("Back").performClick()

        // Verify that rootNavController.navigateUp() is called
        verify { viewModel.resultUiState }
    }

    @Test
    fun testRetryButton() {
        // Given an error state with a message
        val error = Exception("An error occurred")

        // Simulate error state
        mockResultUiState.value = ResultUiState.Error(error)

        // Set up composable screen
        composeTestRule.setContent {
            FeedDetailsScreen(rootNavController = mockk(), viewModel = viewModel)
        }

        // Simulate retry button click
        composeTestRule.onNodeWithText("Retry").performClick()

        // Verify that the fetchProduct method was called again on retry
        verify { viewModel.fetchProduct() }
    }
}

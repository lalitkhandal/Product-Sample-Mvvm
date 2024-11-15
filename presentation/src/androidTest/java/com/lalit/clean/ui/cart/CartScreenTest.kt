package com.lalit.clean.ui.cart

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.lalit.clean.productEntity
import com.lalit.clean.productEntityList
import com.lalit.clean.ui.feed.state.ResultUiState
import com.lalit.clean.ui.graph.RootRouter
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class CartScreenTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: CartViewModel

    private val mockResultUiState = MutableStateFlow<ResultUiState>(ResultUiState.Loading)


    @Before
    fun setup() {
        hiltRule.inject() // Inject the dependencies

        // Mocking CartUseCase
        viewModel = mockk(relaxed = true)

        // Mock the resultUiState to return a MutableStateFlow
        every { viewModel.cartState } returns mockResultUiState
        justRun { viewModel.fetchCartData() }
    }

    @Test
    fun testLoadingState() {
        // Set the cartState to Loading state
        mockResultUiState.value = ResultUiState.Loading

        // Setting up the composable
        composeTestRule.setContent {
            CartScreen(rootRouter = mockk(), viewModel = viewModel)
        }

        // Wait for the UI to settle
        composeTestRule.waitForIdle()

        // Check if ProgressView is shown
        composeTestRule.onNodeWithTag("ProgressView").assertIsDisplayed()
    }

    @Test
    fun testSuccessState() {
        val products = productEntityList

        // Set the cartState to Success state
        mockResultUiState.value = ResultUiState.Success(products)

        // Setting up the composable
        composeTestRule.setContent {
            CartScreen(rootRouter = mockk(), viewModel = viewModel)
        }

        // Wait for the UI to settle
        composeTestRule.waitForIdle()

        // Verify that the product list is displayed
        products.forEach { product ->
            composeTestRule.onNodeWithText(product.title).assertIsDisplayed()
            // Check if the product list is displayed
            composeTestRule.onNodeWithText(product.category).assertIsDisplayed()
        }

        // Check if the "Checkout" button is displayed
        composeTestRule.onNodeWithText("Checkout").assertIsDisplayed()

        // Simulate a click on the "Checkout" button
        composeTestRule.onNodeWithText("Checkout").performClick()
    }

    @Test
    fun testErrorState() {
        // Simulate an error response in the cartState
        val errorMessage = "Network error"
        mockResultUiState.value = ResultUiState.Error(Exception(errorMessage))

        // Setting up the composable
        composeTestRule.setContent {
            CartScreen(rootRouter = mockk(), viewModel = viewModel)
        }

        // Wait for the UI to settle
        composeTestRule.waitForIdle()

        // Check if the error message is displayed
        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }

    @Test
    fun testNavigateToProductDetails() {
        // Simulate success state with a list of products
        val products = listOf(productEntity)
        mockResultUiState.value = ResultUiState.Success(products)

        // Mock RootRouter for navigation
        val rootRouter: RootRouter = mockk()
        every { rootRouter.navigateToFeedDetails(any()) } just Runs

        // Set the composable content
        composeTestRule.setContent {
            CartScreen(rootRouter = rootRouter, viewModel = viewModel)
        }

        // Wait for the UI to settle
        composeTestRule.waitForIdle()

        // Simulate clicking on the product
        composeTestRule.onNodeWithText("Test Product").performClick()

        // Verify navigation was triggered
        verify { rootRouter.navigateToFeedDetails(1) }
    }
}

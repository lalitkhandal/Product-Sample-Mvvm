package com.lalit.clean.ui.feed

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import com.lalit.clean.domain.entities.ProductEntity
import com.lalit.clean.domain.repository.ProductRepository
import com.lalit.clean.domain.usecase.ProductUseCase
import com.lalit.clean.domain.util.Result
import com.lalit.clean.ui.feed.state.ResultUiState
import com.lalit.clean.ui.graph.RootRouter
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Exception

/**
 * FeedScreenTest is a test class that checks the functionality of feed screen features.
 */
@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class FeedScreenTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun testFeedScreen_DisplayedCorrectly() = runTest {
        val expectedListOfProducts = listOf(productEntityData, productEntityData)

        val productRepository = productRepository(expectedListOfProducts)

        initFeedScreen(productRepository)

        advanceUntilIdle()
        advanceTimeBy(1000)

        composeTestRule.onAllNodesWithText("category")[0].assertIsDisplayed()
        composeTestRule.onAllNodesWithText("category")[1].assertIsDisplayed()
        composeTestRule.onAllNodesWithText("title")[0].assertIsDisplayed()
        composeTestRule.onAllNodesWithText("title")[1].assertIsDisplayed()
    }


    @Test
    fun testFeedScreen_LoadingState() = runTest {
        val expectedListOfProducts = listOf(productEntityData)

        val productRepository = productRepository(expectedListOfProducts)
        val viewModel = FeedViewModel(ProductUseCase(productRepository))
        composeTestRule.setContent {
            FeedScreen(
                rootRouter = RootRouter(mainNavController = rememberNavController()),
                viewModel = viewModel
            )
        }
        viewModel.initProductState(ResultUiState.Loading)

        advanceUntilIdle()
        advanceTimeBy(1000)

        // Check for the existence of a loading indicator
        composeTestRule.onNodeWithTag("ProgressView").assertIsDisplayed()
    }

    @Test
    fun testFeedScreen_ErrorState() = runTest {
        val errorMessage = "Network Error"

        val productRepository = object : ProductRepository {
            override suspend fun getProducts(isForceRefresh: Boolean): Result<List<ProductEntity>> {
                return Result.Error(Exception(errorMessage))
            }

            override suspend fun getProduct(productId: Int): Result<ProductEntity> {
                return Result.Error(Exception(errorMessage))
            }

        }

        initFeedScreen(productRepository)

        advanceUntilIdle()
        advanceTimeBy(1000)
        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }

    private fun initFeedScreen(productRepository: ProductRepository) {
        val viewModel = FeedViewModel(ProductUseCase(productRepository))
        composeTestRule.setContent {
            FeedScreen(
                rootRouter = RootRouter(mainNavController = rememberNavController()),
                viewModel = viewModel
            )
        }
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

    private fun productRepository(expectedListOfProducts: List<ProductEntity>): ProductRepository {
        val productRepository = object : ProductRepository {
            override suspend fun getProducts(isForceRefresh: Boolean): Result<List<ProductEntity>> {
                return Result.Success(expectedListOfProducts)
            }

            override suspend fun getProduct(productId: Int): Result<ProductEntity> {
                return Result.Success(expectedListOfProducts.first())
            }

        }
        return productRepository
    }

}
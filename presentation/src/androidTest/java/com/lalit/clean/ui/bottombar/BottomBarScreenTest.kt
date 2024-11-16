package com.lalit.clean.ui.bottombar

import androidx.compose.material3.Text
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BottomBarScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testBottomBarScreenDisplaysTopBarTitle() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            BottomBarScreen(nestedNavController = navController) {}
        }

        // Assert that the title in the TopBar is displayed
        composeTestRule.onNodeWithText("Product Sample").assertIsDisplayed()
    }

    @Test
    fun testBottomBarScreenNavigation() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            BottomBarScreen(nestedNavController = navController) {}
        }

        composeTestRule.waitForIdle() // Ensures navigation has completed

        // Verify that each bottom bar item is displayed
        val items = BottomBarUiState().bottomItems
        items.forEach { item ->
            val itemName = item.tabName
            composeTestRule.onNodeWithText(itemName).assertExists()
        }
    }

    @Test
    fun testBottomBarScreenDisplaysContent() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            BottomBarScreen(nestedNavController = navController) {
                Text("Test Content is displaying")
            }
        }

        // Assert that the custom content is displayed
        composeTestRule.onNodeWithText("Test Content is displaying").assertIsDisplayed()
    }
}

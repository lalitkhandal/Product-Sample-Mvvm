package com.lalit.clean.ui.graph

import androidx.navigation.NavHostController
import com.lalit.clean.ui.navigation.NavScreen

/**
 * A class responsible for managing root-level navigation in the app.
 *
 * @param mainNavController The [NavHostController] used to control navigation within the app.
 */
class RootRouter(private val mainNavController: NavHostController) {

    fun navigateToFeedDetails(productId: Int) {
        mainNavController.navigate(NavScreen.FeedDetails(productId))
    }
}
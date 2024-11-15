package com.lalit.clean.ui.graph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.lalit.clean.ui.bottombar.BottomBarScreen
import com.lalit.clean.ui.bottombar.NavigationBarNestedGraph
import com.lalit.clean.ui.feeddetails.FeedDetailsScreen
import com.lalit.clean.ui.feeddetails.FeedDetailsViewModel
import com.lalit.clean.ui.navigation.Graph
import com.lalit.clean.ui.navigation.NavScreen
import com.lalit.clean.util.composableWithSlideAnimation

/**
 * A composable function to set up the root navigation graph.
 *
 * @param navHostController The [NavHostController] that manages navigation within the app.
 */
@Composable
fun RootNavigationGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        route = Graph.Root::class,
        startDestination = NavScreen.BottomBar
    ) {
        // Bottom bar with Feed details scree as a default one
        composableWithSlideAnimation<NavScreen.BottomBar> { backStack ->
            val nestedNavController = rememberNavController()
            BottomBarScreen(rootRouter = RootRouter(navHostController), nestedNavController) {
                NavigationBarNestedGraph(
                    navController = nestedNavController,
                    mainNavController = navHostController,
                    parentRoute = Graph.Root::class
                )
            }
        }

        // Feed details screen
        composableWithSlideAnimation<NavScreen.FeedDetails> { backStack ->
            val viewModel = hiltViewModel<FeedDetailsViewModel>()
            FeedDetailsScreen(rootNavController = navHostController, viewModel)
        }
    }
} // End of function


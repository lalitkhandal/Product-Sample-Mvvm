package com.lalit.clean.ui.bottombar

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.lalit.clean.ui.cart.CartScreen
import com.lalit.clean.ui.cart.CartViewModel
import com.lalit.clean.ui.feed.FeedScreen
import com.lalit.clean.ui.feed.FeedViewModel
import com.lalit.clean.ui.graph.RootRouter
import com.lalit.clean.ui.navigation.NavScreen
import com.lalit.clean.util.composableWithSlideAnimation
import kotlin.reflect.KClass

/**
 * Sets up a nested navigation graph for a bottom navigation bar or a similar navigation system.
 *
 * @param navController The [NavHostController] that controls the navigation of the nested graph.
 * @param mainNavController The main [NavHostController] that controls the overall navigation.
 * @param parentRoute The parent route that is used to create a nested navigation graph.
 */
@Composable
fun NavigationBarNestedGraph(
    navController: NavHostController,
    mainNavController: NavHostController,
    parentRoute: KClass<*>?
) {
    NavHost(
        navController = navController,
        startDestination = NavScreen.Feed,
        route = parentRoute
    ) {
        composableWithSlideAnimation<NavScreen.Feed> { backStack ->
            val viewModel = hiltViewModel<FeedViewModel>()
            FeedScreen(
                rootRouter = RootRouter(mainNavController),
                viewModel = viewModel,
            )
        }
        composableWithSlideAnimation<NavScreen.Cart> { backStack ->
            val viewModel = hiltViewModel<CartViewModel>()
            CartScreen(
                rootRouter = RootRouter(mainNavController),
                viewModel = viewModel,
            )
        }
    }
} // End of function
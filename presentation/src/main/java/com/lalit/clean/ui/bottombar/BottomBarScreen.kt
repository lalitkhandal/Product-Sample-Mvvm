package com.lalit.clean.ui.bottombar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.lalit.clean.R
import com.lalit.clean.ui.component.BottomBar
import com.lalit.clean.ui.component.TopBar
import com.lalit.clean.ui.navigation.route

/**
 * Sets up a screen with a bottom navigation bar.
 *
 * @param nestedNavController The [NavHostController] that handles the navigation for the nested screen content.
 * @param content A composable function representing the content of the screen. It will be displayed inside the screen layout.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBarScreen(
    nestedNavController: NavHostController,
    content: @Composable () -> Unit
) {
    val uiState = BottomBarUiState()

    Scaffold(
        topBar = {
            TopBar(title = stringResource(R.string.app_name), isNavigationIcon = false)
        },
        bottomBar = {
            BottomBar(
                items = uiState.bottomItems,
                navController = nestedNavController,
                onItemClick = { bottomItem ->
                    val clickedPageRoute = bottomItem.navScreen.route()
                    val notSamePage =
                        nestedNavController.currentDestination?.route != clickedPageRoute
                    if (notSamePage) {
                        nestedNavController.navigate(clickedPageRoute) {
                            launchSingleTop = true
                            popUpTo(nestedNavController.graph.findStartDestination().id)
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            content()
        }
    }
} // End of function
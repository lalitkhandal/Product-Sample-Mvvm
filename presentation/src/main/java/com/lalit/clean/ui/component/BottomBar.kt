package com.lalit.clean.ui.component

import android.content.res.Configuration
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lalit.clean.ui.bottombar.BottomBarItem
import com.lalit.clean.ui.navigation.route
import com.lalit.clean.ui.theme.AppTheme
import com.lalit.clean.ui.theme.Dimension

/**
 * Composable function to display a bottom navigation bar.
 *
 * @param items A list of [BottomBarItem] that defines the items in the bottom navigation bar.
 * @param navController The [NavController] used to control navigation within the app and provide
 *                      the back state entry
 * @param onItemClick A callback function that is invoked when a bottom navigation item is clicked.
 */
@Composable
fun BottomBar(
    items: List<BottomBarItem>,
    navController: NavController,
    onItemClick: (BottomBarItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    NavigationBar(tonalElevation = Dimension.Elevation.default) {
        items.forEach { item ->
            val selected = item.navScreen.route() == backStackEntry.value?.destination?.route
            NavigationBarItem(
                selected = selected,
                onClick = { onItemClick(item) },
                icon = {
                    Icon(imageVector = item.imageVector, contentDescription = null)
                },
                label = {
                    Text(text = item.tabName)
                }
            )
        }
    }
} // End of function

@Preview(name = "Light")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun BottomNavigationBarViewPreview() {
    AppTheme {
        BottomBar(listOf(BottomBarItem.Feed, BottomBarItem.Info), rememberNavController()) {}
    }
}
package com.lalit.clean.ui.bottombar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.lalit.clean.ui.navigation.NavScreen

data class BottomBarUiState(
    val bottomItems: List<BottomBarItem> = listOf(BottomBarItem.Feed, BottomBarItem.Info)
)

sealed class BottomBarItem(
    val tabName: String,
    val imageVector: ImageVector,
    val navScreen: NavScreen,
) {
    data object Feed : BottomBarItem("FEED", imageVector = Icons.Default.Home, NavScreen.Feed)
    data object Info :
        BottomBarItem("CART", imageVector = Icons.Default.ShoppingCart, NavScreen.Cart)
}

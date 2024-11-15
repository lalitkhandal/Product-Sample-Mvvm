package com.lalit.clean.ui.navigation

import kotlinx.serialization.Serializable

/**
 * Represents the different navigation graphs in the app.
 *
 * This sealed class is used to define distinct sections or navigation graphs within the app,
 * such as the root graph or other top-level navigation structures.
 */
sealed class Graph {
    @Serializable
    data object Root : Graph()
}

/**
 * Represents the different screens or destinations within the app's navigation structure.
 */
sealed class NavScreen {
    @Serializable
    data object BottomBar : NavScreen()

    @Serializable
    data object Feed : NavScreen()

    @Serializable
    data object Cart : NavScreen()

    @Serializable
    data class FeedDetails(val productId: Int) : NavScreen()
}

/**
 * Extension function to get the route (canonical name) for a navigation screen.
 *
 * @return The canonical name of the [NavScreen] as a string.
 */
fun NavScreen.route(): String {
    return this.javaClass.canonicalName
}
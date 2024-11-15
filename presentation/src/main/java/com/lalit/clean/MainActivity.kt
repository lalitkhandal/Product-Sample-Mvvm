package com.lalit.clean

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.lalit.clean.ui.graph.RootNavigationGraph
import com.lalit.clean.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 *
 * This activity is annotated with [@AndroidEntryPoint], which allows Hilt to perform dependency injection
 * on the `MainActivity` and its associated view models or other injectable components.
 *
 * By extending [ComponentActivity], this activity is used to set up the core UI components, including
 * themes, navigation graphs, and other essential app-level configurations.
 *
 * @see AppTheme for setting up the app's theme.
 * @see RootNavigationGraph for setting up the navigation graph.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            /**
             * The top-level composable that sets up the app's theme and initializes the navigation system.
             *
             * This composable function wraps the root content of the app in the `AppTheme` composable,
             * ensuring that the correct theme (light or dark) is applied based on the system settings or user preferences.
             * It also initializes the navigation controller using [rememberNavController] and passes it
             * to [RootNavigationGraph] to manage app navigation.
             */
            AppTheme {
                val navController = rememberNavController()
                RootNavigationGraph(navController)
            }
        }
    }
}
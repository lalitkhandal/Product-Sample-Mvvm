package com.lalit.clean.ui.component

import android.os.SystemClock
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

/**
 * A Composable function that adds a debounce effect to the provided `onClick` lambda.
 * This ensures that the `onClick` action is executed at most once within the specified debounce interval.
 *
 * @param onClick The lambda function to be executed when the user interacts with the UI element
 * @param debounceTime The amount of time (in milliseconds) to wait before allowing another invocation
 *                     of the `onClick` lambda. The default value is 1000 milliseconds (1 second).
 *
 * @return A lambda that can be passed to UI elements to handle clicks with debouncing.
 *
 * @sample
 * // Usage example within a Composable function:
 * val debouncedClick = debounced(onClick = {
 *     // Handle the click event (this will be triggered at most once every second)
 *     Log.d("Debounced", "Button clicked!")
 * })
 *
 * Button(onClick = debouncedClick) {
 *     Text("Click me")
 * }
 */
@Composable
fun debounced(
    onClick: () -> Unit,  // No need for 'inline' or 'noinline'
    debounceTime: Long = 1000L  // Default debounce time of 1000 milliseconds
): () -> Unit {
    // Remember the last time the button was clicked (state management)
    val lastTimeClicked = remember { mutableLongStateOf(0L) }

    // 'rememberUpdatedState' ensures we always use the latest onClick lambda
    val onClickLambda = rememberUpdatedState(onClick)

    return {
        val now = SystemClock.uptimeMillis()  // Get the current system time in milliseconds
        // If enough time has passed since the last click, invoke the onClick action
        if (now - lastTimeClicked.longValue > debounceTime) {
            onClickLambda.value()  // Execute the latest onClick function
        }
        lastTimeClicked.longValue = now  // Update the last clicked time to the current time
    }
}


/**
 * Extension function for `Modifier` that adds a debounced clickable modifier.
 *
 * @param debounceTime The time interval (in milliseconds) to wait before allowing another
 *                     click to trigger the `onClick` action. Default value is 1000 ms (1 second).
 * @param onClick The lambda to be executed when the Composable is clicked. This is invoked only
 *                if the debounce interval has passed.
 *
 * @return A `Modifier` that can be applied to clickable elements to prevent multiple rapid clicks.
 *
 * @sample
 * Modifier.debouncedClickable(debounceTime = 500L, onClick = {
 *     // Handle click action, only triggered once every 500 ms
 * })
 */
fun Modifier.debouncedClickable(
    debounceTime: Long = 1000L,
    onClick: () -> Unit
): Modifier {
    return this.composed {
        // Create a debounced click handler
        val debouncedClick = debounced(debounceTime = debounceTime, onClick = onClick)

        // Attach the debounced click handler to the clickable modifier
        this.clickable { debouncedClick() }
    }
}

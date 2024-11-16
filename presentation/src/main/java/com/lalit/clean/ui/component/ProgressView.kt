package com.lalit.clean.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.lalit.clean.ui.theme.AppTheme

/**
 * A composable function that displays a circular progress indicator (spinner) to show that an operation is in progress.
 *
 * @param modifier A [Modifier] to customize the appearance and layout of the progress indicator (default is [Modifier]).
 * @param alignment The alignment of the progress indicator within its parent container (default is [Alignment.Center]).
 */
@Composable
fun ProgressView(modifier: Modifier = Modifier, alignment: Alignment = Alignment.Center) {
    Box(modifier = modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(alignment)
                .testTag("ProgressView")
        )
    }
} // End of function

@Composable
@Preview(heightDp = 100)
private fun ProgressViewPreview() {
    AppTheme {
        ProgressView()
    }
}

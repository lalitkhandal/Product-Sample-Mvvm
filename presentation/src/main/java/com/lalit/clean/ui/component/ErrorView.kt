package com.lalit.clean.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.lalit.clean.R
import com.lalit.clean.ui.theme.AppTheme
import com.lalit.clean.ui.theme.Dimension

/**
 * A Composable function that displays an error message when no match or condition is found.
 *
 * @param modifier The modifier to be applied to the outermost container of the error view.
 * @param title The title of the error message, passed as a string resource ID.
 * @param message An optional custom message to display beneath the title.
 */
@Composable
internal fun ErrorNoMatch(
    modifier: Modifier = Modifier,
    title: Int = R.string.uh_oh,
    message: String? = null
) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(title),
                color = AppTheme.colorScheme.onSurfaceVariant,
                style = AppTheme.typography.titleLarge
            )
            Text(
                modifier = Modifier.padding(top = Dimension.Padding.padding8),
                text = message ?: stringResource(R.string.no_matching_results_found),
                color = AppTheme.colorScheme.onSurfaceVariant,
                style = AppTheme.typography.titleSmall
            )
        }
        Image(
            modifier = Modifier.padding(start = Dimension.Padding.paddingStart),
            painter = painterResource(id = R.drawable.ic_no_match),
            contentDescription = null,
        )
    }
} // End of function


/**
 * A Composable function that displays an error message and provides a retry action.
 *
 * @param modifier The modifier to be applied to the outermost container of the view.
 * @param errorMessage The error message to be displayed to the user.
 * @param onRetry A lambda function that gets invoked when the retry button is clicked.
 */
@Composable
internal fun ErrorFullView(
    modifier: Modifier = Modifier,
    errorMessage: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimension.Padding.defaultPadding),
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            painter = painterResource(id = R.drawable.ic_full_error),
            contentDescription = null,
        )

        Text(
            modifier = Modifier
                .padding(vertical = Dimension.Padding.padding40)
                .align(Alignment.CenterHorizontally),
            text = errorMessage,
            color = AppTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Button(
            onClick = onRetry, modifier = Modifier
                .defaultMinSize(minWidth = Dimension.Size.size180)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(stringResource(R.string.retry), color = Color.White)
        }
    }
} // End of function

@Composable
@Preview(heightDp = 100)
private fun ErrorNoMatchPreview() {
    AppTheme {
        ErrorNoMatch()
    }
}

@Composable
@Preview
private fun ErrorFullViewPreview() {
    AppTheme {
        ErrorFullView(errorMessage = "A Error message", onRetry = {})
    }
}
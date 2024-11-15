package com.lalit.clean.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.lalit.clean.R
import com.lalit.clean.ui.theme.AppTheme
import com.lalit.clean.ui.theme.Dimension


/**
 * A composable function that creates a top app bar with a title, optional navigation icon,
 * and custom actions.
 *
 * @param modifier The [Modifier] to be applied to the top app bar for customization.
 * @param title The string resource ID for the title text displayed in the top app bar.
 * @param contentColor The color of the content in the top app bar. Default is the primary color.
 * @param isNavigationIcon A flag indicating whether to show the navigation icon. Default is `true`.
 * @param onNavigationIconClick A callback function triggered when the navigation icon is clicked. Default is an empty lambda.
 * @param actions A composable lambda that allows custom actions to be added to the top app bar.
 * @param colors The colors to be used for the top app bar, such as background and content colors. Default uses the app's theme.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TopBar(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    contentColor: Color = AppTheme.colorScheme.primary,
    isNavigationIcon: Boolean = true,
    onNavigationIconClick: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors()
) {
    TopBar(
        modifier = modifier,
        title = stringResource(id = title),
        contentColor = contentColor,
        isNavigationIcon = isNavigationIcon,
        onNavigationIconClick = onNavigationIconClick,
        actions = actions,
        colors = colors
    )
} // End of function

/**
 * A composable function that creates a top app bar with a title, optional navigation icon,
 * and custom actions.
 *
 * @param modifier The [Modifier] to be applied to the top app bar for customization.
 * @param title The string the title text displayed in the top app bar.
 * @param contentColor The color of the content in the top app bar. Default is the primary color.
 * @param isNavigationIcon A flag indicating whether to show the navigation icon. Default is `true`.
 * @param onNavigationIconClick A callback function triggered when the navigation icon is clicked. Default is an empty lambda.
 * @param actions A composable lambda that allows custom actions to be added to the top app bar.
 * @param colors The colors to be used for the top app bar, such as background and content colors. Default uses the app's theme.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TopBar(
    modifier: Modifier = Modifier,
    title: String,
    contentColor: Color = AppTheme.colorScheme.primary,
    isNavigationIcon: Boolean = true,
    onNavigationIconClick: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = AppTheme.typography.titleMedium,
                color = contentColor,
                maxLines = 1,
                overflow = TextOverflow.Companion.Ellipsis
            )
        },
        navigationIcon = {
            if (isNavigationIcon) {
                IconButton(
                    onClick = onNavigationIconClick,
                    modifier = modifier,
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = "Back",
                        tint = contentColor
                    )
                }

            }
        },
        actions = actions,
        modifier = modifier.shadow(elevation = Dimension.Elevation.default),
        colors = colors
    )
} // End of function

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
private fun FoodTopBarPreview() {
    AppTheme {
        TopBar(
            title = "NAME",
            onNavigationIconClick = {},
            actions = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
private fun FoodTopBarPreview1() {
    AppTheme {
        TopBar(
            title = stringResource(R.string.app_name),
            onNavigationIconClick = {},
            actions = {}
        )
    }
}
package cz.chm4.chmtestapp.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A set of spacing constants designed to centralize all spacing values in one place
 */
data class Spacing(
    val default: Dp = 0.dp,
    val xxSmall: Dp = 4.dp,
    val xSmall: Dp = 8.dp,
    val small: Dp = 12.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 24.dp,
    val xLarge: Dp = 32.dp,
    val xxLarge: Dp = 48.dp
)

/**
 * A composition local key for spacing and passing a factory function
 * for creating a default value for that key
 */
val LocalSpacing = compositionLocalOf { Spacing() }

/**
 * Extension of the MaterialTheme providing LocalSpacing serving only as a syntactic sugar
 */
val MaterialTheme.spacing: Spacing
    @Composable
    get() = LocalSpacing.current
package com.shivam_raj.noteapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

@Immutable
data class ExtendedColorScheme(
    val highPriorityNoteColor: ColorFamily,
    val lowPriorityNoteColor: ColorFamily,
    val mediumPriorityNoteColor: ColorFamily,
    val veryLowPriorityNoteColor: ColorFamily,
    val customColor1: ColorFamily,
    val customColor2: ColorFamily,
    val customColor3: ColorFamily,
    val customColor4: ColorFamily,
    val customColor5: ColorFamily,
    val customColor6: ColorFamily,
    val customColor7: ColorFamily,
    val customColor8: ColorFamily,
    val customColor9: ColorFamily,
    val customColor10: ColorFamily,
)

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

val extendedLight = ExtendedColorScheme(
    highPriorityNoteColor = ColorFamily(
        highPriorityNoteColorLight,
        onHighPriorityNoteColorLight,
        highPriorityNoteColorContainerLight,
        onHighPriorityNoteColorContainerLight,
    ),
    lowPriorityNoteColor = ColorFamily(
        lowPriorityNoteColorLight,
        onLowPriorityNoteColorLight,
        lowPriorityNoteColorContainerLight,
        onLowPriorityNoteColorContainerLight,
    ),
    mediumPriorityNoteColor = ColorFamily(
        mediumPriorityNoteColorLight,
        onMediumPriorityNoteColorLight,
        mediumPriorityNoteColorContainerLight,
        onMediumPriorityNoteColorContainerLight,
    ),
    veryLowPriorityNoteColor = ColorFamily(
        veryLowPriorityColorLight,
        onVeryLowPriorityColorLight,
        veryLowPriorityColorContainerLight,
        onVeryLowPriorityColorContainerLight,
    ),
    customColor1 = ColorFamily(
        customColor1Light,
        onCustomColor1Light,
        customColor1ContainerLight,
        onCustomColor1ContainerLight
    ),
    customColor2 = ColorFamily(
        customColor2Light,
        onCustomColor2Light,
        customColor2ContainerLight,
        onCustomColor2ContainerLight,
    ),
    customColor3 = ColorFamily(
        customColor3Light,
        onCustomColor3Light,
        customColor3ContainerLight,
        onCustomColor3ContainerLight,
    ),
    customColor4 = ColorFamily(
        customColor4Light,
        onCustomColor4Light,
        customColor4ContainerLight,
        onCustomColor4ContainerLight,
    ),
    customColor5 = ColorFamily(
        customColor5Light,
        onCustomColor5Light,
        customColor5ContainerLight,
        onCustomColor5ContainerLight,
    ),
    customColor6 = ColorFamily(
        customColor6Light,
        onCustomColor6Light,
        customColor6ContainerLight,
        onCustomColor6ContainerLight,
    ),
    customColor7 = ColorFamily(
        customColor7Light,
        onCustomColor7Light,
        customColor7ContainerLight,
        onCustomColor7ContainerLight,
    ),
    customColor8 = ColorFamily(
        customColor8Light,
        onCustomColor8Light,
        customColor8ContainerLight,
        onCustomColor8ContainerLight,
    ),
    customColor9 = ColorFamily(
        customColor9Light,
        onCustomColor9Light,
        customColor9ContainerLight,
        onCustomColor9ContainerLight,
    ),
    customColor10 = ColorFamily(
        customColor10Light,
        onCustomColor10Light,
        customColor10ContainerLight,
        onCustomColor10ContainerLight,
    ),
)

val extendedDark = ExtendedColorScheme(
    highPriorityNoteColor = ColorFamily(
        highPriorityNoteColorDark,
        onHighPriorityNoteColorDark,
        highPriorityNoteColorContainerDark,
        onHighPriorityNoteColorContainerDark,
    ),
    lowPriorityNoteColor = ColorFamily(
        lowPriorityNoteColorDark,
        onLowPriorityNoteColorDark,
        lowPriorityNoteColorContainerDark,
        onLowPriorityNoteColorContainerDark,
    ),
    mediumPriorityNoteColor = ColorFamily(
        mediumPriorityNoteColorDark,
        onMediumPriorityNoteColorDark,
        mediumPriorityNoteColorContainerDark,
        onMediumPriorityNoteColorContainerDark,
    ),
    veryLowPriorityNoteColor = ColorFamily(
        veryLowPriorityColorDark,
        onVeryLowPriorityColorDark,
        veryLowPriorityColorContainerDark,
        onVeryLowPriorityColorContainerDark,
    ),
    customColor1 = ColorFamily(
        customColor1Dark,
        onCustomColor1Dark,
        customColor1ContainerDark,
        onCustomColor1ContainerDark
    ),
    customColor2 = ColorFamily(
        customColor2Dark,
        onCustomColor2Dark,
        customColor2ContainerDark,
        onCustomColor2ContainerDark,
    ),
    customColor3 = ColorFamily(
        customColor3Dark,
        onCustomColor3Dark,
        customColor3ContainerDark,
        onCustomColor3ContainerDark,
    ),
    customColor4 = ColorFamily(
        customColor4Dark,
        onCustomColor4Dark,
        customColor4ContainerDark,
        onCustomColor4ContainerDark,
    ),
    customColor5 = ColorFamily(
        customColor5Dark,
        onCustomColor5Dark,
        customColor5ContainerDark,
        onCustomColor5ContainerDark,
    ),
    customColor6 = ColorFamily(
        customColor6Dark,
        onCustomColor6Dark,
        customColor6ContainerDark,
        onCustomColor6ContainerDark,
    ),
    customColor7 = ColorFamily(
        customColor7Dark,
        onCustomColor7Dark,
        customColor7ContainerDark,
        onCustomColor7ContainerDark,
    ),
    customColor8 = ColorFamily(
        customColor8Dark,
        onCustomColor8Dark,
        customColor8ContainerDark,
        onCustomColor8ContainerDark,
    ),
    customColor9 = ColorFamily(
        customColor9Dark,
        onCustomColor9Dark,
        customColor9ContainerDark,
        onCustomColor9ContainerDark,
    ),
    customColor10 = ColorFamily(
        customColor10Dark,
        onCustomColor10Dark,
        customColor10ContainerDark,
        onCustomColor10ContainerDark,
    ),
)

/**
 * Color family for the note's background color
 * @param color A little darker color than colorContainer. You will find its use in background color of timestamp field in DetailNoteScreen.
 * @param onColor Text color which should be used above color.  You will find its use in text color of timestamp field in DetailNoteScreen.
 * @param colorContainer Actual color which will be used as background color of the note.
 * @param onColorContainer Text color which should be used above colorContainer
 */
@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

@Composable
fun NoteAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkScheme
        else -> lightScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}


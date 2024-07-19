package com.shivam_raj.noteapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

/**
 * Lottie animation composable function which can be used to play lottie animation.
 * @param modifier Modifier to be applied to this layout.
 * @param animation Animation to be played by this. Default is warning_animation.
 * warning_animation is played in the confirmation dialog when a note is being deleted
 */
@Composable
fun LottieAnimations(
    modifier: Modifier = Modifier,
    animation: Int = R.raw.warning_animation,
) {
    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            animation
        )
    )

    val preloaderProgress by animateLottieCompositionAsState(
        preloaderLottieComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )
    LottieAnimation(
        composition = preloaderLottieComposition,
        progress = { preloaderProgress },
        modifier = modifier
    )
}
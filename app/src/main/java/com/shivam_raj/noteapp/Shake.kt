package com.shivam_raj.noteapp

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer

/**
 * Remember and returns an instance of ShakeController which can be passed as the argument for [Modifier.shake] modifier to give a shake effect.
 *  - Please call [ShakeController.shake] function to shake a view.
 */
@Composable
fun rememberShakeController(): ShakeController {
    return remember { ShakeController() }
}

/**
 * Shake modifier to give a shake effect.
 * @param shakeController An instance of ShakeController
 * @see rememberShakeController
 */
fun Modifier.shake(shakeController: ShakeController) = composed {
    shakeController.shakeConfig?.let { shakeConfig ->
        val shake = remember { Animatable(0f) }
        LaunchedEffect(key1 = shakeConfig) {
            for (i in 0..shakeConfig.iterations) {
                if (i % 2 == 0) shake.animateTo(
                    1f,
                    animationSpec = spring(stiffness = shakeConfig.stiffness)
                )
                else shake.animateTo(
                    -1f,
                    animationSpec = spring(stiffness = shakeConfig.stiffness)
                )
            }
            shake.animateTo(0f, spring(dampingRatio = 0.1f))
        }
        this.graphicsLayer {
            translationX = shake.value * shakeConfig.translationX
        }
    } ?: this
}


class ShakeController {
    var shakeConfig: ShakeConfig? by mutableStateOf(null)

    /**
     * Gives a shake effect
     * @param shakeConfig An instance of [ShakeConfig] class
     */
    fun shake(shakeConfig: ShakeConfig = ShakeConfig()) {
        this.shakeConfig = shakeConfig
    }
}

/**
 * Properties of shake modifier.
 * @param iterations Number of times of shaking, Default is 2
 * @param translationX Horizontal pixel offset of the content
 * @param stiffness stiffness of the spring.
 */
data class ShakeConfig(
    val iterations: Int = 2,
    val translationX: Float = 100f,
    val stiffness:Float = 100_000f,
    val trigger:Long = System.currentTimeMillis()
)
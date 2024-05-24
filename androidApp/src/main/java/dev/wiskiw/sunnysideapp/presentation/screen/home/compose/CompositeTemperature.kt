package dev.wiskiw.sunnysideapp.presentation.screen.home.compose

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.wiskiw.sunnysideapp.presentation.theme.size
import kotlin.math.cos
import kotlin.math.sin

private object CompositeTemperatureDefaults {
    const val sourceCountChangedAnimationDuration = 800
    const val idleAnimationDuration = 2000
    const val maxWeatherSourceBubbleRadiusMagnifyFactor = 1.2f
    const val maxWeatherSourceBubbleOffsetFactor = 0.4f
}

@Composable
fun CompositeTemperature(
    modifier: Modifier = Modifier,
    sources: List<Boolean>,
) {
    BoxWithConstraints(
        modifier = modifier
    ) {

//        Box(
//            modifier = Modifier
//                .align(Alignment.Center)
//                .size(MaterialTheme.size.half)
//                .background(
//                    color = Color.Black,
//                    shape = CircleShape,
//                ),
//        )


        val minWeatherSourceBubbleRadius = MaterialTheme.size.five
        val maxWeatherSourceBubbleRadius =
            (minWeatherSourceBubbleRadius.value * CompositeTemperatureDefaults.maxWeatherSourceBubbleRadiusMagnifyFactor).dp


        var isVisibleState by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            isVisibleState = true
        }
        val initTransition = updateTransition(
            targetState = isVisibleState,
            label = "initTransition",
        )
        val offsetAngle by initTransition.animateFloat(
            transitionSpec = { tween(durationMillis = CompositeTemperatureDefaults.sourceCountChangedAnimationDuration) },
            label = "offsetAngle",
            targetValueByState = { isVisible -> if (isVisible) sources.size * 10f else 0f }
        )
        val gapAngle by initTransition.animateFloat(
            transitionSpec = { tween(durationMillis = CompositeTemperatureDefaults.sourceCountChangedAnimationDuration) },
            label = "gapAngle",
            targetValueByState = { isVisible -> if (isVisible) 360f / sources.size else 0f }
        )
        val radius by initTransition.animateFloat(
            transitionSpec = { tween(durationMillis = CompositeTemperatureDefaults.sourceCountChangedAnimationDuration) },
            label = "radius",
            targetValueByState = { isVisible -> if (isVisible) ((maxWidth / 2) - maxWeatherSourceBubbleRadius).value else 0f }
        )
        val weatherSourceBubbleRadius by initTransition.animateFloat(
            transitionSpec = { tween(durationMillis = CompositeTemperatureDefaults.sourceCountChangedAnimationDuration) },
            label = "weatherSourceBubbleRadius",
            targetValueByState = { isVisible -> if (isVisible) minWeatherSourceBubbleRadius.value else 0f }
        )

        for ((index, source) in sources.withIndex()) {
            val idleTransition = rememberInfiniteTransition(label = "idleTransition")
            val weatherSourceBubbleRadiusFactor by idleTransition.animateFloat(
                initialValue = 1f,
                targetValue = CompositeTemperatureDefaults.maxWeatherSourceBubbleRadiusMagnifyFactor,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = CompositeTemperatureDefaults.idleAnimationDuration,
                        easing = LinearEasing,
                    ),
                    repeatMode = RepeatMode.Reverse,
                    initialStartOffset = StartOffset(
                        offsetMillis = (CompositeTemperatureDefaults.idleAnimationDuration / 10..CompositeTemperatureDefaults.idleAnimationDuration)
                            .random()
                    )
                ),
                label = "weatherSourceBubbleRadiusFactor"
            )
            val weatherSourceBubbleOffsetXFactor by idleTransition.animateFloat(
                initialValue = 0f,
                targetValue = CompositeTemperatureDefaults.maxWeatherSourceBubbleOffsetFactor,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = CompositeTemperatureDefaults.idleAnimationDuration,
                        easing = LinearEasing,
                    ),
                    repeatMode = RepeatMode.Reverse,
                    initialStartOffset = StartOffset(
                        offsetMillis = (0..CompositeTemperatureDefaults.idleAnimationDuration).random(),
                    )
                ),
                label = "weatherSourceBubbleOffsetXFactor"
            )
            val weatherSourceBubbleOffsetYFactor by idleTransition.animateFloat(
                initialValue = 0f,
                targetValue = CompositeTemperatureDefaults.maxWeatherSourceBubbleOffsetFactor,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = CompositeTemperatureDefaults.idleAnimationDuration,
                        easing = LinearEasing,
                    ),
                    repeatMode = RepeatMode.Reverse,
                    initialStartOffset = StartOffset(
                        offsetMillis = (CompositeTemperatureDefaults.idleAnimationDuration / 10..CompositeTemperatureDefaults.idleAnimationDuration)
                            .random()
                    )
                ),
                label = "weatherSourceBubbleOffsetYFactor"
            )


            val xyCoordinate = PolarCoordinate(
                radius = radius,
                angleDegrees = index * gapAngle + offsetAngle
            )
                .toCartesianCoordinate()

            val extraXOffset = weatherSourceBubbleRadius*weatherSourceBubbleOffsetXFactor
            val extraYOffset = weatherSourceBubbleRadius*weatherSourceBubbleOffsetYFactor
            WeatherSourceBubble(
                modifier = Modifier
                    .size((weatherSourceBubbleRadius * 2 * weatherSourceBubbleRadiusFactor).dp)
                    .offset(
                        x = xyCoordinate.first.dp + extraXOffset.dp,
                        y = xyCoordinate.second.dp + extraYOffset.dp,
                    )
                    .align(Alignment.Center),
                isActive = source,
            )
        }
    }
}

@Composable
private fun WeatherSourceBubble(
    modifier: Modifier = Modifier,
    isActive: Boolean,
) {
    Box(
        modifier = modifier
            .background(
                color = if (isActive) Color.Green else Color.Red,
                shape = CircleShape,
            ),
    )
}

private data class PolarCoordinate(
    val radius: Float,
    val angleDegrees: Float,
) {
    fun toCartesianCoordinate(): Pair<Float, Float> {
        val angleRadians = Math.toRadians(angleDegrees.toDouble())
        val x = radius * cos(angleRadians)
        val y = radius * sin(angleRadians)
        return Pair(x.toFloat(), y.toFloat())
    }
}

@Preview(
    showBackground = true,
    widthDp = 512,
    heightDp = 512,
)
@Composable
private fun CompositeTemperaturePreview() {
    CompositeTemperature(
        modifier = Modifier
            .fillMaxSize(),
        sources = listOf(
            true,
            true,
            false,
            false,
            true,
            false,
        )
    )
}

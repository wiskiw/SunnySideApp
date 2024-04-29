package dev.wiskiw.sunnysideapp.presentation.screen.home.compose

import androidx.compose.animation.core.FloatTweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.wiskiw.sunnysideapp.ui.theme.size
import kotlin.math.cos
import kotlin.math.sin

private object CompositeTemperatureDefaults {
    const val sourceCountChangedAnimationDuration = 800
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

        val weatherSourceBubbleRadius = MaterialTheme.size.two

        val sourceCountChangedAnimationSpec = FloatTweenSpec(
            duration = CompositeTemperatureDefaults.sourceCountChangedAnimationDuration,
        )
        var targetOffsetAngle by remember { mutableFloatStateOf(0f) }
        val offsetAngle: Float by animateFloatAsState(
            targetValue = targetOffsetAngle,
            label = "Offset Angle",
            animationSpec = sourceCountChangedAnimationSpec,
        )

        var targetGapAngle by remember { mutableFloatStateOf(0f) }
        val gapAngle: Float by animateFloatAsState(
            targetValue = targetGapAngle,
            label = "Gap Angle",
            animationSpec = sourceCountChangedAnimationSpec,
        )

        var targetRadius by remember { mutableFloatStateOf(0f) }
        val radius: Float by animateFloatAsState(
            targetValue = targetRadius,
            label = "Gap Angle",
            animationSpec = sourceCountChangedAnimationSpec,
        )

        var targetBubbleRadius by remember { mutableFloatStateOf(0f) }
        val bubbleRadius: Float by animateFloatAsState(
            targetValue = targetBubbleRadius,
            label = "Gap Angle",
            animationSpec = sourceCountChangedAnimationSpec,
        )


        LaunchedEffect(sources.size) {
            targetGapAngle = 360f / sources.size
            targetOffsetAngle = sources.size * 10f
        }
        LaunchedEffect(Unit) {
            targetRadius = ((this@BoxWithConstraints.maxWidth / 2) - weatherSourceBubbleRadius).value
            targetBubbleRadius = weatherSourceBubbleRadius.value * 2
        }

        for ((index, source) in sources.withIndex()) {
            val xyCoordinate = PolarCoordinate(
                radius = radius,
                angleDegrees = index * gapAngle + offsetAngle
            )
                .toCartesianCoordinate()

            WeatherSourceBubble(
                modifier = Modifier
                    .size(bubbleRadius.dp)
                    .offset(
                        x = xyCoordinate.first.dp,
                        y = xyCoordinate.second.dp,
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
    device = Devices.PIXEL_7,
)
@Composable
private fun CompositeTemperaturePreview() {
    CompositeTemperature(
        modifier = Modifier
            .size(360.dp),
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


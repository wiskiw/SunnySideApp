package dev.wiskiw.sunnysideapp.presentation.screen.home.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.wiskiw.sunnysideapp.ui.theme.size
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun CompositeTemperature(
    modifier: Modifier = Modifier,
    sources: List<Boolean>,
) {
    BoxWithConstraints(
        modifier = modifier
    ) {
        val weatherSourceBubbleRadius = MaterialTheme.size.two

        // Center debug dot
//        Box(
//            modifier = Modifier
//                .align(Alignment.Center)
//                .size(MaterialTheme.size.half)
//                .background(
//                    color = Color.Black,
//                    shape = CircleShape,
//                ),
//        )

        val maxRadiusDp = (this.maxWidth / 2) - weatherSourceBubbleRadius
        val gapAngle = 360f / sources.size
        val offsetAngle = (0..360).random()

        for ((index, source) in sources.withIndex()) {
            val xyCoordinate = PolarCoordinate(
                radius = maxRadiusDp.value,
                angleDegrees = index * gapAngle + offsetAngle
            )
                .toCartesianCoordinate()

            WeatherSourceBubble(
                modifier = Modifier
                    .size(weatherSourceBubbleRadius * 2)
                    .offset(
                        x = xyCoordinate.first.roundToInt().dp,
                        y = xyCoordinate.second.roundToInt().dp,
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


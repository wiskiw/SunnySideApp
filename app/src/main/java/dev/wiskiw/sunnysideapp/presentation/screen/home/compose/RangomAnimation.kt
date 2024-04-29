package dev.wiskiw.sunnysideapp.presentation.screen.home.compose

import androidx.compose.animation.core.AnimationVector
import androidx.compose.animation.core.DurationBasedAnimationSpec
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.VectorizedDurationBasedAnimationSpec

class RangomAnimation : DurationBasedAnimationSpec<Float> {

    override fun <V : AnimationVector> vectorize(converter: TwoWayConverter<Float, V>): VectorizedDurationBasedAnimationSpec<V> {
        TODO("Not yet implemented")
    }

}
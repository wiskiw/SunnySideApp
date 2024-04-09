package dev.wiskiw.sunnysideapp.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow

@Composable
fun <T : BaseViewModel.SideEffect> SingleEventEffect(
    sideEffectFlow: Flow<T>,
    lifeCycleState: Lifecycle.State = Lifecycle.State.STARTED,
    collector: (T) -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(sideEffectFlow) {
        lifecycleOwner.repeatOnLifecycle(lifeCycleState) {
            sideEffectFlow.collect(collector)
        }
    }
}

@Composable
fun <E : BaseViewModel.Event, S : BaseViewModel.SideEffect> SingleEventEffect(
    sideEffectFlowHolder: BaseViewModel<E, S>,
    lifeCycleState: Lifecycle.State = Lifecycle.State.STARTED,
    collector: (S) -> Unit,
) {
    SingleEventEffect(sideEffectFlowHolder.sideEffectFlow, lifeCycleState, collector)
}

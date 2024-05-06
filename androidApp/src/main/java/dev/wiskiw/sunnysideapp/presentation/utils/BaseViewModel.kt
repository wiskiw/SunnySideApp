package dev.wiskiw.sunnysideapp.presentation.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<E : BaseViewModel.Event, S : BaseViewModel.SideEffect> : ViewModel() {

    interface Event
    interface SideEffect

    private val _sideEffectChannel = Channel<S>(capacity = Channel.BUFFERED)
    val sideEffectFlow: Flow<S>
        get() = _sideEffectChannel.receiveAsFlow()

    protected fun sendSideEffect(effect: S) {
        viewModelScope.launch { _sideEffectChannel.send(effect) }
    }

    abstract fun handleEvent(event: E)

}
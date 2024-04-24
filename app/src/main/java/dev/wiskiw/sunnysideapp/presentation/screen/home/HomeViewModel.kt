package dev.wiskiw.sunnysideapp.presentation.screen.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.wiskiw.sunnysideapp.domain.usecase.LocalTemperatureUseCase
import dev.wiskiw.sunnysideapp.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This ViewModel follows a hybrid approach between MVVM and MVI patterns.
 * It incorporates Event (Intent) from MVI for handling user interactions,
 * while maintaining separate state for each field as seen in MVVM.
 *
 * Considerations:
 * - Single `handleEvent()` method reduces boilerplate code and simplifies handling UI actions in the Screen.
 * - The `handleEvent()` centralizes processing of all UI interactions, including navigation. It can also delegate
 * specific actions back to the UI as needed using `SideEffect`.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val localTemperatureUseCase: LocalTemperatureUseCase,
) : BaseViewModel<HomeViewModel.Event, HomeViewModel.SideEffect>() {

    sealed class Event : BaseViewModel.Event {
        data object LocationPermissionGranted : Event()
    }

    sealed class SideEffect : BaseViewModel.SideEffect

    var address: String? by mutableStateOf(null)
        private set

    var temperatureValue: String by mutableStateOf("")
        private set

    var valueSourceCount: Int by mutableIntStateOf(0)
        private set

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.LocationPermissionGranted -> fetchTemperature()
        }
    }

    private fun fetchTemperature() {
        viewModelScope.launch {
            localTemperatureUseCase.getLocalTemperature(this)
                .collectLatest { localTemperature ->
                    address = localTemperature.address?.locality?.toString()
                    temperatureValue = "%.1f°C".format(localTemperature.temperature.value)
                    valueSourceCount = localTemperature.temperature.sourceCount
                }
        }
    }
}

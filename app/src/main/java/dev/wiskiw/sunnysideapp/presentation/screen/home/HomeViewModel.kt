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

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val localTemperatureUseCase: LocalTemperatureUseCase,
) : BaseViewModel<HomeViewModel.Event, HomeViewModel.SideEffect>() {

    sealed class Event : BaseViewModel.Event {
        data object LocationPermissionGranted : Event()
    }

    sealed class  SideEffect : BaseViewModel.SideEffect

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

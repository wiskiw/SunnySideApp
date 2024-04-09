package dev.wiskiw.sunnysideapp.presentation.screen.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.wiskiw.sunnysideapp.domain.usecase.LocalTemperatureUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    localTemperatureUseCase: LocalTemperatureUseCase,
) : ViewModel() {

    var address: String? by mutableStateOf(null)
        private set

    var temperatureValue: String by mutableStateOf("")
        private set

    var valueSourceCount: Int by mutableIntStateOf(0)
        private set

    init {
        viewModelScope.launch {
            localTemperatureUseCase.getTemperature(this)
                .collectLatest { (geoAddress, avgTemperature) ->
                    address = geoAddress?.locality?.toString()
                    temperatureValue = "%.1f°C".format(avgTemperature.value)
                    valueSourceCount = avgTemperature.sourceCount
                }
        }
    }
}

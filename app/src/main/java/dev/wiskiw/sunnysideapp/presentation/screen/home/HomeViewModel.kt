package dev.wiskiw.sunnysideapp.presentation.screen.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.wiskiw.sunnysideapp.common.model.LatLng
import dev.wiskiw.sunnysideapp.domain.usecase.AverageTemperatureUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    temperatureUseCase: AverageTemperatureUseCase,
) : ViewModel() {

    var temperatureValue: String by mutableStateOf("")
        private set

    var valueSourceCount: String by mutableStateOf("")
        private set

    init {
        viewModelScope.launch {
            val location = LatLng(52.52, 13.619998)
            temperatureUseCase.getTemperature(location)
                .collectLatest {
                    temperatureValue = it.value.toString()
                    valueSourceCount = it.sourceCount.toString()
                }
        }
    }
}

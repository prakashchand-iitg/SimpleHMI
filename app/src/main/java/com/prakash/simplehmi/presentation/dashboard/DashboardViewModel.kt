package com.prakash.simplehmi.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prakash.simplehmi.data.vehicle.SimulatedVehicleDataSource
import com.prakash.simplehmi.data.vehicle.VehicleDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val dataSource: VehicleDataSource = SimulatedVehicleDataSource()
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            dataSource.observeVehicleData().collect { telemetry ->
                _uiState.value = telemetry.toDashboardUiState(System.currentTimeMillis())
            }
        }
    }
}

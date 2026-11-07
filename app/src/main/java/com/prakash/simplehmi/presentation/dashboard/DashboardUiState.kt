package com.prakash.simplehmi.presentation.dashboard

import com.prakash.simplehmi.domain.model.VehicleTelemetry

data class DashboardUiState(
    val speedKmh: Int? = 0,
    val motorOn: Boolean = false,
    val killSwitchActive: Boolean = false,
    val scenario: String = "CONNECTING",
    val signalMessage: String? = null,
    val isStale: Boolean = false
)

fun VehicleTelemetry.toDashboardUiState(nowMillis: Long): DashboardUiState {
    val stale = nowMillis - timestampMillis > 3_000
    val validSpeed = speedKmh?.takeIf { it >= 0 }

    val message = when {
        stale -> "Vehicle data is stale"
        speedKmh == null -> "Speed signal unavailable"
        speedKmh < 0 -> "Invalid speed signal"
        else -> null
    }

    return DashboardUiState(
        speedKmh = if (stale) null else validSpeed,
        motorOn = motorOn,
        killSwitchActive = killSwitchActive,
        scenario = scenario,
        signalMessage = message,
        isStale = stale
    )
}

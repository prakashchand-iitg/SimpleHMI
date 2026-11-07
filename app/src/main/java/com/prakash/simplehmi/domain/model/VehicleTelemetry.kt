package com.prakash.simplehmi.domain.model

data class VehicleTelemetry(
    val speedKmh: Int?,
    val motorOn: Boolean,
    val killSwitchActive: Boolean,
    val scenario: String,
    val timestampMillis: Long
)

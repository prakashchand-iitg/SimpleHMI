package com.prakash.simplehmi.data.vehicle

import com.prakash.simplehmi.domain.model.VehicleTelemetry
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface VehicleDataSource {
    fun observeVehicleData(): Flow<VehicleTelemetry>
}

class SimulatedVehicleDataSource : VehicleDataSource {
    override fun observeVehicleData(): Flow<VehicleTelemetry> = flow {
        // Five required states, followed by two signal-quality edge-case examples.
        val scenarios = listOf(
            Scenario("PARKED", 0, false, false),
            Scenario("READY", 0, true, false),
            Scenario("RIDING", 25, true, false),
            Scenario("ACCELERATING", 50, true, false),
            Scenario("MOTOR DISABLED", 0, false, true),
            Scenario("INVALID SPEED SAMPLE", -8, true, false),
            Scenario("SPEED SIGNAL MISSING", null, true, false)
        )

        while (true) {
            scenarios.forEach { scenario ->
                emit(
                    VehicleTelemetry(
                        speedKmh = scenario.speed,
                        motorOn = scenario.motorOn,
                        killSwitchActive = scenario.killSwitchActive,
                        scenario = scenario.name,
                        timestampMillis = System.currentTimeMillis()
                    )
                )
                delay(1_000)
            }
        }
    }

    private data class Scenario(
        val name: String,
        val speed: Int?,
        val motorOn: Boolean,
        val killSwitchActive: Boolean
    )
}

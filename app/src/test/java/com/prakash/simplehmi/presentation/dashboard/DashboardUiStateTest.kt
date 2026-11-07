package com.prakash.simplehmi.presentation.dashboard

import com.prakash.simplehmi.domain.model.VehicleTelemetry
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class DashboardUiStateTest {

    @Test
    fun `valid telemetry maps to dashboard state`() {
        val telemetry = VehicleTelemetry(
            speedKmh = 25,
            motorOn = true,
            killSwitchActive = false,
            scenario = "RIDING",
            timestampMillis = 10_000L
        )

        val result = telemetry.toDashboardUiState(nowMillis = 10_500L)

        assertEquals(25, result.speedKmh)
        assertTrue(result.motorOn)
        assertFalse(result.killSwitchActive)
        assertNull(result.signalMessage)
    }

    @Test
    fun `negative speed is rejected gracefully`() {
        val telemetry = VehicleTelemetry(
            speedKmh = -5,
            motorOn = true,
            killSwitchActive = false,
            scenario = "INVALID",
            timestampMillis = 10_000L
        )

        val result = telemetry.toDashboardUiState(nowMillis = 10_500L)

        assertNull(result.speedKmh)
        assertEquals("Invalid speed signal", result.signalMessage)
    }

    @Test
    fun `stale telemetry hides speed and reports stale signal`() {
        val telemetry = VehicleTelemetry(
            speedKmh = 40,
            motorOn = true,
            killSwitchActive = false,
            scenario = "RIDING",
            timestampMillis = 1_000L
        )

        val result = telemetry.toDashboardUiState(nowMillis = 5_000L)

        assertNull(result.speedKmh)
        assertTrue(result.isStale)
        assertEquals("Vehicle data is stale", result.signalMessage)
    }
}

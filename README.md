# SimpleHMI – Android HMI Developer Assignment

A landscape Android dashboard that simulates vehicle speed, motor status and kill-switch status using Kotlin, Jetpack Compose, Coroutines and Flow.

## Requirements covered

- Speedometer in km/h with reactive updates.
- Motor ON/OFF and kill-switch Active/Inactive indicators.
- Five required scenarios: Parked, Ready, Riding, Accelerating and Motor Disabled.
- Simulated telemetry emitted approximately once per second.
- ViewModel exposes a `StateFlow` consumed by Compose.
- Invalid negative speed, missing speed and stale telemetry are handled without crashing.
- Unit tests cover valid mapping, invalid speed and stale telemetry.

## Architecture

- `data/vehicle`: `VehicleDataSource` abstraction and simulated implementation.
- `domain/model`: vehicle telemetry model.
- `presentation/dashboard`: UI state mapping, ViewModel and Compose dashboard.
- `MainActivity`: hosts the dashboard; orientation is locked to landscape in the manifest.

Data flow:

`SimulatedVehicleDataSource (Flow) -> DashboardViewModel -> StateFlow<DashboardUiState> -> Compose UI`

## Build and run

1. Open the `SimpleHMI` folder in Android Studio.
2. Allow Gradle sync to finish.
3. Use JDK 17 and install Android SDK Platform 35 if prompted.
4. Create/start an emulator (or connect an Android device).
5. Click Run.

The activity is configured to launch in landscape orientation.

## Run unit tests

From Android Studio, open the Gradle tool window and run `:app:test`, or use the IDE gutter action on the test class. This starter ZIP does not include the Gradle wrapper scripts; Android Studio will use its configured Gradle installation when importing the project.

## Simulation behaviour

The data source cycles through the five required states, then emits two extra signal-quality samples (negative speed and missing speed) to demonstrate graceful handling.

Each sample is emitted at approximately one-second intervals.

## Assumptions and limitations

- This is a software-only simulation; no vehicle hardware, CAN bus, Bluetooth or backend integration is included.
- The scenario sequence repeats continuously.
- A telemetry sample older than three seconds is treated as stale.
- An invalid or unavailable speed is shown as `--`; motor and kill-switch values continue to reflect the received telemetry.
- This is an assignment demonstration, not a production vehicle safety system.

## Before submission

- Run the app on an emulator and verify all scenarios.
- Run the unit tests.
- Add your own screenshots to the repository if useful.
- Create a GitHub repository and push the complete source.

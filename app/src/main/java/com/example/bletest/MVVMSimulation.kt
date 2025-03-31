package com.example.bletest

import android.location.Address
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class BLEViewModelSimulation: ViewModel() {

    private val serviceUuid = UUID.randomUUID()
    private val characteristicUuid = UUID.randomUUID()

    private val _uiState = MutableStateFlow(BLEUiState())
    val uiState: StateFlow<BLEUiState> = _uiState.asStateFlow()

    fun connectToDevice(deviceAddress: String) {
        _uiState.value = _uiState.value.copy(
            connectionState = BleConnectionState.Connecting,
            error = null
        )

        viewModelScope.launch {
            try {
                // Delay simulation
                delay(1500)

                //
                _uiState.value = _uiState.value.copy(
                    connectionState = BleConnectionState.Connected,
                    receivedText = "Connecting to device: $deviceAddress"
                )

                // Receving data simulation
                startSimulatingDataReceiving()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    connectionState = BleConnectionState.Error("Connection failed."),
                    error = e.message
                )
            }
        }
    }

    fun disconnect() {
        _uiState.value = BLEUiState(
            connectionState = BleConnectionState.Disconnected,
            receivedText = "Disconnected"
        )
    }

    private fun startSimulatingDataReceiving() {
        viewModelScope.launch {
            var counter = 0
            while (_uiState.value.connectionState == BleConnectionState.Connected) {
                delay(2000)

                counter++
                val simulatedData = when (counter % 5) {
                    0 -> "Temperature: ${20 + (0..10).random()}°C"
                    1 -> "Angular velocity: ${40 + (0..30).random()}rad/s"
                    2 -> "Battery: ${10 + (0..90).random()}%"
                    3 -> "Time: ${-60 + (0..40).random()} sec"
                    else -> "Data #$counter: ${UUID.randomUUID().toString().take(8)}"
                }

                _uiState.value = _uiState.value.copy(
                    receivedText = simulatedData,
                    isReceiving = true
                )

                // Complete
                delay(300)
                _uiState.value = _uiState.value.copy(isReceiving = false)
            }
        }
    }
}


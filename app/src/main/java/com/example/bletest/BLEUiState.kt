package com.example.bletest

sealed interface BleConnectionState {
    object Disconnected: BleConnectionState
    object Connecting: BleConnectionState
    object Connected: BleConnectionState
    data class Error(val message: String): BleConnectionState
}

data class BLEUiState (
    val connectionState: BleConnectionState = BleConnectionState.Disconnected,
    val receivedText: String = "",
    val isReceiving: Boolean = false,
    val error: String? = null
)
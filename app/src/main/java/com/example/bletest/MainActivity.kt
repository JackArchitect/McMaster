package com.example.bletest

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.bleapp.databinding.ActivityBleTextBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBleTextBinding
    private val viewModel: MVVMSimulation by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBleTextBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // UI
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    updateUi(state)
                }
            }
        }

        // Button event
        binding.connectButton.setOnClickListener {
            viewModel.connectToDevice("AA:BB:CC:DD:EE:FF") // Simulated address
        }

        binding.disconnectButton.setOnClickListener {
            viewModel.disconnect()
        }
    }

    private fun updateUi(state: BLEUiState) {
        // Update
        binding.connectionStatus.text = when (state.connectionState) {
            is BleConnectionState.Connected -> "Connected"
            is BleConnectionState.Connecting -> "Connecting..."
            is BleConnectionState.Disconnected -> "Unconnected"
            is BleConnectionState.Error -> "Error: ${(state.connectionState as BleConnectionState.Error).message}"
        }

        // Receiving text
        binding.receivedText.text = state.receivedText

        // Update loading status
        binding.progressBar.visibility = if (state.isReceiving) {
            android.view.View.VISIBLE
        } else {
            android.view.View.INVISIBLE
        }

        // Error
        state.error?.let { error ->
            binding.errorText.text = error
            binding.errorText.visibility = android.view.View.VISIBLE
        } ?: run {
            binding.errorText.visibility = android.view.View.GONE
        }

        // Control button
        binding.connectButton.isEnabled = state.connectionState is BleConnectionState.Disconnected
        binding.disconnectButton.isEnabled = state.connectionState is BleConnectionState.Connected
    }
}
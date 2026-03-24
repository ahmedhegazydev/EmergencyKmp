package com.example.emergencykmp.mvi

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun EmergencyScreenSettings(
    vm: EmergencyViewModel = koinViewModel()
) {
    val state by vm.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) { vm.onIntent(EmergencyIntent.Load) }

    LaunchedEffect(Unit) {
        vm.effect.collectLatest { e ->
            if (e is EmergencyEffect.Toast) {
                Toast.makeText(context, e.msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = state.keyword,
            onValueChange = { vm.onIntent(EmergencyIntent.SetKeyword(it)) },
            label = { Text("Keyword") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = state.phone,
            onValueChange = { vm.onIntent(EmergencyIntent.SetPhone(it)) },
            label = { Text("Phone") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = state.sms,
            onValueChange = { vm.onIntent(EmergencyIntent.SetSms(it)) },
            label = { Text("SMS") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Row {
            Button(onClick = { vm.onIntent(EmergencyIntent.Save) }) { Text("Save") }
            Spacer(Modifier.width(8.dp))
            if (!state.enabled) {
                Button(onClick = { vm.onIntent(EmergencyIntent.StartMode) }) { Text("Start") }
            } else {
                OutlinedButton(onClick = { vm.onIntent(EmergencyIntent.StopMode) }) { Text("Stop") }
            }
        }
    }
}

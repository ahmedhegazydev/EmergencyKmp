package com.example.emergencykmp

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {

    private val perms = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CALL_PHONE,
        Manifest.permission.SEND_SMS,
    )

    private val requestPerms = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { /* ignore for now */ }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MaterialTheme {
//                MainScreen(
//                    onRequestPermissions = { requestPerms.launch(perms) },
//                    onOpenSettings = {
//                        startActivity(Intent(this, SettingsActivity::class.java))
//                    }
//                )
                AppNavHost(
                    onRequestPermissions = { requestPerms.launch(perms) },
                )
            }
        }
    }
}

@Composable
fun MainScreen(
    onRequestPermissions: () -> Unit=  {},
    onOpenSettings: () -> Unit
) {
    // ✅ دي اللي اتفقنا عليها: controller من Koin
    val service: EmergencyServiceController = koinInject()

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Emergency KMP (Android)", style = MaterialTheme.typography.headlineSmall)

        Spacer(Modifier.height(16.dp))

        Button(onClick = onRequestPermissions) {
            Text("Request Permissions")
        }

        Spacer(Modifier.height(16.dp))

        Button(onClick = { service.start() }) {
            Text("Enable Emergency Mode (Start Service)")
        }

        Spacer(Modifier.height(10.dp))

        OutlinedButton(onClick = { service.stop() }) {
            Text("Disable Emergency Mode (Stop Service)")
        }

        Spacer(Modifier.height(10.dp))

        Button(onClick = onOpenSettings) {
            Text("Open Settings")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppAndroidPreview() {
    MaterialTheme {
        // Preview من غير Koin injection
        MainScreen(
            onRequestPermissions = {},
            onOpenSettings = {}
        )
    }
}

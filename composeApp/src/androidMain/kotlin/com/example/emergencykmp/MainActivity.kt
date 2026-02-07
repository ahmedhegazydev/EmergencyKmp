package com.example.emergencykmp

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

//        setContent {
//            App()
//        }
        setContent {
            MaterialTheme {
                Column(Modifier.fillMaxSize().padding(16.dp)) {
                    Text("Emergency KMP (Android)", style = MaterialTheme.typography.headlineSmall)

                    Spacer(Modifier.height(16.dp))

                    Button(onClick = { requestPerms.launch(perms) }) {
                        Text("Request Permissions")
                    }

                    Spacer(Modifier.height(16.dp))

                    Button(onClick = { ServiceController.start(this@MainActivity) }) {
                        Text("Enable Emergency Mode (Start Service)")
                    }

                    Spacer(Modifier.height(10.dp))

                    OutlinedButton(onClick = { ServiceController.stop(this@MainActivity) }) {
                        Text("Disable Emergency Mode (Stop Service)")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
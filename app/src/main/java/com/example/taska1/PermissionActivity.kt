package com.example.taska1

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED

const val PERMISSION_REQUEST_CODE = 1

class PermissionActivity : ComponentActivity() {

    private val requestOverlayPermission =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                // The permission has been granted.
                // Resend the last command - we have only one, so no additional logic needed.
                startFloatingService(INTENT_COMMAND_NOTE)
                finish()
        }

    @Composable
    private fun PermissionScreen() {
        Column {
            Text(
                text = getString(R.string.permission_required_title),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 8.dp)
            )

            Text(
                text = getString(R.string.permission_required_text),
                modifier = Modifier.padding(16.dp, 4.dp)
            )

            Button(
                onClick = {
                    requestPermission()
                },
                modifier = Modifier.padding(16.dp, 8.dp)
            ) {
                Text(text = getString(R.string.permission_required_open))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PermissionScreen()
        }
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.SYSTEM_ALERT_WINDOW
                ) != PERMISSION_GRANTED
            ) {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                try {
                    requestOverlayPermission.launch(intent)
                } catch (e: Exception) {

                }
            } else {
                startFloatingService(INTENT_COMMAND_NOTE)
                finish()
            }
        } else {
            finish()
        }
    }


    private fun startFloatingService(command: String) {
        val intent = Intent(this, FloatingService::class.java)
        intent.putExtra(INTENT_COMMAND, command)
        startService(intent)
    }
}

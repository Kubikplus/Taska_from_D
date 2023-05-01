package com.localazy.quicknote

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import com.example.taska1.FloatingService
import com.example.taska1.INTENT_COMMAND
import com.example.taska1.PermissionActivity

fun Context.startFloatingService(command: String = "") {
    val intent = Intent(this, FloatingService::class.java)
    if (command.isNotBlank()) intent.putExtra(INTENT_COMMAND, command)
    this.startForegroundService(intent)
}


fun Context.drawOverOtherAppsEnabled(): Boolean {
    return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        true
    } else {
        Settings.canDrawOverlays(this)
    }
}


fun Context.startPermissionActivity() {
    startActivity(
        Intent(this, PermissionActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
    )
}
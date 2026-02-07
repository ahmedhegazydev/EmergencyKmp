package com.example.emergencykmp

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat

object ServiceController {
    fun start(context: Context) {
        val i = Intent(context, EmergencyListenService::class.java)
        ContextCompat.startForegroundService(context, i)
    }

    fun stop(context: Context) {
        val i = Intent(context, EmergencyListenService::class.java)
        context.stopService(i)
    }
}

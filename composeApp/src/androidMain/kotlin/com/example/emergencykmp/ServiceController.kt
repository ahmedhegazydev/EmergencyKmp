package com.example.emergencykmp

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat

interface EmergencyServiceController {
    fun start()
    fun stop()
}

class AndroidEmergencyServiceController(
    private val context: Context
) : EmergencyServiceController {

    override fun start() {
        val i = Intent(context, EmergencyListenService::class.java)
        ContextCompat.startForegroundService(context, i)
    }

    override fun stop() {
        val i = Intent(context, EmergencyListenService::class.java)
        context.stopService(i)
    }
}
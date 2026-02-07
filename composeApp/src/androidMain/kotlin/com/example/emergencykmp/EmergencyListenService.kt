package com.example.emergencykmp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.core.app.NotificationCompat

class EmergencyListenService : Service() {

    private var speech: SpeechRecognizer? = null

    override fun onCreate() {
        super.onCreate()
        startForeground(1001, buildNotification("Emergency mode ON"))

        startListening()
    }

    private fun startListening() {
        if (!SpeechRecognizer.isRecognitionAvailable(this)) return

        if (speech == null) {
            speech = SpeechRecognizer.createSpeechRecognizer(this).apply {
                setRecognitionListener(object : RecognitionListener {

                    override fun onResults(results: Bundle) {
                        val texts = results
                            .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                            .orEmpty()

                        // ✅ مؤقتًا: اعتبر أي كلام trigger علشان نجرب الديمو بسرعة
                        // بعدين هنربطه بـ keyword من Settings
                        if (texts.isNotEmpty()) {
                            openConfirm()
                        }

                        restart()
                    }

                    override fun onError(error: Int) {
                        restart()
                    }

                    private fun restart() {
                        try { startListening(buildRecognizerIntent()) } catch (_: Exception) {}
                    }

                    override fun onReadyForSpeech(params: Bundle?) {}
                    override fun onBeginningOfSpeech() {}
                    override fun onRmsChanged(rmsdB: Float) {}
                    override fun onBufferReceived(buffer: ByteArray?) {}
                    override fun onEndOfSpeech() {}
                    override fun onPartialResults(partialResults: Bundle?) {}
                    override fun onEvent(eventType: Int, params: Bundle?) {}
                })
            }
        }

        try {
            speech?.startListening(buildRecognizerIntent())
        } catch (_: Exception) {}
    }

    private fun buildRecognizerIntent(): Intent =
        Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        }

    private fun openConfirm() {
        val i = Intent(this, EmergencyConfirmActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(i)
    }

    private fun buildNotification(text: String): Notification {
        val channelId = "emergency_mode"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nm = getSystemService(NotificationManager::class.java)
            nm.createNotificationChannel(
                NotificationChannel(channelId, "Emergency Mode", NotificationManager.IMPORTANCE_LOW)
            )
        }
        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Emergency Mode")
            .setContentText(text)
            .setSmallIcon(android.R.drawable.ic_lock_silent_mode_off)
            .setOngoing(true)
            .build()
    }

    override fun onDestroy() {
        speech?.destroy()
        speech = null
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}

package com.example.emergencykmp

import android.app.Activity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView

class EmergencyConfirmActivity : Activity() {

    private var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // UI بسيط جدًا (بدون XML) علشان ننجز بسرعة
        val title = TextView(this).apply {
            textSize = 22f
            text = "Emergency Triggered!"
            setPadding(40, 80, 40, 20)
        }

        val counter = TextView(this).apply {
            textSize = 18f
            setPadding(40, 20, 40, 20)
        }

        val cancel = Button(this).apply {
            text = "Cancel"
            setOnClickListener {
                timer?.cancel()
                finish()
            }
        }

        val layout = android.widget.LinearLayout(this).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            addView(title)
            addView(counter)
            addView(cancel)
        }

        setContentView(layout)

        timer = object : CountDownTimer(3000, 1000) {
            override fun onTick(ms: Long) {
                counter.text = "Calling in ${ms / 1000}..."
            }

            override fun onFinish() {
                // الخطوة الجاية هنضيف Call + SMS هنا
                finish()
            }
        }.start()
    }

    override fun onDestroy() {
        timer?.cancel()
        super.onDestroy()
    }
}

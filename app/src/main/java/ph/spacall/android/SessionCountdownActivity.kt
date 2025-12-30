// app/src/main/java/ph/spacall/android/SessionCountdownActivity.kt

package ph.spacall.android

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageButton
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SessionCountdownActivity : AppCompatActivity() {

    private lateinit var backButton: ImageButton
    private lateinit var resetButton: ImageButton
    private lateinit var countdownText: TextView
    private lateinit var earlyStopButton: Button
    private lateinit var clockDialView: ClockDialView

    private var countDownTimer: CountDownTimer? = null
    private val sessionDurationMillis: Long = 30000 // 30 seconds
    private var therapistName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session_countdown)

        // Get therapist name from intent
        therapistName = intent.getStringExtra("EXTRA_NAME") ?: "Therapist"

        // Initialize views
        initializeViews()

        // Setup listeners
        setupListeners()

        // Start countdown
        startCountdown()
    }

    private fun initializeViews() {
        backButton = findViewById(R.id.backButton)
        resetButton = findViewById(R.id.resetButton)
        countdownText = findViewById(R.id.countdownText)
        earlyStopButton = findViewById(R.id.earlyStopButton)
        clockDialView = findViewById(R.id.clockDialView)
    }

    private fun setupListeners() {
        backButton.setOnClickListener {
            finish()
        }

        resetButton.setOnClickListener {
            resetCountdown()
        }

        earlyStopButton.setOnClickListener {
            stopSessionEarly()
        }
    }

    private fun startCountdown() {
        countDownTimer?.cancel()

        countDownTimer = object : CountDownTimer(sessionDurationMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = (millisUntilFinished / 1000).toInt()

                // Update countdown text
                countdownText.text = String.format("%02d", secondsRemaining)

                // Update clock dial view
                val progress = ((sessionDurationMillis - millisUntilFinished).toFloat() / sessionDurationMillis.toFloat())
                clockDialView.setProgress(progress)
            }

            override fun onFinish() {
                countdownText.text = "00"
                clockDialView.setProgress(1f)

                // Navigate to Session Finished page
                navigateToSessionFinished()
            }
        }.start()
    }

    private fun resetCountdown() {
        countDownTimer?.cancel()
        startCountdown()
    }

    private fun stopSessionEarly() {
        countDownTimer?.cancel()
        navigateToSessionFinished()
    }

    private fun navigateToSessionFinished() {
        val intent = Intent(this, SessionFinishedActivity::class.java)
        intent.putExtra("EXTRA_NAME", therapistName)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }
}
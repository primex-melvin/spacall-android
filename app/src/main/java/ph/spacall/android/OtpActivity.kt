// app/src/main/java/ph/spacall/android/OtpActivity.kt

package ph.spacall.android

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class OtpActivity : AppCompatActivity() {

    private lateinit var timerText: TextView
    private lateinit var otpDigit1: TextView
    private lateinit var otpDigit2: TextView
    private lateinit var otpDigit3: TextView
    private lateinit var otpDigit4: TextView
    private lateinit var sendAgainText: TextView
    private lateinit var backButton: ImageButton

    private val otpDigits = mutableListOf<String>()
    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)

        // Initialize views
        initializeViews()

        // Start countdown timer
        startTimer()

        // Setup number pad
        setupNumberPad()

        // Setup back button
        backButton.setOnClickListener {
            finish()
        }

        // Setup send again
        sendAgainText.setOnClickListener {
            resetOtp()
            startTimer()
            Toast.makeText(this, "Code sent again", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initializeViews() {
        timerText = findViewById(R.id.timerText)
        otpDigit1 = findViewById(R.id.otpDigit1)
        otpDigit2 = findViewById(R.id.otpDigit2)
        otpDigit3 = findViewById(R.id.otpDigit3)
        otpDigit4 = findViewById(R.id.otpDigit4)
        sendAgainText = findViewById(R.id.sendAgainText)
        backButton = findViewById(R.id.backButton)
    }

    private fun setupNumberPad() {
        // Number buttons
        findViewById<TextView>(R.id.btn0).setOnClickListener { addDigit("0") }
        findViewById<TextView>(R.id.btn1).setOnClickListener { addDigit("1") }
        findViewById<TextView>(R.id.btn2).setOnClickListener { addDigit("2") }
        findViewById<TextView>(R.id.btn3).setOnClickListener { addDigit("3") }
        findViewById<TextView>(R.id.btn4).setOnClickListener { addDigit("4") }
        findViewById<TextView>(R.id.btn5).setOnClickListener { addDigit("5") }
        findViewById<TextView>(R.id.btn6).setOnClickListener { addDigit("6") }
        findViewById<TextView>(R.id.btn7).setOnClickListener { addDigit("7") }
        findViewById<TextView>(R.id.btn8).setOnClickListener { addDigit("8") }
        findViewById<TextView>(R.id.btn9).setOnClickListener { addDigit("9") }

        // Delete button
        findViewById<ImageView>(R.id.btnDelete).setOnClickListener { deleteDigit() }
    }

    private fun addDigit(digit: String) {
        if (otpDigits.size < 4) {
            otpDigits.add(digit)
            updateOtpDisplay()

            // If all 4 digits entered, verify OTP
            if (otpDigits.size == 4) {
                verifyOtp()
            }
        }
    }

    private fun deleteDigit() {
        if (otpDigits.isNotEmpty()) {
            otpDigits.removeAt(otpDigits.size - 1)
            updateOtpDisplay()
        }
    }

    private fun updateOtpDisplay() {
        val digitViews = listOf(otpDigit1, otpDigit2, otpDigit3, otpDigit4)

        for (i in digitViews.indices) {
            if (i < otpDigits.size) {
                digitViews[i].text = otpDigits[i]
                digitViews[i].setBackgroundResource(R.drawable.otp_box_filled)
                digitViews[i].setTextColor(resources.getColor(R.color.white, null))
            } else {
                digitViews[i].text = "0"
                if (i == otpDigits.size) {
                    // Current digit (next to be filled)
                    digitViews[i].setBackgroundResource(R.drawable.otp_box_empty)
                    digitViews[i].setTextColor(resources.getColor(R.color.brand_secondary, null))
                } else {
                    // Future digits
                    digitViews[i].setBackgroundResource(R.drawable.otp_box_empty)
                    digitViews[i].setTextColor(resources.getColor(R.color.stroke_light, null))
                }
            }
        }
    }

    private fun verifyOtp() {
        val enteredOtp = otpDigits.joinToString("")

        // TODO: Verify OTP with backend
        Toast.makeText(this, "Verifying OTP: $enteredOtp", Toast.LENGTH_SHORT).show()

        // Navigate to Profile Details screen
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    private fun resetOtp() {
        otpDigits.clear()
        updateOtpDisplay()
    }

    private fun startTimer() {
        countDownTimer?.cancel()

        countDownTimer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / 1000 / 60
                val seconds = millisUntilFinished / 1000 % 60
                timerText.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                timerText.text = "00:00"
                Toast.makeText(this@OtpActivity, "Code expired. Please send again.", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }
}
package ph.spacall.android

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PhoneActivity : AppCompatActivity() {

    private lateinit var phoneNumberInput: EditText
    private lateinit var continueButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone)

        // Initialize views
        phoneNumberInput = findViewById(R.id.phoneNumberInput)
        continueButton = findViewById(R.id.continueButton)

        // Set click listener for continue button
        continueButton.setOnClickListener {
            val phoneNumber = phoneNumberInput.text.toString().trim()

            // Validate phone number
            if (phoneNumber.isEmpty()) {
                Toast.makeText(this, "Please enter your phone number", Toast.LENGTH_SHORT).show()
            } else if (phoneNumber.length < 10) {
                Toast.makeText(this, "Please enter a valid 10-digit phone number", Toast.LENGTH_SHORT).show()
            } else {
                // Phone number is valid, navigate to OTP screen
                val fullNumber = "+63$phoneNumber"
                Toast.makeText(this, "Sending code to $fullNumber", Toast.LENGTH_SHORT).show()

                // Navigate to OTP verification screen
                val intent = Intent(this, OtpActivity::class.java)
                intent.putExtra("phone_number", fullNumber)
                startActivity(intent)
            }
        }
    }
}
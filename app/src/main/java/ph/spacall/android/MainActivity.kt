// app/src/main/java/ph/spacall/android/MainActivity.kt

package ph.spacall.android

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check flag to decide which layout to use
        val useTemporaryWelcome = resources.getBoolean(R.bool.use_temporary_welcome_page)

        if (useTemporaryWelcome) {
            // Use temporary welcome page with background image
            setContentView(R.layout.activity_main_temp)
            setupTemporaryWelcomePage()
        } else {
            // Use original welcome page
            setContentView(R.layout.activity_main)
            setupOriginalWelcomePage()
        }
    }

    private fun setupTemporaryWelcomePage() {
        // Initialize views for temporary page
        val getStartedButton = findViewById<Button>(R.id.getStartedButton)

        // Set click listener
        getStartedButton.setOnClickListener {
            // Navigate to phone number registration screen
            val intent = Intent(this, PhoneActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupOriginalWelcomePage() {
        // Initialize views for original page
        val getStartedButton = findViewById<Button>(R.id.getStartedButton)
        val signInText = findViewById<TextView>(R.id.signInText)

        // Set click listeners
        getStartedButton.setOnClickListener {
            // Navigate to phone number registration screen
            val intent = Intent(this, PhoneActivity::class.java)
            startActivity(intent)
        }

        signInText.setOnClickListener {
            // Handle Sign In text click
            Toast.makeText(this, "Sign In clicked", Toast.LENGTH_SHORT).show()
            // TODO: Navigate to login screen
            // startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
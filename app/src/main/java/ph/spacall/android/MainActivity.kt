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
        setContentView(R.layout.activity_main)

        // Initialize views
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
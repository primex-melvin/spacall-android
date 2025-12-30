// app/src/main/java/ph/spacall/android/TherapistArrivedActivity.kt

package ph.spacall.android

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class TherapistArrivedActivity : AppCompatActivity() {

    private lateinit var backButton: ImageButton
    private lateinit var resetButton: ImageButton
    private lateinit var startSessionButton: Button

    // Therapist data
    private var therapistName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_therapist_arrived)

        // Get therapist data from intent
        therapistName = intent.getStringExtra("EXTRA_NAME") ?: "Therapist"

        // Initialize views
        initializeViews()

        // Setup listeners
        setupListeners()
    }

    private fun initializeViews() {
        backButton = findViewById(R.id.backButton)
        resetButton = findViewById(R.id.resetButton)
        startSessionButton = findViewById(R.id.startSessionButton)
    }

    private fun setupListeners() {
        backButton.setOnClickListener {
            finish()
        }

        resetButton.setOnClickListener {
            Toast.makeText(this, "Reset", Toast.LENGTH_SHORT).show()
            // Add reset functionality if needed
        }

        startSessionButton.setOnClickListener {
            Toast.makeText(this, "Session started with $therapistName", Toast.LENGTH_LONG).show()
            // Navigate to session screen or perform session start logic
            finish()
        }
    }
}
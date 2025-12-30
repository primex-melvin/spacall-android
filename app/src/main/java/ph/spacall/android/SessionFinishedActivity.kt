// app/src/main/java/ph/spacall/android/SessionFinishedActivity.kt

package ph.spacall.android

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SessionFinishedActivity : AppCompatActivity() {

    private lateinit var backButton: ImageButton
    private lateinit var resetButton: ImageButton
    private lateinit var descriptionText: TextView
    private lateinit var homeButton: Button

    private var therapistName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session_finished)

        // Get therapist name from intent
        therapistName = intent.getStringExtra("EXTRA_NAME") ?: "Therapist"

        // Initialize views
        initializeViews()

        // Set description with therapist name
        descriptionText.text = getString(R.string.session_finished_description, therapistName)

        // Setup listeners
        setupListeners()
    }

    private fun initializeViews() {
        backButton = findViewById(R.id.backButton)
        resetButton = findViewById(R.id.resetButton)
        descriptionText = findViewById(R.id.descriptionText)
        homeButton = findViewById(R.id.homeButton)
    }

    private fun setupListeners() {
        backButton.setOnClickListener {
            finish()
        }

        resetButton.setOnClickListener {
            // Navigate back to searching therapists
            navigateToSearchingTherapists()
        }

        homeButton.setOnClickListener {
            // Navigate back to searching therapists
            navigateToSearchingTherapists()
        }
    }

    private fun navigateToSearchingTherapists() {
        val intent = Intent(this, SearchingTherapistsActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}
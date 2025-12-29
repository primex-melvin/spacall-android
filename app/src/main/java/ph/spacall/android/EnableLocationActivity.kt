// app/src/main/java/ph/spacall/android/EnableLocationActivity.kt

package ph.spacall.android

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class EnableLocationActivity : AppCompatActivity() {

    private lateinit var enableLocationButton: Button

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enable_location)

        // Initialize views
        initializeViews()

        // Setup listeners
        setupListeners()
    }

    private fun initializeViews() {
        enableLocationButton = findViewById(R.id.enableLocationButton)
    }

    private fun setupListeners() {
        // Enable Location button
        enableLocationButton.setOnClickListener {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Permission already granted
            Toast.makeText(this, "Location permission granted", Toast.LENGTH_SHORT).show()
            navigateToSearching()
        } else {
            // Request permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted - navigate to searching page
                Toast.makeText(this, "Location enabled successfully", Toast.LENGTH_SHORT).show()
                navigateToSearching()
            } else {
                // Permission denied - still navigate but show message
                Toast.makeText(
                    this,
                    "Location permission denied. Limited functionality available.",
                    Toast.LENGTH_LONG
                ).show()
                navigateToSearching()
            }
        }
    }

    private fun navigateToSearching() {
        // Navigate to Searching Therapists page
        val intent = Intent(this, SearchingTherapistsActivity::class.java)
        startActivity(intent)
        finish()
    }
}
// app/src/main/java/ph/spacall/android/BookingRequestAcceptedActivity.kt

package ph.spacall.android

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polygon
import org.osmdroid.views.overlay.Polyline

class BookingRequestAcceptedActivity : AppCompatActivity() {

    private lateinit var mapView: MapView
    private lateinit var backButton: ImageButton
    private lateinit var resetButton: ImageButton
    private lateinit var emergencyButton: Button
    private lateinit var listButton: ImageButton
    private lateinit var homeButton: ImageButton
    private lateinit var titleText: TextView

    // Therapist data
    private var therapistName: String = ""
    private var therapistLat: Double = 0.0
    private var therapistLng: Double = 0.0

    // User location (center of Manila)
    private val userLocation = GeoPoint(14.5995, 120.9842)

    // Countdown variables
    private var countdownSeconds = 3
    private val countdownHandler = Handler(Looper.getMainLooper())
    private var currentToast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize osmdroid configuration
        Configuration.getInstance().load(
            this,
            PreferenceManager.getDefaultSharedPreferences(this)
        )

        setContentView(R.layout.activity_booking_request_accepted)

        // Get therapist data from intent
        therapistName = intent.getStringExtra("EXTRA_NAME") ?: "Therapist"
        therapistLat = intent.getDoubleExtra("EXTRA_LATITUDE", 14.6050)
        therapistLng = intent.getDoubleExtra("EXTRA_LONGITUDE", 120.9900)

        // Initialize views
        initializeViews()

        // Setup title with therapist name
        titleText.text = getString(R.string.request_accepted_title, therapistName)

        // Setup map
        setupMap()

        // Add markers and route
        addMapElements()

        // Setup listeners
        setupListeners()

        // Start countdown timer
        startCountdown()
    }

    private fun initializeViews() {
        mapView = findViewById(R.id.mapView)
        backButton = findViewById(R.id.backButton)
        resetButton = findViewById(R.id.resetButton)
        emergencyButton = findViewById(R.id.emergencyButton)
        listButton = findViewById(R.id.listButton)
        homeButton = findViewById(R.id.homeButton)
        titleText = findViewById(R.id.titleText)
    }

    private fun setupMap() {
        // Set tile source (using OpenStreetMap)
        mapView.setTileSource(TileSourceFactory.MAPNIK)

        // Enable multi-touch controls
        mapView.setMultiTouchControls(true)

        // Set initial zoom level
        mapView.controller.setZoom(14.0)

        // Center on midpoint between user and therapist
        val midpointLat = (userLocation.latitude + therapistLat) / 2
        val midpointLng = (userLocation.longitude + therapistLng) / 2
        val midpoint = GeoPoint(midpointLat, midpointLng)
        mapView.controller.setCenter(midpoint)
    }

    private fun addMapElements() {
        // Add user location marker (center - blue)
        addUserLocationMarker()

        // Add therapist location marker (gold)
        addTherapistLocationMarker()

        // Add gold search radius circles
        addSearchRadiusCircles()

        // Add red route line
        addRouteLine()
    }

    private fun addUserLocationMarker() {
        val userMarker = Marker(mapView)
        userMarker.position = userLocation
        userMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        userMarker.title = "Your Location"

        // Set custom icon (blue circle)
        val userIcon = ContextCompat.getDrawable(this, R.drawable.ic_user_location_pin)
        userMarker.icon = userIcon

        mapView.overlays.add(userMarker)
    }

    private fun addTherapistLocationMarker() {
        val therapistLocation = GeoPoint(therapistLat, therapistLng)

        val therapistMarker = Marker(mapView)
        therapistMarker.position = therapistLocation
        therapistMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        therapistMarker.title = therapistName

        // Set custom icon (gold marker)
        val therapistIcon = ContextCompat.getDrawable(this, R.drawable.therapist_marker_icon)
        therapistMarker.icon = therapistIcon

        mapView.overlays.add(therapistMarker)
    }

    private fun addSearchRadiusCircles() {
        // Add gold circle around user (smaller radius)
        val userCircle = Polygon(mapView)
        userCircle.points = Polygon.pointsAsCircle(userLocation, 800.0) // 800 meters
        userCircle.fillColor = 0x40D4AF37.toInt() // Semi-transparent gold (40 = 25% opacity)
        userCircle.strokeColor = 0x80D4AF37.toInt() // More opaque gold for border
        userCircle.strokeWidth = 3f
        mapView.overlays.add(0, userCircle) // Add at bottom layer

        // Add gold circle around therapist (larger radius)
        val therapistLocation = GeoPoint(therapistLat, therapistLng)
        val therapistCircle = Polygon(mapView)
        therapistCircle.points = Polygon.pointsAsCircle(therapistLocation, 1500.0) // 1500 meters
        therapistCircle.fillColor = 0x40D4AF37.toInt() // Semi-transparent gold
        therapistCircle.strokeColor = 0x80D4AF37.toInt() // More opaque gold for border
        therapistCircle.strokeWidth = 3f
        mapView.overlays.add(0, therapistCircle) // Add at bottom layer
    }

    private fun addRouteLine() {
        // Create red route line between user and therapist
        val routeLine = Polyline(mapView)

        val therapistLocation = GeoPoint(therapistLat, therapistLng)
        val routePoints = listOf(userLocation, therapistLocation)
        routeLine.setPoints(routePoints)

        // Set red color and width
        routeLine.outlinePaint.color = 0xFFFF0000.toInt() // Red color
        routeLine.outlinePaint.strokeWidth = 8f

        mapView.overlays.add(routeLine)

        // Refresh map
        mapView.invalidate()
    }

    private fun startCountdown() {
        showCountdownToast()
    }

    private fun showCountdownToast() {
        if (countdownSeconds > 0) {
            // Cancel previous toast
            currentToast?.cancel()

            // Show new toast with countdown
            val message = getString(R.string.therapist_arriving_in, countdownSeconds)
            currentToast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
            currentToast?.show()

            // Decrease counter
            countdownSeconds--

            // Schedule next countdown
            countdownHandler.postDelayed({
                showCountdownToast()
            }, 1000) // 1 second delay
        } else {
            // Countdown finished, navigate to Therapist Arrived page
            navigateToTherapistArrived()
        }
    }

    private fun navigateToTherapistArrived() {
        val intent = Intent(this, TherapistArrivedActivity::class.java).apply {
            putExtra("EXTRA_NAME", therapistName)
        }
        startActivity(intent)
        finish() // Close current activity
    }

    private fun setupListeners() {
        backButton.setOnClickListener {
            // Cancel countdown when going back
            countdownHandler.removeCallbacksAndMessages(null)
            currentToast?.cancel()
            finish()
        }

        resetButton.setOnClickListener {
            // Reset map to midpoint view
            val midpointLat = (userLocation.latitude + therapistLat) / 2
            val midpointLng = (userLocation.longitude + therapistLng) / 2
            val midpoint = GeoPoint(midpointLat, midpointLng)

            mapView.controller.setZoom(14.0)
            mapView.controller.animateTo(midpoint)
            Toast.makeText(this, "View reset", Toast.LENGTH_SHORT).show()
        }

        emergencyButton.setOnClickListener {
            Toast.makeText(this, "Emergency services contacted", Toast.LENGTH_SHORT).show()
            // In production, this would call emergency services
        }

        listButton.setOnClickListener {
            Toast.makeText(this, "List view", Toast.LENGTH_SHORT).show()
            // Navigate to list view if needed
        }

        homeButton.setOnClickListener {
            // Cancel countdown when going home
            countdownHandler.removeCallbacksAndMessages(null)
            currentToast?.cancel()
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDetach()
        // Clean up countdown handler
        countdownHandler.removeCallbacksAndMessages(null)
        currentToast?.cancel()
    }
}
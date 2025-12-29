// app/src/main/java/ph/spacall/android/SearchingTherapistsActivity.kt

package ph.spacall.android

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polygon

class SearchingTherapistsActivity : AppCompatActivity() {

    private lateinit var mapView: MapView
    private lateinit var backButton: ImageButton
    private lateinit var resetButton: ImageButton
    private lateinit var listButton: ImageButton
    private lateinit var homeButton: ImageButton
    private lateinit var therapistsRecyclerView: RecyclerView
    private lateinit var therapistAdapter: TherapistAdapter
    private lateinit var bottomSheet: NestedScrollView
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<NestedScrollView>

    private val therapists = mutableListOf<Therapist>()

    // Manila coordinates (center of city)
    private val manilaLocation = GeoPoint(14.5995, 120.9842)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize osmdroid configuration
        Configuration.getInstance().load(
            this,
            PreferenceManager.getDefaultSharedPreferences(this)
        )

        setContentView(R.layout.activity_searching_therapists)

        // Initialize views
        initializeViews()

        // Setup map
        setupMap()

        // Setup bottom sheet
        setupBottomSheet()

        // Setup therapists data
        setupTherapistsData()

        // Add markers to map
        addTherapistMarkers()

        // Setup RecyclerView
        setupRecyclerView()

        // Setup listeners
        setupListeners()

        // Simulate search completion after 3 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            Toast.makeText(this, "Available therapists found!", Toast.LENGTH_SHORT).show()
        }, 3000)
    }

    private fun initializeViews() {
        mapView = findViewById(R.id.mapView)
        backButton = findViewById(R.id.backButton)
        resetButton = findViewById(R.id.resetButton)
        listButton = findViewById(R.id.listButton)
        homeButton = findViewById(R.id.homeButton)
        therapistsRecyclerView = findViewById(R.id.therapistsRecyclerView)
        bottomSheet = findViewById(R.id.bottomSheet)
    }

    private fun setupMap() {
        // Set tile source (using OpenStreetMap)
        mapView.setTileSource(TileSourceFactory.MAPNIK)

        // Enable multi-touch controls
        mapView.setMultiTouchControls(true)

        // Set initial zoom level (14 = city level)
        mapView.controller.setZoom(14.0)

        // Center on Manila
        mapView.controller.setCenter(manilaLocation)

        // Add gold search radius circle
        addSearchRadiusCircle()

        // Add user location marker (center)
        addUserLocationMarker()
    }

    private fun addSearchRadiusCircle() {
        // Create gold circle overlay (2km radius)
        val circle = Polygon(mapView)
        circle.points = Polygon.pointsAsCircle(manilaLocation, 2000.0) // 2000 meters
        circle.fillColor = 0x40D4AF37.toInt() // Semi-transparent gold (40 = 25% opacity)
        circle.strokeColor = 0x80D4AF37.toInt() // More opaque gold for border
        circle.strokeWidth = 3f
        mapView.overlays.add(circle)
    }

    private fun addUserLocationMarker() {
        // User location marker (blue pin at center)
        val userMarker = Marker(mapView)
        userMarker.position = manilaLocation
        userMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        userMarker.title = "Your Location"

        // Set custom icon (blue circle)
        val userIcon = ContextCompat.getDrawable(this, R.drawable.ic_user_location_pin)
        userMarker.icon = userIcon

        mapView.overlays.add(userMarker)
    }

    private fun setupBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.peekHeight = 200
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // Handle state changes if needed
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // Handle sliding if needed
            }
        })
    }

    private fun setupTherapistsData() {
        // Manila area coordinates (scattered around center)
        therapists.add(
            Therapist(
                id = 1,
                name = "Maria Santos",
                rating = 4.8f,
                reviews = 156,
                distance = "0.8 km",
                avatarUrl = "https://i.pravatar.cc/150?img=5",
                specialty = "Thai Massage",
                latitude = 14.6050, // North of center
                longitude = 120.9900
            )
        )
        therapists.add(
            Therapist(
                id = 2,
                name = "John Reyes",
                rating = 4.9f,
                reviews = 203,
                distance = "1.2 km",
                avatarUrl = "https://i.pravatar.cc/150?img=12",
                specialty = "Deep Tissue",
                latitude = 14.6020, // Northeast
                longitude = 120.9920
            )
        )
        therapists.add(
            Therapist(
                id = 3,
                name = "Anna Cruz",
                rating = 4.7f,
                reviews = 89,
                distance = "1.5 km",
                avatarUrl = "https://i.pravatar.cc/150?img=9",
                specialty = "Swedish Massage",
                latitude = 14.5950, // West
                longitude = 120.9750
            )
        )
        therapists.add(
            Therapist(
                id = 4,
                name = "Pedro Garcia",
                rating = 4.6f,
                reviews = 112,
                distance = "2.1 km",
                avatarUrl = "https://i.pravatar.cc/150?img=15",
                specialty = "Sports Massage",
                latitude = 14.5920, // Southwest
                longitude = 120.9780
            )
        )
        therapists.add(
            Therapist(
                id = 5,
                name = "Lisa Mendoza",
                rating = 4.9f,
                reviews = 187,
                distance = "2.3 km",
                avatarUrl = "https://i.pravatar.cc/150?img=10",
                specialty = "Hot Stone",
                latitude = 14.5970, // South
                longitude = 120.9850
            )
        )
        therapists.add(
            Therapist(
                id = 6,
                name = "Miguel Torres",
                rating = 4.5f,
                reviews = 76,
                distance = "2.8 km",
                avatarUrl = "https://i.pravatar.cc/150?img=13",
                specialty = "Aromatherapy",
                latitude = 14.6010, // East
                longitude = 120.9950
            )
        )
    }

    private fun addTherapistMarkers() {
        // Add marker for each therapist
        therapists.forEach { therapist ->
            val marker = Marker(mapView)
            marker.position = GeoPoint(therapist.latitude, therapist.longitude)
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            marker.title = therapist.name
            marker.snippet = "${therapist.specialty} - ★${therapist.rating}"

            // Set custom icon (gold/pink circle)
            val therapistIcon = ContextCompat.getDrawable(this, R.drawable.therapist_marker_icon)
            marker.icon = therapistIcon

            // Click listener
            marker.setOnMarkerClickListener { clickedMarker, _ ->
                Toast.makeText(
                    this,
                    "${therapist.name} - ${therapist.specialty}",
                    Toast.LENGTH_SHORT
                ).show()
                true
            }

            mapView.overlays.add(marker)
        }

        // Refresh map
        mapView.invalidate()
    }

    private fun setupRecyclerView() {
        therapistAdapter = TherapistAdapter(therapists) { therapist ->
            // When therapist card is clicked, center map on that therapist
            val location = GeoPoint(therapist.latitude, therapist.longitude)
            mapView.controller.animateTo(location)
            mapView.controller.setZoom(16.0) // Zoom in closer

            Toast.makeText(
                this,
                "Selected: ${therapist.name}",
                Toast.LENGTH_SHORT
            ).show()
        }

        therapistsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@SearchingTherapistsActivity)
            adapter = therapistAdapter
        }
    }

    private fun setupListeners() {
        backButton.setOnClickListener {
            finish()
        }

        resetButton.setOnClickListener {
            // Reset map to center
            mapView.controller.setZoom(14.0)
            mapView.controller.animateTo(manilaLocation)
            Toast.makeText(this, "Search reset", Toast.LENGTH_SHORT).show()
        }

        listButton.setOnClickListener {
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            } else {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        homeButton.setOnClickListener {
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
    }
}

// Updated Data class for Therapist with location
data class Therapist(
    val id: Int,
    val name: String,
    val rating: Float,
    val reviews: Int,
    val distance: String,
    val avatarUrl: String,
    val specialty: String,
    val latitude: Double,
    val longitude: Double
)
// app/src/main/java/ph/spacall/android/TherapistDetailActivity.kt

package ph.spacall.android

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.BlurTransformation

class TherapistDetailActivity : AppCompatActivity() {

    private var therapistName: String = ""
    private var therapistLat: Double = 0.0
    private var therapistLng: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_therapist_detail)

        // Setup Gold Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbarDetail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        // Get Dynamic Data
        therapistName = intent.getStringExtra("EXTRA_NAME") ?: "Therapist"
        val avatarUrl = intent.getStringExtra("EXTRA_AVATAR")
        therapistLat = intent.getDoubleExtra("EXTRA_LATITUDE", 14.6050)
        therapistLng = intent.getDoubleExtra("EXTRA_LONGITUDE", 120.9900)

        findViewById<TextView>(R.id.tvDetailName).text = therapistName

        // Load Circular Profile Pic
        val ivAvatar = findViewById<ImageView>(R.id.ivDetailAvatar)
        Glide.with(this).load(avatarUrl).circleCrop().into(ivAvatar)

        // --- DUMMY IMAGES FOR LIFESTYLE ---
        val photoClear1 = "https://images.pexels.com/photos/3757942/pexels-photo-3757942.jpeg"
        val photoClear2 = "https://images.pexels.com/photos/3851914/pexels-photo-3851914.jpeg"
        val photoLocked1 = "https://images.pexels.com/photos/3760263/pexels-photo-3760263.jpeg"
        val photoLocked2 = "https://images.pexels.com/photos/3997993/pexels-photo-3997993.jpeg"
        val photoLocked3 = "https://images.pexels.com/photos/4041391/pexels-photo-4041391.jpeg"

        // 1. Load UNBLURRED (Featured) Photos
        val ivFeatured1 = findViewById<ImageView>(R.id.ivFeatured1)
        val ivFeatured2 = findViewById<ImageView>(R.id.ivFeatured2)

        Glide.with(this).load(photoClear1).centerCrop().into(ivFeatured1)
        Glide.with(this).load(photoClear2).centerCrop().into(ivFeatured2)

        // 2. Load BLURRED (Gallery) Photos
        val ivGallery1 = findViewById<ImageView>(R.id.ivGallery1)
        val ivGallery2 = findViewById<ImageView>(R.id.ivGallery2)
        val ivGallery3 = findViewById<ImageView>(R.id.ivGallery3)

        val blurEffect = RequestOptions.bitmapTransform(BlurTransformation(25, 3))

        Glide.with(this).load(photoLocked1).apply(blurEffect).into(ivGallery1)
        Glide.with(this).load(photoLocked2).apply(blurEffect).into(ivGallery2)
        Glide.with(this).load(photoLocked3).apply(blurEffect).into(ivGallery3)

        // 3. Setup Book Now Button
        val btnBookNow = findViewById<Button>(R.id.btnBookNow)
        btnBookNow.setOnClickListener {
            // Navigate to Booking Request Accepted Activity
            val intent = Intent(this, BookingRequestAcceptedActivity::class.java).apply {
                putExtra("EXTRA_NAME", therapistName)
                putExtra("EXTRA_LATITUDE", therapistLat)
                putExtra("EXTRA_LONGITUDE", therapistLng)
            }
            startActivity(intent)
        }
    }
}
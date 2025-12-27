// app/src/main/java/ph/spacall/android/FilterActivity.kt

package ph.spacall.android

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class FilterActivity : AppCompatActivity() {

    private lateinit var backButton: ImageButton
    private lateinit var resetButton: ImageButton
    private lateinit var massageTypeSpinner: Spinner
    private lateinit var genderSpinner: Spinner
    private lateinit var ageSpinner: Spinner
    private lateinit var advancedFilterText: TextView
    private lateinit var continueButton: Button

    // Avatar ImageViews
    private lateinit var avatar1: ImageView
    private lateinit var avatar2: ImageView
    private lateinit var avatar3: ImageView
    private lateinit var avatar4: ImageView

    // Avatar URLs (placeholder images)
    private val avatarUrls = listOf(
        "https://i.pravatar.cc/150?img=1",
        "https://i.pravatar.cc/150?img=2",
        "https://i.pravatar.cc/150?img=3",
        "https://i.pravatar.cc/150?img=4"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        // Initialize views
        initializeViews()

        // Load avatar images
        loadAvatarImages()

        // Setup spinners
        setupSpinners()

        // Setup listeners
        setupListeners()
    }

    private fun initializeViews() {
        backButton = findViewById(R.id.backButton)
        resetButton = findViewById(R.id.resetButton)
        massageTypeSpinner = findViewById(R.id.massageTypeSpinner)
        genderSpinner = findViewById(R.id.genderSpinner)
        ageSpinner = findViewById(R.id.ageSpinner)
        advancedFilterText = findViewById(R.id.advancedFilterText)
        continueButton = findViewById(R.id.continueButton)

        // Avatar ImageViews
        avatar1 = findViewById(R.id.avatar1)
        avatar2 = findViewById(R.id.avatar2)
        avatar3 = findViewById(R.id.avatar3)
        avatar4 = findViewById(R.id.avatar4)
    }

    private fun loadAvatarImages() {
        // Load Avatar 1
        Glide.with(this)
            .load(avatarUrls[0])
            .circleCrop()
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    // If image fails to load, keep the background color
                    avatar1.setBackgroundResource(R.drawable.avatar_circle_1)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })
            .into(avatar1)

        // Load Avatar 2
        Glide.with(this)
            .load(avatarUrls[1])
            .circleCrop()
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    avatar2.setBackgroundResource(R.drawable.avatar_circle_2)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })
            .into(avatar2)

        // Load Avatar 3
        Glide.with(this)
            .load(avatarUrls[2])
            .circleCrop()
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    avatar3.setBackgroundResource(R.drawable.avatar_circle_3)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })
            .into(avatar3)

        // Load Avatar 4
        Glide.with(this)
            .load(avatarUrls[3])
            .circleCrop()
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    avatar4.setBackgroundResource(R.drawable.avatar_circle_4)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })
            .into(avatar4)
    }

    private fun setupSpinners() {
        // Massage Type Spinner
        val massageTypes = arrayOf(
            "Thai Massage",
            "Swedish Massage",
            "Deep Tissue Massage",
            "Hot Stone Massage",
            "Sports Massage",
            "Aromatherapy Massage"
        )
        val massageAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, massageTypes)
        massageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        massageTypeSpinner.adapter = massageAdapter

        // Gender Spinner
        val genders = arrayOf("Female", "Male", "No Preference")
        val genderAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genders)
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner.adapter = genderAdapter

        // Age Range Spinner
        val ageRanges = arrayOf(
            "18-25",
            "26-35",
            "36-45",
            "46-55",
            "56+",
            "No Preference"
        )
        val ageAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, ageRanges)
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        ageSpinner.adapter = ageAdapter
    }

    private fun setupListeners() {
        // Back button
        backButton.setOnClickListener {
            finish()
        }

        // Reset button
        resetButton.setOnClickListener {
            resetFilters()
        }

        // Advanced Filter Text
        advancedFilterText.setOnClickListener {
            Toast.makeText(this, "Advanced Filter Options coming soon", Toast.LENGTH_SHORT).show()
        }

        // Continue button
        continueButton.setOnClickListener {
            val selectedMassage = massageTypeSpinner.selectedItem.toString()
            val selectedGender = genderSpinner.selectedItem.toString()
            val selectedAge = ageSpinner.selectedItem.toString()

            // Navigate to Payment screen
            val intent = Intent(this, PaymentActivity::class.java)
            intent.putExtra("massage_type", selectedMassage)
            intent.putExtra("gender", selectedGender)
            intent.putExtra("age_range", selectedAge)
            startActivity(intent)
        }
    }

    private fun resetFilters() {
        massageTypeSpinner.setSelection(0)
        genderSpinner.setSelection(0)
        ageSpinner.setSelection(0)
        Toast.makeText(this, "Filters reset", Toast.LENGTH_SHORT).show()
    }
}
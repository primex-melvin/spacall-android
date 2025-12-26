package ph.spacall.android

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var backButton: ImageButton
    private lateinit var profileImage: ImageView
    private lateinit var cameraButton: ImageButton
    private lateinit var firstNameInput: EditText
    private lateinit var lastNameInput: EditText
    private lateinit var birthdayButton: Button
    private lateinit var confirmButton: Button

    private var selectedBirthday: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Initialize views
        initializeViews()

        // Setup listeners
        setupListeners()
    }

    private fun initializeViews() {
        backButton = findViewById(R.id.backButton)
        profileImage = findViewById(R.id.profileImage)
        cameraButton = findViewById(R.id.cameraButton)
        firstNameInput = findViewById(R.id.firstNameInput)
        lastNameInput = findViewById(R.id.lastNameInput)
        birthdayButton = findViewById(R.id.birthdayButton)
        confirmButton = findViewById(R.id.confirmButton)
    }

    private fun setupListeners() {
        // Back button
        backButton.setOnClickListener {
            finish()
        }

        // Camera button
        cameraButton.setOnClickListener {
            openCamera()
        }

        // Birthday button - Show bottom sheet dialog
        birthdayButton.setOnClickListener {
            showBirthdayPicker()
        }

        // Confirm button
        confirmButton.setOnClickListener {
            validateAndSubmit()
        }
    }

    private fun openCamera() {
        // TODO: Implement camera/gallery picker
        Toast.makeText(this, "Camera feature coming soon", Toast.LENGTH_SHORT).show()

        // Example: Open camera
        // val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    private fun showBirthdayPicker() {
        val bottomSheet = BirthdayBottomSheetDialog()
        bottomSheet.setOnDateSelectedListener { date ->
            selectedBirthday = date
            birthdayButton.text = date
        }
        bottomSheet.show(supportFragmentManager, BirthdayBottomSheetDialog.TAG)
    }

    private fun validateAndSubmit() {
        val firstName = firstNameInput.text.toString().trim()
        val lastName = lastNameInput.text.toString().trim()

        // Validation
        if (firstName.isEmpty()) {
            Toast.makeText(this, "Please enter your first name", Toast.LENGTH_SHORT).show()
            firstNameInput.requestFocus()
            return
        }

        if (lastName.isEmpty()) {
            Toast.makeText(this, "Please enter your last name", Toast.LENGTH_SHORT).show()
            lastNameInput.requestFocus()
            return
        }

        if (selectedBirthday.isEmpty()) {
            Toast.makeText(this, "Please choose your birthday", Toast.LENGTH_SHORT).show()
            return
        }

        // All validations passed
        Toast.makeText(
            this,
            "Profile created: $firstName $lastName\nBirthday: $selectedBirthday",
            Toast.LENGTH_LONG
        ).show()

        // TODO: Save profile data and navigate to next screen
        // startActivity(Intent(this, NextActivity::class.java))
        // finish()
    }

    companion object {
        private const val CAMERA_REQUEST_CODE = 100
    }
}
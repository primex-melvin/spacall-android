// app/src/main/java/ph/spacall/android/ProfileActivity.kt

package ph.spacall.android

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
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
    private var hasProfileImage: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Initialize views
        initializeViews()

        // Setup listeners
        setupListeners()

        // Setup text watchers for validation
        setupTextWatchers()

        // Initial button state (disabled)
        updateConfirmButtonState()
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
            if (isFormValid()) {
                navigateToUploadId()
            }
        }
    }

    private fun setupTextWatchers() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateConfirmButtonState()
            }
            override fun afterTextChanged(s: Editable?) {}
        }

        firstNameInput.addTextChangedListener(textWatcher)
        lastNameInput.addTextChangedListener(textWatcher)
    }

    private fun isFormValid(): Boolean {
        val firstName = firstNameInput.text.toString().trim()
        val lastName = lastNameInput.text.toString().trim()

        return firstName.isNotEmpty() &&
                lastName.isNotEmpty() &&
                selectedBirthday.isNotEmpty()
    }

    private fun updateConfirmButtonState() {
        val isValid = isFormValid()

        confirmButton.isEnabled = isValid
        confirmButton.alpha = if (isValid) 1.0f else 0.5f
    }

    private fun openCamera() {
        // TODO: Implement camera/gallery picker
        hasProfileImage = true
        Toast.makeText(this, "Camera feature coming soon", Toast.LENGTH_SHORT).show()

        // Example: Open camera
        // val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    private fun showBirthdayPicker() {
        val bottomSheet = BirthdayBottomSheetDialog()

        // When date is selected (on any day click)
        bottomSheet.setOnDateSelectedListener { date ->
            selectedBirthday = date
            birthdayButton.text = date
            // Update button state immediately when date changes
            updateConfirmButtonState()
        }

        // When Save button is clicked in the dialog
        bottomSheet.setOnSaveClickListener {
            // Validate all fields before navigating
            if (isFormValid()) {
                // Dismiss dialog and navigate
                navigateToUploadId()
            } else {
                // Show what's missing
                if (firstNameInput.text.toString().trim().isEmpty()) {
                    Toast.makeText(this, "Please enter your first name", Toast.LENGTH_SHORT).show()
                } else if (lastNameInput.text.toString().trim().isEmpty()) {
                    Toast.makeText(this, "Please enter your last name", Toast.LENGTH_SHORT).show()
                } else if (selectedBirthday.isEmpty()) {
                    Toast.makeText(this, "Please select a birthday", Toast.LENGTH_SHORT).show()
                }
            }
        }

        bottomSheet.show(supportFragmentManager, BirthdayBottomSheetDialog.TAG)
    }

    private fun navigateToUploadId() {
        val firstName = firstNameInput.text.toString().trim()
        val lastName = lastNameInput.text.toString().trim()

        // Log for debugging
        Toast.makeText(
            this,
            "Profile: $firstName $lastName\nBirthday: $selectedBirthday",
            Toast.LENGTH_SHORT
        ).show()

        val intent = Intent(this, UploadIdActivity::class.java)
        intent.putExtra("first_name", firstName)
        intent.putExtra("last_name", lastName)
        intent.putExtra("birthday", selectedBirthday)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            hasProfileImage = true
            // TODO: Handle captured image
            updateConfirmButtonState()
        }
    }

    companion object {
        private const val CAMERA_REQUEST_CODE = 100
    }
}
// app/src/main/java/ph/spacall/android/UploadIdActivity.kt

package ph.spacall.android

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class UploadIdActivity : AppCompatActivity() {

    private lateinit var uploadArea: LinearLayout
    private lateinit var continueButton: Button

    private var selectedDocumentUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_id)

        // Initialize views
        initializeViews()

        // Setup listeners
        setupListeners()
    }

    private fun initializeViews() {
        uploadArea = findViewById(R.id.uploadArea)
        continueButton = findViewById(R.id.continueButton)
    }

    private fun setupListeners() {
        // Upload area click
        uploadArea.setOnClickListener {
            openDocumentPicker()
        }

        // Continue button
        continueButton.setOnClickListener {
            navigateToFilter()
        }
    }

    private fun openDocumentPicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*"
            addCategory(Intent.CATEGORY_OPENABLE)
            // Accept images and PDFs
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf(
                "image/*",
                "application/pdf"
            ))
        }
        startActivityForResult(Intent.createChooser(intent, "Select ID Document"), DOCUMENT_PICKER_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == DOCUMENT_PICKER_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                selectedDocumentUri = uri
                Toast.makeText(this, "Document selected", Toast.LENGTH_SHORT).show()

                // TODO: Update UI to show selected document
                // You can add a preview or change the upload area appearance
            }
        }
    }

    private fun navigateToFilter() {
        // Navigate to Filter Options screen
        val intent = Intent(this, FilterActivity::class.java)
        startActivity(intent)
    }

    companion object {
        private const val DOCUMENT_PICKER_REQUEST = 200
    }
}
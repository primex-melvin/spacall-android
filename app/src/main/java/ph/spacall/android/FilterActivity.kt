package ph.spacall.android

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class FilterActivity : AppCompatActivity() {

    private lateinit var backButton: ImageButton
    private lateinit var resetButton: ImageButton
    private lateinit var massageTypeSpinner: Spinner
    private lateinit var genderSpinner: Spinner
    private lateinit var ageSpinner: Spinner
    private lateinit var advancedFilterLayout: LinearLayout
    private lateinit var continueButton: Button

    private var selectedMassageType: String = ""
    private var selectedGender: String = ""
    private var selectedAgeRange: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        // Initialize views
        initializeViews()

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
        advancedFilterLayout = findViewById(R.id.advancedFilterLayout)
        continueButton = findViewById(R.id.continueButton)
    }

    private fun setupSpinners() {
        // Massage Type options
        val massageTypes = arrayOf(
            "Thai Massage",
            "Swedish Massage",
            "Deep Tissue Massage",
            "Hot Stone Massage",
            "Aromatherapy Massage",
            "Sports Massage",
            "Shiatsu Massage"
        )
        val massageAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, massageTypes)
        massageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        massageTypeSpinner.adapter = massageAdapter

        // Gender options
        val genders = arrayOf(
            "Female",
            "Male",
            "Any"
        )
        val genderAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genders)
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner.adapter = genderAdapter

        // Age Range options
        val ageRanges = arrayOf(
            "18-25",
            "26-35",
            "36-45",
            "46-55",
            "56+"
        )
        val ageAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, ageRanges)
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        ageSpinner.adapter = ageAdapter

        // Set selection listeners
        massageTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedMassageType = massageTypes[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedGender = genders[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        ageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedAgeRange = ageRanges[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
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

        // Advanced filter options
        advancedFilterLayout.setOnClickListener {
            Toast.makeText(this, "Advanced filters coming soon", Toast.LENGTH_SHORT).show()
            // TODO: Navigate to advanced filter screen
            // startActivity(Intent(this, AdvancedFilterActivity::class.java))
        }

        // Continue button
        continueButton.setOnClickListener {
            applyFilters()
        }
    }

    private fun resetFilters() {
        massageTypeSpinner.setSelection(0)
        genderSpinner.setSelection(0)
        ageSpinner.setSelection(0)
        Toast.makeText(this, "Filters reset", Toast.LENGTH_SHORT).show()
    }

    private fun applyFilters() {
        // Show selected filters
        val filterSummary = """
            Filters Applied:
            Massage Type: $selectedMassageType
            Gender: $selectedGender
            Age Range: $selectedAgeRange
        """.trimIndent()

        Toast.makeText(this, filterSummary, Toast.LENGTH_LONG).show()

        // TODO: Save filters and navigate to main app screen
        // val intent = Intent(this, MainActivity::class.java)
        // intent.putExtra("massage_type", selectedMassageType)
        // intent.putExtra("gender", selectedGender)
        // intent.putExtra("age_range", selectedAgeRange)
        // startActivity(intent)
        // finish()
    }
}
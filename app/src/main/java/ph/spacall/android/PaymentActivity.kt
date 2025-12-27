// app/src/main/java/ph/spacall/android/PaymentActivity.kt

package ph.spacall.android

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PaymentActivity : AppCompatActivity() {

    private lateinit var backButton: ImageButton
    private lateinit var resetButton: ImageButton
    private lateinit var savedCardsGroup: RadioGroup
    private lateinit var gcashOption: LinearLayout
    private lateinit var mayaOption: LinearLayout
    private lateinit var gcashLinkedText: TextView
    private lateinit var addNewPaymentButton: LinearLayout
    private lateinit var payNowButton: Button

    private var selectedPaymentMethod: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        // Initialize views
        initializeViews()

        // Setup listeners
        setupListeners()
    }

    private fun initializeViews() {
        backButton = findViewById(R.id.backButton)
        resetButton = findViewById(R.id.resetButton)
        savedCardsGroup = findViewById(R.id.savedCardsGroup)
        gcashOption = findViewById(R.id.gcashOption)
        mayaOption = findViewById(R.id.mayaOption)
        gcashLinkedText = findViewById(R.id.gcashLinkedText)
        addNewPaymentButton = findViewById(R.id.addNewPaymentButton)
        payNowButton = findViewById(R.id.payNowButton)
    }

    private fun setupListeners() {
        // Back button
        backButton.setOnClickListener {
            finish()
        }

        // Reset button
        resetButton.setOnClickListener {
            resetSelection()
        }

        // Saved cards radio group
        savedCardsGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.hdfcCard -> {
                    selectedPaymentMethod = "HDFC Credit Card ****5229"
                }
                R.id.iciciCard -> {
                    selectedPaymentMethod = "ICICI Credit Card ****4421"
                }
            }
        }

        // Gcash option
        gcashOption.setOnClickListener {
            savedCardsGroup.clearCheck()
            selectedPaymentMethod = "Gcash"
            Toast.makeText(this, "Gcash selected", Toast.LENGTH_SHORT).show()
        }

        // Maya option
        mayaOption.setOnClickListener {
            savedCardsGroup.clearCheck()
            selectedPaymentMethod = "Maya"
            Toast.makeText(this, "Maya selected", Toast.LENGTH_SHORT).show()
        }

        // Add new payment option
        addNewPaymentButton.setOnClickListener {
            Toast.makeText(this, "Add new payment option coming soon", Toast.LENGTH_SHORT).show()
        }

        // Pay Now button
        payNowButton.setOnClickListener {
            if (selectedPaymentMethod.isNotEmpty()) {
                // Navigate to Enter Amount screen
                val intent = Intent(this, EnterAmountActivity::class.java)
                intent.putExtra("payment_method", selectedPaymentMethod)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun resetSelection() {
        savedCardsGroup.clearCheck()
        selectedPaymentMethod = ""
        Toast.makeText(this, "Selection cleared", Toast.LENGTH_SHORT).show()
    }
}
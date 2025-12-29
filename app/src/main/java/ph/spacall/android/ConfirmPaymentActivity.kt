// app/src/main/java/ph/spacall/android/ConfirmPaymentActivity.kt

package ph.spacall.android

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ConfirmPaymentActivity : AppCompatActivity() {

    private lateinit var amountText: TextView
    private lateinit var proceedButton: Button

    private var paymentMethod: String = ""
    private var amount: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_payment)

        // Get data from previous screen
        paymentMethod = intent.getStringExtra("payment_method") ?: "MasterCard 8604"
        amount = intent.getStringExtra("amount") ?: "0"

        // Initialize views
        initializeViews()

        // Setup data
        setupData()

        // Setup listeners
        setupListeners()
    }

    private fun initializeViews() {
        amountText = findViewById(R.id.amountText)
        proceedButton = findViewById(R.id.proceedButton)
    }

    private fun setupData() {
        // Set amount
        amountText.text = "₱$amount"
    }

    private fun setupListeners() {
        // Proceed button
        proceedButton.setOnClickListener {
            // Navigate to VIP Success screen
            val intent = Intent(this, VipSuccessActivity::class.java)
            startActivity(intent)
            // Finish this activity so user can't go back
            finish()
        }
    }
}
// app/src/main/java/ph/spacall/android/EnterAmountActivity.kt

package ph.spacall.android

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EnterAmountActivity : AppCompatActivity() {

    private lateinit var backButton: ImageButton
    private lateinit var paymentMethodText: TextView
    private lateinit var amountDisplay: TextView
    private lateinit var balanceText: TextView
    private lateinit var nextButton: Button
    private lateinit var quickAmount50: TextView
    private lateinit var quickAmount100: TextView
    private lateinit var quickAmount200: TextView
    private lateinit var quickAmount300: TextView

    private var currentAmount: String = "0"
    private val balance: Double = 20150.11
    private var paymentMethod: String = "MasterCard 8604"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_amount)

        // Get payment method from previous screen
        paymentMethod = intent.getStringExtra("payment_method") ?: "MasterCard 8604"

        // Initialize views
        initializeViews()

        // Set payment method
        paymentMethodText.text = paymentMethod

        // Setup number pad
        setupNumberPad()

        // Setup listeners
        setupListeners()

        // Initial button state (disabled)
        updateNextButtonState()
    }

    private fun initializeViews() {
        backButton = findViewById(R.id.backButton)
        paymentMethodText = findViewById(R.id.paymentMethodText)
        amountDisplay = findViewById(R.id.amountDisplay)
        balanceText = findViewById(R.id.balanceText)
        nextButton = findViewById(R.id.nextButton)
        quickAmount50 = findViewById(R.id.quickAmount50)
        quickAmount100 = findViewById(R.id.quickAmount100)
        quickAmount200 = findViewById(R.id.quickAmount200)
        quickAmount300 = findViewById(R.id.quickAmount300)

        // Set balance
        balanceText.text = "Current balance: ₱${String.format("%.2f", balance)}"
    }

    private fun setupNumberPad() {
        // Number buttons
        findViewById<TextView>(R.id.btn0).setOnClickListener { addDigit("0") }
        findViewById<TextView>(R.id.btn1).setOnClickListener { addDigit("1") }
        findViewById<TextView>(R.id.btn2).setOnClickListener { addDigit("2") }
        findViewById<TextView>(R.id.btn3).setOnClickListener { addDigit("3") }
        findViewById<TextView>(R.id.btn4).setOnClickListener { addDigit("4") }
        findViewById<TextView>(R.id.btn5).setOnClickListener { addDigit("5") }
        findViewById<TextView>(R.id.btn6).setOnClickListener { addDigit("6") }
        findViewById<TextView>(R.id.btn7).setOnClickListener { addDigit("7") }
        findViewById<TextView>(R.id.btn8).setOnClickListener { addDigit("8") }
        findViewById<TextView>(R.id.btn9).setOnClickListener { addDigit("9") }

        // Delete button
        findViewById<ImageView>(R.id.btnDelete).setOnClickListener { deleteDigit() }
    }

    private fun setupListeners() {
        // Back button
        backButton.setOnClickListener {
            finish()
        }

        // Quick amount buttons
        quickAmount50.setOnClickListener { setAmount("50") }
        quickAmount100.setOnClickListener { setAmount("100") }
        quickAmount200.setOnClickListener { setAmount("200") }
        quickAmount300.setOnClickListener { setAmount("300") }

        // Next button
        nextButton.setOnClickListener {
            if (currentAmount != "0" && currentAmount.isNotEmpty()) {
                val amount = currentAmount.toDoubleOrNull() ?: 0.0
                if (amount > balance) {
                    Toast.makeText(this, "Insufficient balance", Toast.LENGTH_SHORT).show()
                } else if (amount <= 0) {
                    Toast.makeText(this, "Please enter a valid amount", Toast.LENGTH_SHORT).show()
                } else {
                    // Show payment verification drawer
                    showPaymentVerificationDrawer()
                }
            } else {
                Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addDigit(digit: String) {
        if (currentAmount == "0") {
            currentAmount = digit
        } else {
            currentAmount += digit
        }
        updateDisplay()
        updateNextButtonState()
    }

    private fun deleteDigit() {
        if (currentAmount.length > 1) {
            currentAmount = currentAmount.dropLast(1)
        } else {
            currentAmount = "0"
        }
        updateDisplay()
        updateNextButtonState()
    }

    private fun setAmount(amount: String) {
        currentAmount = amount
        updateDisplay()
        updateNextButtonState()
    }

    private fun updateDisplay() {
        amountDisplay.text = "₱$currentAmount"
    }

    private fun updateNextButtonState() {
        val amount = currentAmount.toDoubleOrNull() ?: 0.0
        val isValid = amount > 0 && amount <= balance

        nextButton.isEnabled = isValid
        nextButton.alpha = if (isValid) 1.0f else 0.5f
    }

    private fun showPaymentVerificationDrawer() {
        val bottomSheet = PaymentVerificationBottomSheet()

        // Pass data to bottom sheet
        val bundle = Bundle()
        bundle.putString("payment_method", paymentMethod)
        bundle.putString("amount", currentAmount)
        bottomSheet.arguments = bundle

        bottomSheet.show(supportFragmentManager, PaymentVerificationBottomSheet.TAG)
    }
}
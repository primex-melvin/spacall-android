// app/src/main/java/ph/spacall/android/PaymentVerificationBottomSheet.kt

package ph.spacall.android

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PaymentVerificationBottomSheet : BottomSheetDialogFragment() {

    private lateinit var closeButton: ImageButton
    private lateinit var paymentMethodText: TextView
    private lateinit var topUpAmountText: TextView
    private lateinit var totalAmountText: TextView
    private lateinit var topUpButton: Button

    private var paymentMethod: String = ""
    private var amount: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get data from arguments
        arguments?.let {
            paymentMethod = it.getString("payment_method", "MasterCard 8604")
            amount = it.getString("amount", "0")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_payment_verification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews(view)
        setupData()
        setupListeners()
    }

    private fun initializeViews(view: View) {
        closeButton = view.findViewById(R.id.closeButton)
        paymentMethodText = view.findViewById(R.id.paymentMethodValue)
        topUpAmountText = view.findViewById(R.id.topUpAmountValue)
        totalAmountText = view.findViewById(R.id.totalValue)
        topUpButton = view.findViewById(R.id.topUpButton)
    }

    private fun setupData() {
        // Set payment method
        paymentMethodText.text = paymentMethod

        // Set amounts
        topUpAmountText.text = "₱$amount"
        totalAmountText.text = "₱$amount"

        // Button is always enabled since amount is already validated
        topUpButton.isEnabled = true
        topUpButton.alpha = 1.0f
    }

    private fun setupListeners() {
        // Close button
        closeButton.setOnClickListener {
            dismiss()
        }

        // Top up button
        topUpButton.setOnClickListener {
            // Navigate to Confirm Payment screen
            val intent = Intent(requireContext(), ConfirmPaymentActivity::class.java)
            intent.putExtra("payment_method", paymentMethod)
            intent.putExtra("amount", amount)
            startActivity(intent)

            // Close the drawer
            dismiss()

            // Close the parent activity (Enter Amount page)
            activity?.finish()
        }
    }

    companion object {
        const val TAG = "PaymentVerificationBottomSheet"
    }
}
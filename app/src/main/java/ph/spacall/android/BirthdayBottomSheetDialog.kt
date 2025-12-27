// app/src/main/java/ph/spacall/android/BirthdayBottomSheetDialog.kt

package ph.spacall.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class BirthdayBottomSheetDialog : BottomSheetDialogFragment() {

    private lateinit var yearText: TextView
    private lateinit var monthText: TextView
    private lateinit var previousButton: ImageButton
    private lateinit var nextButton: ImageButton
    private lateinit var calendarGrid: GridLayout
    private lateinit var saveButton: Button

    private val calendar = Calendar.getInstance()
    private var selectedDay = 11  // Default to 11 like in the design
    private var selectedMonth = 6  // Default to July (0-based)
    private var selectedYear = 1995  // Default to 1995

    private var onDateSelectedListener: ((String) -> Unit)? = null
    private var onSaveClickListener: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set calendar to default date
        calendar.set(selectedYear, selectedMonth, selectedDay)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_birthday_picker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews(view)
        setupListeners()
        updateCalendar()
    }

    private fun initializeViews(view: View) {
        yearText = view.findViewById(R.id.yearText)
        monthText = view.findViewById(R.id.monthText)
        previousButton = view.findViewById(R.id.previousButton)
        nextButton = view.findViewById(R.id.nextButton)
        calendarGrid = view.findViewById(R.id.calendarGrid)
        saveButton = view.findViewById(R.id.saveButton)
    }

    private fun setupListeners() {
        previousButton.setOnClickListener {
            calendar.add(Calendar.MONTH, -1)
            updateCalendar()
        }

        nextButton.setOnClickListener {
            calendar.add(Calendar.MONTH, 1)
            updateCalendar()
        }

        saveButton.setOnClickListener {
            // Format: MM/DD/YYYY
            val formattedDate = String.format(
                "%02d/%02d/%04d",
                selectedMonth + 1,
                selectedDay,
                selectedYear
            )

            // First, update the date in the parent activity
            onDateSelectedListener?.invoke(formattedDate)

            // Dismiss the dialog first
            dismiss()

            // Then trigger the save action (navigation will happen in parent)
            // Use post to ensure dialog is dismissed first
            view?.postDelayed({
                onSaveClickListener?.invoke()
            }, 100)
        }
    }

    private fun updateCalendar() {
        // Update year and month display
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)

        yearText.text = currentYear.toString()
        monthText.text = SimpleDateFormat("MMMM", Locale.getDefault()).format(calendar.time)

        // Clear previous calendar
        calendarGrid.removeAllViews()

        // Get calendar info
        val tempCalendar = calendar.clone() as Calendar
        tempCalendar.set(Calendar.DAY_OF_MONTH, 1)
        val firstDayOfWeek = tempCalendar.get(Calendar.DAY_OF_WEEK) - 1
        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        // Add day cells
        var dayCounter = 1
        for (row in 0 until 6) {
            for (col in 0 until 7) {
                val dayView = createDayView()

                val cellIndex = row * 7 + col
                if (cellIndex >= firstDayOfWeek && dayCounter <= daysInMonth) {
                    val day = dayCounter
                    dayView.text = day.toString()
                    dayView.visibility = View.VISIBLE

                    // Check if this is the selected day
                    val isSelected = day == selectedDay &&
                            currentMonth == selectedMonth &&
                            currentYear == selectedYear

                    if (isSelected) {
                        dayView.setBackgroundResource(R.drawable.calendar_day_selected)
                        dayView.setTextColor(resources.getColor(android.R.color.white, null))
                    } else {
                        dayView.setBackgroundResource(R.drawable.calendar_day_background)
                        dayView.setTextColor(resources.getColor(android.R.color.black, null))
                    }

                    dayView.setOnClickListener {
                        // Update selected date
                        selectedDay = day
                        selectedMonth = currentMonth
                        selectedYear = currentYear

                        // Format and notify immediately when day is clicked
                        val formattedDate = String.format(
                            "%02d/%02d/%04d",
                            selectedMonth + 1,
                            selectedDay,
                            selectedYear
                        )
                        onDateSelectedListener?.invoke(formattedDate)

                        // Refresh calendar to show selection
                        updateCalendar()
                    }

                    dayCounter++
                } else {
                    dayView.visibility = View.INVISIBLE
                }

                val params = GridLayout.LayoutParams()
                params.width = 0
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                params.columnSpec = GridLayout.spec(col, 1f)
                params.setMargins(4, 4, 4, 4)
                dayView.layoutParams = params

                calendarGrid.addView(dayView)
            }

            // Break if we've added all days
            if (dayCounter > daysInMonth) break
        }
    }

    private fun createDayView(): TextView {
        return TextView(requireContext()).apply {
            textSize = 18f
            gravity = android.view.Gravity.CENTER
            setPadding(16, 16, 16, 16)
            minHeight = 48  // Minimum touch target
            minWidth = 48
        }
    }

    fun setOnDateSelectedListener(listener: (String) -> Unit) {
        onDateSelectedListener = listener
    }

    fun setOnSaveClickListener(listener: () -> Unit) {
        onSaveClickListener = listener
    }

    companion object {
        const val TAG = "BirthdayBottomSheetDialog"
    }
}
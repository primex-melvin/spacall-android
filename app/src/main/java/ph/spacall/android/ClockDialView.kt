// app/src/main/java/ph/spacall/android/ClockDialView.kt

package ph.spacall.android

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import kotlin.math.cos
import kotlin.math.sin

class ClockDialView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 40f
        color = ContextCompat.getColor(context, R.color.stroke_light)
    }

    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 40f
        strokeCap = Paint.Cap.ROUND
        color = ContextCompat.getColor(context, R.color.gold_primary)
    }

    private val markerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.text_secondary)
    }

    private val iconPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.lime_accent)
    }

    private val iconCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.gold_primary)
    }

    private val iconStrokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 6f
        color = Color.WHITE
    }

    private var progress: Float = 0f
    private val rectF = RectF()

    fun setProgress(progress: Float) {
        this.progress = progress.coerceIn(0f, 1f)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f
        val radius = (Math.min(width, height) / 2f) - 50f

        rectF.set(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius
        )

        // Draw background circle
        canvas.drawCircle(centerX, centerY, radius, backgroundPaint)

        // Draw progress arc
        val sweepAngle = 360f * progress
        canvas.drawArc(rectF, -90f, sweepAngle, false, progressPaint)

        // Draw hour markers
        drawHourMarkers(canvas, centerX, centerY, radius)

        // Draw icon indicators at 12 and 6 o'clock
        drawIconIndicator(canvas, centerX, centerY, radius, -90f) // 12 o'clock
        drawIconIndicator(canvas, centerX, centerY, radius, 90f)  // 6 o'clock
    }

    private fun drawHourMarkers(canvas: Canvas, centerX: Float, centerY: Float, radius: Float) {
        val hours = listOf(12, 3, 6, 9)
        val angles = listOf(-90f, 0f, 90f, 180f)

        val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = 40f
            color = ContextCompat.getColor(context, R.color.text_secondary)
            textAlign = Paint.Align.CENTER
        }

        for (i in hours.indices) {
            val angle = Math.toRadians(angles[i].toDouble())
            val textRadius = radius + 60f

            val x = centerX + (textRadius * cos(angle)).toFloat()
            val y = centerY + (textRadius * sin(angle)).toFloat() + 15f

            canvas.drawText(hours[i].toString(), x, y, textPaint)
        }
    }

    private fun drawIconIndicator(canvas: Canvas, centerX: Float, centerY: Float, radius: Float, angleDegrees: Float) {
        val angle = Math.toRadians(angleDegrees.toDouble())

        val x = centerX + (radius * cos(angle)).toFloat()
        val y = centerY + (radius * sin(angle)).toFloat()

        // Draw outer white circle
        canvas.drawCircle(x, y, 32f, iconStrokePaint)

        // Draw inner gold circle
        canvas.drawCircle(x, y, 26f, iconCirclePaint)

        // Draw smiley face icon
        val faceRadius = 12f

        // Eyes
        canvas.drawCircle(x - 5f, y - 3f, 2f, iconPaint)
        canvas.drawCircle(x + 5f, y - 3f, 2f, iconPaint)

        // Smile
        val smilePath = Path()
        smilePath.addArc(
            x - faceRadius / 2,
            y - faceRadius / 2,
            x + faceRadius / 2,
            y + faceRadius / 2,
            0f,
            180f
        )

        val smilePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = 2f
            color = ContextCompat.getColor(context, R.color.lime_accent)
            strokeCap = Paint.Cap.ROUND
        }
        canvas.drawPath(smilePath, smilePaint)
    }
}
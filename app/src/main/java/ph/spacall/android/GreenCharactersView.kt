// app/src/main/java/ph/spacall/android/GreenCharactersView.kt

package ph.spacall.android

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class GreenCharactersView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val lightGreenPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.parseColor("#C5F5C5")
    }

    private val darkGreenPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.parseColor("#00FF00")
    }

    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 3f
        color = Color.parseColor("#4D8B4D")
    }

    private val blackPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.BLACK
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f

        // Draw light green blob background
        drawBlob(canvas, centerX, centerY)

        // Draw left dark green character
        drawLeftCharacter(canvas, centerX - 80f, centerY + 20f)

        // Draw right light green character
        drawRightCharacter(canvas, centerX + 80f, centerY - 20f)
    }

    private fun drawBlob(canvas: Canvas, centerX: Float, centerY: Float) {
        val path = Path()

        // Create organic blob shape
        path.moveTo(centerX - 150f, centerY - 80f)
        path.cubicTo(
            centerX - 180f, centerY - 100f,
            centerX - 200f, centerY - 20f,
            centerX - 180f, centerY + 60f
        )
        path.cubicTo(
            centerX - 160f, centerY + 100f,
            centerX - 80f, centerY + 120f,
            centerX, centerY + 100f
        )
        path.cubicTo(
            centerX + 80f, centerY + 120f,
            centerX + 160f, centerY + 100f,
            centerX + 180f, centerY + 40f
        )
        path.cubicTo(
            centerX + 200f, centerY - 20f,
            centerX + 180f, centerY - 80f,
            centerX + 140f, centerY - 100f
        )
        path.cubicTo(
            centerX + 100f, centerY - 120f,
            centerX + 20f, centerY - 110f,
            centerX - 60f, centerY - 100f
        )
        path.cubicTo(
            centerX - 100f, centerY - 90f,
            centerX - 150f, centerY - 80f,
            centerX - 150f, centerY - 80f
        )
        path.close()

        canvas.drawPath(path, lightGreenPaint)
        canvas.drawPath(path, strokePaint)
    }

    private fun drawLeftCharacter(canvas: Canvas, centerX: Float, centerY: Float) {
        // Main body circle
        canvas.drawCircle(centerX, centerY, 60f, darkGreenPaint)
        canvas.drawCircle(centerX, centerY, 60f, strokePaint)

        // Hair spikes
        val hairPath = Path()
        hairPath.moveTo(centerX - 15f, centerY - 60f)
        hairPath.lineTo(centerX - 5f, centerY - 80f)
        hairPath.lineTo(centerX + 5f, centerY - 60f)
        hairPath.lineTo(centerX + 15f, centerY - 75f)
        hairPath.lineTo(centerX + 25f, centerY - 60f)
        canvas.drawPath(hairPath, darkGreenPaint)

        // Happy eyes (closed, smiling)
        val eyePath1 = Path()
        eyePath1.moveTo(centerX - 20f, centerY - 10f)
        eyePath1.quadTo(centerX - 15f, centerY - 5f, centerX - 10f, centerY - 10f)

        val eyePath2 = Path()
        eyePath2.moveTo(centerX + 10f, centerY - 10f)
        eyePath2.quadTo(centerX + 15f, centerY - 5f, centerX + 20f, centerY - 10f)

        val eyePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = 3f
            color = Color.BLACK
            strokeCap = Paint.Cap.ROUND
        }
        canvas.drawPath(eyePath1, eyePaint)
        canvas.drawPath(eyePath2, eyePaint)

        // Big smile
        val smilePath = Path()
        smilePath.moveTo(centerX - 25f, centerY + 5f)
        smilePath.quadTo(centerX, centerY + 25f, centerX + 25f, centerY + 5f)
        canvas.drawPath(smilePath, eyePaint)

        // Left arm (raised)
        val leftArmPath = Path()
        leftArmPath.moveTo(centerX - 50f, centerY + 10f)
        leftArmPath.cubicTo(
            centerX - 70f, centerY - 10f,
            centerX - 75f, centerY - 30f,
            centerX - 65f, centerY - 50f
        )
        val armPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = 12f
            color = Color.parseColor("#00DD00")
            strokeCap = Paint.Cap.ROUND
        }
        canvas.drawPath(leftArmPath, armPaint)

        // Right arm (raised)
        val rightArmPath = Path()
        rightArmPath.moveTo(centerX + 50f, centerY + 10f)
        rightArmPath.cubicTo(
            centerX + 55f, centerY - 10f,
            centerX + 50f, centerY - 30f,
            centerX + 45f, centerY - 50f
        )
        canvas.drawPath(rightArmPath, armPaint)

        // Legs
        val leftLegPath = Path()
        leftLegPath.moveTo(centerX - 20f, centerY + 55f)
        leftLegPath.lineTo(centerX - 30f, centerY + 80f)
        leftLegPath.lineTo(centerX - 20f, centerY + 85f)

        val rightLegPath = Path()
        rightLegPath.moveTo(centerX + 20f, centerY + 55f)
        rightLegPath.lineTo(centerX + 30f, centerY + 80f)
        rightLegPath.lineTo(centerX + 40f, centerY + 85f)

        canvas.drawPath(leftLegPath, armPaint)
        canvas.drawPath(rightLegPath, armPaint)
    }

    private fun drawRightCharacter(canvas: Canvas, centerX: Float, centerY: Float) {
        // Main body circle (lighter green)
        val lightCharPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            color = Color.parseColor("#90EE90")
        }
        canvas.drawCircle(centerX, centerY, 50f, lightCharPaint)
        canvas.drawCircle(centerX, centerY, 50f, strokePaint)

        // Happy closed eyes
        val eyePath1 = Path()
        eyePath1.moveTo(centerX - 18f, centerY - 8f)
        eyePath1.quadTo(centerX - 13f, centerY - 3f, centerX - 8f, centerY - 8f)

        val eyePath2 = Path()
        eyePath2.moveTo(centerX + 8f, centerY - 8f)
        eyePath2.quadTo(centerX + 13f, centerY - 3f, centerX + 18f, centerY - 8f)

        val eyePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = 3f
            color = Color.BLACK
            strokeCap = Paint.Cap.ROUND
        }
        canvas.drawPath(eyePath1, eyePaint)
        canvas.drawPath(eyePath2, eyePaint)

        // Wide open smile
        val smilePath = Path()
        smilePath.moveTo(centerX - 20f, centerY + 8f)
        smilePath.quadTo(centerX, centerY + 22f, centerX + 20f, centerY + 8f)
        smilePath.lineTo(centerX + 15f, centerY + 10f)
        smilePath.quadTo(centerX, centerY + 18f, centerX - 15f, centerY + 10f)
        smilePath.close()
        canvas.drawPath(smilePath, blackPaint)

        // Teeth
        val teethPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            color = Color.WHITE
        }
        val teethRect = RectF(centerX - 15f, centerY + 10f, centerX + 15f, centerY + 15f)
        canvas.drawRect(teethRect, teethPaint)

        // Left arm (raised)
        val leftArmPath = Path()
        leftArmPath.moveTo(centerX - 42f, centerY + 8f)
        leftArmPath.cubicTo(
            centerX - 60f, centerY - 10f,
            centerX - 65f, centerY - 25f,
            centerX - 55f, centerY - 45f
        )
        val armPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = 10f
            color = Color.parseColor("#80DD80")
            strokeCap = Paint.Cap.ROUND
        }
        canvas.drawPath(leftArmPath, armPaint)

        // Right arm (raised)
        val rightArmPath = Path()
        rightArmPath.moveTo(centerX + 42f, centerY + 8f)
        rightArmPath.cubicTo(
            centerX + 60f, centerY - 5f,
            centerX + 65f, centerY - 20f,
            centerX + 60f, centerY - 40f
        )
        canvas.drawPath(rightArmPath, armPaint)
    }
}
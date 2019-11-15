package com.revolution.robotics.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.revolution.robotics.R
import com.revolution.robotics.core.extensions.color
import com.revolution.robotics.core.extensions.dimension
import com.revolution.robotics.core.extensions.font
import com.revolution.robotics.utils.Vector

@Suppress("ClickableViewAccessibility")
class DonutSelectorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        const val OPTION_COUNT = 12

        private const val TOUCH_THRESHOLD = 250L

        private const val HALF = 0.5f
        private const val HALF_CIRCLE_DEGREES = 180
        private const val OPTION_WIDTH_DEGREES = 30
        private const val OPTION_WIDTH_HALF_DEGREES = 15
        private const val OPTION_DIVIDER_DEGREES = 0.75f
        private const val ROTATION_CORRECTION = 90
        private const val OPTION_RADIUS_MULTIPLIER = 0.2f
    }

    private var dimension = 0
    private var dimensionHalf = 0.0f
    private var lastTouchTime = 0L
    private var clickVector = Vector()
    private var fontVector = Vector()
    private var selection = Array(OPTION_COUNT) { false }

    private val labelTextSize = context.dimension(R.dimen.dialog_donut_text_size).toFloat()
    private val arcPaint = Paint().apply { color = context.color(R.color.grey_1d) }
    private val textPaint = Paint().apply {
        textAlign = Paint.Align.CENTER
        textSize = labelTextSize
        typeface = context.font(R.font.barlow_medium)
        color = context.color(R.color.white)
    }
    private val selectedArcPaint = Paint().apply { color = context.color(R.color.white) }
    private val selectedTextPaint = Paint(textPaint).apply {
        color = context.color(R.color.grey_28)
    }
    private val holePaint = Paint().apply { color = context.color(R.color.grey_28) }

    var selectionListener: SelectionListener? = null

    fun setSelection(selection: List<Boolean>) {
        selection.forEachIndexed { index, value ->
            this.selection[index] = value
        }
        invalidate()
    }

    fun getSelection() =
        selection.toList()

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            lastTouchTime = System.currentTimeMillis()
        }

        if (event.action == MotionEvent.ACTION_UP && System.currentTimeMillis() - lastTouchTime < TOUCH_THRESHOLD) {
            onClicked(event.x, event.y)
        }

        return true
    }

    private fun onClicked(x: Float, y: Float) {
        clickVector.calculateDistance(dimensionHalf, dimensionHalf, x, y)
        if (clickVector.distance >= dimensionHalf * (1.0f - OPTION_RADIUS_MULTIPLIER * 2)) {
            clickVector.calculateAngle(dimensionHalf, dimensionHalf, x, y)
            var angle = Math.toDegrees(clickVector.angle).toInt() + ROTATION_CORRECTION
            if (clickVector.mirrored) {
                angle += HALF_CIRCLE_DEGREES
            }
            val index =
                if (angle - OPTION_WIDTH_HALF_DEGREES < 0) {
                    selection.size - 1
                } else {
                    (angle - OPTION_WIDTH_HALF_DEGREES) / OPTION_WIDTH_DEGREES
                }
            changeSelection(index)
        }
    }

    private fun changeSelection(index: Int) {
        selection[index] = !selection[index]
        selectionListener?.onSelectionChanged(index, selection[index])
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        if (measuredWidth == 0 && measuredHeight == 0) {
            return
        }

        if (dimension == 0) {
            dimension = measuredWidth
            dimensionHalf = dimension * HALF
        }

        selection.forEachIndexed { index, selected ->
            canvas.drawArc(
                0.0f,
                0.0f,
                dimension.toFloat(),
                dimension.toFloat(),
                index * OPTION_WIDTH_DEGREES + OPTION_WIDTH_HALF_DEGREES + OPTION_DIVIDER_DEGREES - ROTATION_CORRECTION,
                OPTION_WIDTH_DEGREES - OPTION_DIVIDER_DEGREES * 2,
                true,
                if (selected) selectedArcPaint else arcPaint
            )

            fontVector.angle = Math.toRadians(((index + 1) * OPTION_WIDTH_DEGREES - ROTATION_CORRECTION).toDouble())
            fontVector.distance = (dimensionHalf * (1.0f - OPTION_RADIUS_MULTIPLIER)).toDouble()
            canvas.drawText(
                "${index + 1}",
                fontVector.getX() + dimensionHalf,
                fontVector.getY() + dimensionHalf + labelTextSize * HALF,
                if (selected) selectedTextPaint else textPaint
            )
        }

        canvas.drawCircle(
            dimensionHalf,
            dimensionHalf,
            dimensionHalf * (1.0f - OPTION_RADIUS_MULTIPLIER * 2),
            holePaint
        )
    }

    interface SelectionListener {
        fun onSelectionChanged(index: Int, value: Boolean)
    }
}

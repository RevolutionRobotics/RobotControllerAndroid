package com.revolution.robotics.views.slider

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatSeekBar
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.remote.BuildStep
import com.revolution.robotics.core.extensions.dimensionAsFloat
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

class RoboticsSeekBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    AppCompatSeekBar(context, attrs) {

    private val milestonePaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.WHITE
    }

    private val maxWidth: Float = context.dimensionAsFloat(R.dimen.dimen_4dp)
    private val thumbWidth: Float = context.dimensionAsFloat(R.dimen.seekbar_thumb_width)
    private val milestoneHeight = context.dimensionAsFloat(R.dimen.seekbar_progress_height)
    private val topMilestonePadding = context.dimensionAsFloat(R.dimen.seekbar_milestone_top_margin)

    private var isViewMeasured = false
    private val steps: MutableList<BuildStep> = mutableListOf()
    private val milestoneRects: MutableList<RectF> = mutableListOf()

    init {
        thumbOffset = (thumbWidth / 2.0f).roundToInt()
    }

    fun setBuildSteps(steps: List<BuildStep>, startIndex: Int = 0) {
        this.steps.clear()
        this.steps.addAll(steps)
        max = steps.size - 1
        progress = startIndex
        calculateMilestoneRect(steps)
        invalidate()
    }

    fun selectNext() {
        progress = min(max, progress + 1)
    }

    fun selectPrevious() {
        progress = max(0, progress - 1)
    }

    private fun calculateMilestoneRect(steps: List<BuildStep>) {
        milestoneRects.clear()
        val stepWidth = (width - (paddingLeft + paddingRight)) / (steps.size - 1).toFloat()
        val leftMilestoneMargin = max((stepWidth - maxWidth) / 2.0f, 0.0f)
        steps.forEachIndexed { index, buildStep ->
            if (buildStep.milestone != null) {
                milestoneRects.add(
                    RectF(
                        paddingLeft + (index - 1) * stepWidth + leftMilestoneMargin + stepWidth / 2.0f,
                        topMilestonePadding,
                        paddingLeft + (index - 1) * stepWidth + min(
                            stepWidth,
                            maxWidth
                        ) + leftMilestoneMargin + stepWidth / 2.0f,
                        topMilestonePadding + milestoneHeight
                    )
                )
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (width != 0 && height != 0 && !isViewMeasured) {
            calculateMilestoneRect(steps)
            isViewMeasured = true
        }

        milestoneRects.forEach {
            canvas.drawRect(it, milestonePaint)
        }
    }
}

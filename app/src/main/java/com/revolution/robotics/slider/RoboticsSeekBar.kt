package com.revolution.robotics.slider

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
        thumbOffset = Math.round(thumbWidth / 2.0f)
    }

    fun setBuildSteps(steps: List<BuildStep>) {
        this.steps.clear()
        this.steps.addAll(steps)
        max = steps.size
        progress = 0
        calculateMilestoneRect(steps)
        invalidate()
    }

    private fun calculateMilestoneRect(steps: List<BuildStep>) {
        milestoneRects.clear()
        val stepWidth = (width - (paddingLeft + paddingRight)) / steps.size.toFloat()
        val leftMilestoneMargin = Math.max((stepWidth - maxWidth) / 2.0f, 0.0f)
        steps.forEachIndexed { index, buildStep ->
            if (buildStep.milestone != null) {
                milestoneRects.add(
                    RectF(
                        paddingLeft + index * stepWidth + leftMilestoneMargin + stepWidth / 2.0f,
                        topMilestonePadding,
                        paddingLeft + index * stepWidth + Math.min(
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

package com.revolution.robotics.features.challenges.challengeDetail

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatSeekBar
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.remote.ChallengeStep
import com.revolution.robotics.core.extensions.dimensionAsFloat

class ChallengeDetailSeekbar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
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
    private val steps: MutableList<ChallengeStep> = mutableListOf()
    private val challengeStepRects: MutableList<RectF> = mutableListOf()

    init {
        thumbOffset = Math.round(thumbWidth / 2.0f)
    }

    fun setChallengeSteps(steps: List<ChallengeStep>) {
        this.steps.clear()
        this.steps.addAll(steps)
        max = steps.size - 1
        progress = 0
        calculateMilestoneRect(steps)
        invalidate()
    }

    fun selectNext() {
        progress = Math.min(max, progress + 1)
    }

    fun selectPrevious() {
        progress = Math.max(0, progress - 1)
    }

    private fun calculateMilestoneRect(steps: List<ChallengeStep>) {
        challengeStepRects.clear()
        val stepWidth = (width - (paddingLeft + paddingRight)) / (steps.size - 1).toFloat()
        val leftMilestoneMargin = Math.max((stepWidth - maxWidth) / 2.0f, 0.0f)
        steps.forEachIndexed { index, _ ->
            if (index > 0 && index < steps.size - 1) {
                challengeStepRects.add(
                    RectF(
                        paddingLeft + (index - 1) * stepWidth + leftMilestoneMargin + stepWidth / 2.0f,
                        topMilestonePadding,
                        paddingLeft + (index - 1) * stepWidth + Math.min(
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

        challengeStepRects.forEach {
            canvas.drawRect(it, milestonePaint)
        }
    }
}

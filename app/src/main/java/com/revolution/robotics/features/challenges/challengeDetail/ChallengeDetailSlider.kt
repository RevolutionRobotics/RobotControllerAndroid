package com.revolution.robotics.features.challenges.challengeDetail

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import com.revolution.robotics.core.domain.remote.ChallengeStep
import com.revolution.robotics.core.extensions.visible
import com.revolution.robotics.databinding.SliderChallengeDetailBinding
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class ChallengeDetailSlider @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    ConstraintLayout(context, attrs), KodeinAware, SeekBar.OnSeekBarChangeListener {

    override val kodein: Kodein by kodein()
    private val binding = SliderChallengeDetailBinding.inflate(LayoutInflater.from(context), this, true).apply {
        seekbarBuildSteps.setOnSeekBarChangeListener(this@ChallengeDetailSlider)
        imgBack.setOnClickListener { seekbarBuildSteps.selectPrevious() }
        imgNext.setOnClickListener { next() }
        imgFinish.setOnClickListener { buildStepSelectedListener?.onChallengeFinished() }
    }

    private var buildStepSelectedListener: ChallengeStepSelectedListener? = null
    private var buildSteps: List<ChallengeStep>? = null

    fun next() {
        binding.seekbarBuildSteps.selectNext()
    }

    fun setChallengeSteps(steps: List<ChallengeStep>, listener: ChallengeStepSelectedListener) {
        buildSteps = steps
        this.buildStepSelectedListener = listener
        binding.seekbarBuildSteps.apply {
            setChallengeSteps(steps)
            progress = 0
        }
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        binding.imgFinish.visible = buildSteps?.size == progress + 1
        buildSteps?.get(progress)?.let { step ->
            buildStepSelectedListener?.onChallengeStepSelected(step, fromUser)
        }
    }

    override fun onStartTrackingTouch(p0: SeekBar?) = Unit

    override fun onStopTrackingTouch(p0: SeekBar?) = Unit

    interface ChallengeStepSelectedListener {
        fun onChallengeStepSelected(challengeStep: ChallengeStep, fromUser: Boolean)
        fun onChallengeFinished()
    }
}

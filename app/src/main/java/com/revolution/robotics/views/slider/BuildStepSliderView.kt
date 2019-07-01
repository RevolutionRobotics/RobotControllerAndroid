package com.revolution.robotics.views.slider

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import com.revolution.robotics.core.domain.remote.BuildStep
import com.revolution.robotics.core.extensions.visible
import com.revolution.robotics.databinding.SliderBuildStepBinding
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class BuildStepSliderView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    ConstraintLayout(context, attrs), KodeinAware, SeekBar.OnSeekBarChangeListener {

    override val kodein: Kodein by kodein()
    private val binding = SliderBuildStepBinding.inflate(LayoutInflater.from(context), this, true).apply {
        seekbarBuildSteps.setOnSeekBarChangeListener(this@BuildStepSliderView)
        imgBack.setOnClickListener { seekbarBuildSteps.selectPrevious() }
        imgNext.setOnClickListener { onNextClicked() }
        imgFinish.setOnClickListener { buildStepSelectedListener?.onBuildFinished() }
    }

    private var buildStepSelectedListener: BuildStepSelectedListener? = null
    private var buildSteps: List<BuildStep>? = null

    private fun onNextClicked() {
        val currentBuildStep = buildSteps?.get(binding.seekbarBuildSteps.progress)
        val listener = buildStepSelectedListener
        if (currentBuildStep != null && listener != null) {
            if (listener.shouldShowNext(currentBuildStep)) {
                next()
            }
        } else {
            next()
        }
    }

    fun next() {
        binding.seekbarBuildSteps.selectNext()
    }

    fun setBuildSteps(steps: List<BuildStep>, listener: BuildStepSelectedListener, startIndex: Int = 0) {
        buildSteps = steps
        this.buildStepSelectedListener = listener
        binding.seekbarBuildSteps.apply {
            setBuildSteps(steps, startIndex)
            progress = startIndex
        }
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        binding.imgFinish.visible = buildSteps?.size == progress + 1
        buildSteps?.get(progress)?.let { step ->
            buildStepSelectedListener?.onBuildStepSelected(step, fromUser)
        }
    }

    override fun onStartTrackingTouch(p0: SeekBar?) = Unit

    override fun onStopTrackingTouch(p0: SeekBar?) = Unit

    interface BuildStepSelectedListener {
        fun shouldShowNext(buildStep: BuildStep): Boolean
        fun onBuildStepSelected(buildStep: BuildStep, fromUser: Boolean)
        fun onBuildFinished()
    }
}

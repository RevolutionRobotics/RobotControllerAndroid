package com.revolution.robotics.views.slider

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import com.revolution.robotics.core.domain.remote.BuildStep
import com.revolution.robotics.databinding.SliderBuildStepBinding
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.erased.instance

class BuildStepSliderView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    ConstraintLayout(context, attrs), KodeinAware, SeekBar.OnSeekBarChangeListener {

    override val kodein: Kodein by kodein()
    private val viewModel: BuildStepSliderViewModel by instance()
    private val binding = SliderBuildStepBinding.inflate(LayoutInflater.from(context), this, true).apply {
        viewModel = this@BuildStepSliderView.viewModel
        seekbarBuildSteps.setOnSeekBarChangeListener(this@BuildStepSliderView)
        imgBack.setOnClickListener { seekbarBuildSteps.selectPrevious() }
        imgNext.setOnClickListener { seekbarBuildSteps.selectNext() }
    }

    private var buildStepSelectedListener: BuildStepSelectedListener? = null

    fun setBuildSteps(buildSteps: List<BuildStep>, listener: BuildStepSelectedListener, startIndex: Int = 0) {
        this.buildStepSelectedListener = listener
        viewModel.buildSteps.value = buildSteps
        binding.seekbarBuildSteps.apply {
            setBuildSteps(buildSteps, startIndex)
            progress = startIndex
        }
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        viewModel.buildSteps.value?.get(progress)?.let {
            buildStepSelectedListener?.onBuildStepSelected(it, fromUser)
        }
    }

    override fun onStartTrackingTouch(p0: SeekBar?) = Unit

    override fun onStopTrackingTouch(p0: SeekBar?) = Unit

    interface BuildStepSelectedListener {
        fun onBuildStepSelected(buildStep: BuildStep, fromUser: Boolean)
    }
}

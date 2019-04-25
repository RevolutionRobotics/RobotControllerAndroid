package com.revolution.robotics.features.build

import android.os.Bundle
import android.view.View
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.remote.BuildStep
import com.revolution.robotics.core.domain.remote.Milestone
import com.revolution.robotics.databinding.FragmentBuildRobotBinding
import com.revolution.robotics.views.slider.BuildStepSliderView
import org.kodein.di.erased.instance

@Suppress("UnnecessaryApply")
class BuildRobotFragment : BaseFragment<FragmentBuildRobotBinding, BuildRobotViewModel>(R.layout.fragment_build_robot),
    BuildRobotMvp.View, BuildStepSliderView.BuildStepSelectedListener {

    override val viewModelClass = BuildRobotViewModel::class.java
    private val presenter: BuildRobotMvp.Presenter by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.register(this, viewModel)
        // TODO set robotID as an argument parameter
        presenter.loadBuildSteps(110)
        binding?.apply { toolbarViewModel = BuildRobotToolbarViewModel() }
    }

    override fun onBuildStepsLoaded(steps: List<BuildStep>) {
        binding?.seekbar?.setBuildSteps(steps, this)
        onBuildStepSelected(steps.first(), false)
    }

    override fun onBuildStepSelected(buildStep: BuildStep, fromUser: Boolean) {
        viewModel?.buildStep?.value = buildStep
    }
}

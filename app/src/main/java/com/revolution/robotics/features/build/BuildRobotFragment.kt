package com.revolution.robotics.features.build

import android.os.Bundle
import android.view.View
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.remote.BuildStep
import com.revolution.robotics.core.domain.remote.Milestone
import com.revolution.robotics.databinding.FragmentBuildRobotBinding
import com.revolution.robotics.views.slider.BuildStepSliderView

@Suppress("UnnecessaryApply")
class BuildRobotFragment : BaseFragment<FragmentBuildRobotBinding, BuildRobotViewModel>(R.layout.fragment_build_robot),
    BuildStepSliderView.BuildStepSelectedListener {

    override val viewModelClass = BuildRobotViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.apply {
            toolbarViewModel = BuildRobotToolbarViewModel()
            seekbar.setBuildSteps(
                listOf(
                    BuildStep(null, null, 0, 1, null),
                    BuildStep(null, null, 0, 2, null),
                    BuildStep(null, null, 0, 3, null),
                    BuildStep(null, null, 0, 4, null),
                    BuildStep(null, null, 0, 5, Milestone(null, 0, null)),
                    BuildStep(null, null, 0, 6, null),
                    BuildStep(null, null, 0, 7, null),
                    BuildStep(null, null, 0, 8, null),
                    BuildStep(null, null, 0, 9, null),
                    BuildStep(null, null, 0, 10, null),
                    BuildStep(null, null, 0, 11, Milestone(null, 0, null)),
                    BuildStep(null, null, 0, 12, null),
                    BuildStep(null, null, 0, 13, null),
                    BuildStep(null, null, 0, 14, null)
                ), this@BuildRobotFragment
            )
        }
    }

    override fun onBuildStepSelected(buildStep: BuildStep, fromUser: Boolean) {
        // TODO
    }
}

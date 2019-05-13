package com.revolution.robotics.features.build

import android.os.Bundle
import android.view.View
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.BuildStatus
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.domain.remote.BuildStep
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.databinding.FragmentBuildRobotBinding
import com.revolution.robotics.features.bluetooth.BluetoothManager
import com.revolution.robotics.features.build.buildFinished.BuildFinishedDialog
import com.revolution.robotics.features.build.chapterFinished.ChapterFinishedDialog
import com.revolution.robotics.features.build.testing.BumperTestDialog
import com.revolution.robotics.features.build.testing.DrivetrainTestDialog
import com.revolution.robotics.features.build.testing.MotorTestDialog
import com.revolution.robotics.features.build.testing.UltrasonicTestDialog
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig
import com.revolution.robotics.views.slider.BuildStepSliderView
import org.kodein.di.erased.instance
import java.util.Date

@Suppress("UnnecessaryApply")
class BuildRobotFragment : BaseFragment<FragmentBuildRobotBinding, BuildRobotViewModel>(R.layout.fragment_build_robot),
    BuildRobotMvp.View, BuildStepSliderView.BuildStepSelectedListener, DialogEventBus.Listener {

    companion object {
        private const val TEST_TYPE_BUMPER = 1
        private const val TEST_TYPE_ULTRASONIC = 2
        private const val TEST_TYPE_DRIVETRAIN = 3
        private const val TEST_TYPE_MOTOR = 4

        const val DEFAULT_STARTING_INDEX = 1

        private var Bundle.robot by BundleArgumentDelegate.Parcelable<UserRobot>("robot")
    }

    override val viewModelClass = BuildRobotViewModel::class.java
    private val presenter: BuildRobotMvp.Presenter by kodein.instance()
    private val dialogEventBus: DialogEventBus by kodein.instance()
    private val bluetoothManager: BluetoothManager by kodein.instance()

    private var buildStepCount = 0
    private var currentBuildStep: BuildStep? = null
    private var wasRobotFinished = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.apply {
            toolbarViewModel = BuildRobotToolbarViewModel()
            background = ChippedBoxConfig.Builder()
                .chipSize(R.dimen.dialog_chip_size)
                .backgroundColorResource(R.color.grey_28)
                .borderColorResource(R.color.grey_6d)
                .create()
        }

        presenter.register(this, viewModel)
        presenter.loadBuildSteps(arguments?.robot?.id ?: 0)

        bluetoothManager.startConnectionFlow()
        dialogEventBus.register(this)
    }

    override fun onStop() {
        if (!wasRobotFinished) {
            arguments?.robot?.apply {
                actualBuildStep = currentBuildStep?.stepNumber ?: DEFAULT_STARTING_INDEX
                lastModified = Date(System.currentTimeMillis())
                presenter.saveUserRobot(this, false)
            }
        }
        super.onStop()
    }

    override fun onDestroyView() {
        dialogEventBus.unregister(this)
        super.onDestroyView()
    }

    override fun onDialogEvent(event: DialogEvent) {
        if (event == DialogEvent.CHAPTER_FINISHED) {
            getTestingDialog(event.extras.getInt(ChapterFinishedDialog.KEY_TEST_CODE_ID))?.show(fragmentManager)
        }
    }

    private fun getTestingDialog(testCodeId: Int) =
        when (testCodeId) {
            TEST_TYPE_BUMPER -> BumperTestDialog.Build()
            TEST_TYPE_ULTRASONIC -> UltrasonicTestDialog.Build()
            TEST_TYPE_DRIVETRAIN -> DrivetrainTestDialog.Build()
            TEST_TYPE_MOTOR -> MotorTestDialog.Build()
            else -> null
        }

    override fun onBuildStepsLoaded(steps: List<BuildStep>) {
        val startIndex = (arguments?.robot?.actualBuildStep ?: DEFAULT_STARTING_INDEX) - 1
        binding?.seekbar?.setBuildSteps(steps, this, startIndex)
        buildStepCount = steps.size
        onBuildStepSelected(steps[startIndex], true)
    }

    override fun onBuildStepSelected(buildStep: BuildStep, fromUser: Boolean) {
        currentBuildStep?.let { currentBuildStep ->
            if (currentBuildStep.stepNumber < buildStep.stepNumber && !fromUser) {
                currentBuildStep.milestone?.let { ChapterFinishedDialog.newInstance(it).show(fragmentManager) }
            }
        }
        viewModel?.setBuildStep(buildStep, buildStepCount)
        currentBuildStep = buildStep
    }

    override fun onBuildFinished() {
        wasRobotFinished = true
        arguments?.robot?.apply {
            buildStatus = BuildStatus.COMPLETED
            lastModified = Date(System.currentTimeMillis())
            presenter.saveUserRobot(this, true)
        }
        BuildFinishedDialog.newInstance().show(fragmentManager)
    }
}

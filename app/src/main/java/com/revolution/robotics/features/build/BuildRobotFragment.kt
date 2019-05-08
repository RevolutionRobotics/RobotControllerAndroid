package com.revolution.robotics.features.build

import android.os.Bundle
import android.view.View
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.BuildStatus
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.domain.remote.BuildStep
import com.revolution.robotics.core.domain.shared.RobotDescriptor
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.core.utils.dynamicPermissions.BluetoothConnectionFlowHelper
import com.revolution.robotics.databinding.FragmentBuildRobotBinding
import com.revolution.robotics.features.build.buildFinished.BuildFinishedDialog
import com.revolution.robotics.features.build.chapterFinished.ChapterFinishedDialog
import com.revolution.robotics.features.build.testing.MotorTestDialog
import com.revolution.robotics.features.build.testing.MovementTestDialog
import com.revolution.robotics.features.build.testing.SensorTestDialog
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig
import com.revolution.robotics.views.slider.BuildStepSliderView
import org.kodein.di.erased.instance
import java.util.Date

@Suppress("UnnecessaryApply")
class BuildRobotFragment : BaseFragment<FragmentBuildRobotBinding, BuildRobotViewModel>(R.layout.fragment_build_robot),
    BuildRobotMvp.View, BuildStepSliderView.BuildStepSelectedListener, DialogEventBus.Listener,
    BluetoothConnectionFlowHelper.Listener {

    companion object {
        private var Bundle.robot by BundleArgumentDelegate.Parcelable<RobotDescriptor>("robot")
        const val DEFAULT_STARTING_INDEX = 1
    }

    override val viewModelClass = BuildRobotViewModel::class.java
    private val presenter: BuildRobotMvp.Presenter by kodein.instance()
    private val dialogEventBus: DialogEventBus by kodein.instance()
    private val connectionFlowHelper = BluetoothConnectionFlowHelper(kodein)

    private var userRobot: UserRobot? = null
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
        presenter.loadUserRobot(arguments?.robot?.id ?: 0)

        dialogEventBus.register(this)
        connectionFlowHelper.init(fragmentManager, this)
        connectionFlowHelper.startConnectionFlow(requireActivity())
    }

    override fun onStop() {
        if (!wasRobotFinished) {
            if (userRobot == null) {
                presenter.createNewRobot(arguments?.robot, currentBuildStep)
            } else {
                userRobot?.apply {
                    actualBuildStep = currentBuildStep?.stepNumber ?: DEFAULT_STARTING_INDEX
                    lastModified = Date(System.currentTimeMillis())
                    presenter.saveUserRobot(this)
                }
            }
        }
        super.onStop()
    }

    override fun onDestroyView() {
        connectionFlowHelper.shutdown()
        dialogEventBus.unregister(this)
        super.onDestroyView()
    }

    override fun onBluetoothConnected() {
        viewModel?.isBluetoothConnected?.set(true)
    }

    override fun onDialogEvent(event: DialogEvent) {
        if (event == DialogEvent.CHAPTER_FINISHED) {
            getTestingDialog(event.extras.getInt(ChapterFinishedDialog.KEY_TEST_CODE_ID)).show(fragmentManager)
        }
    }

    private fun getTestingDialog(testCodeId: Int) =
        when (testCodeId) {
            1 -> SensorTestDialog.newInstance()
            2 -> MotorTestDialog.newInstance()
            else -> MovementTestDialog.newInstance()
        }

    override fun onUserRobotLoaded(userRobot: UserRobot?) {
        this.userRobot = userRobot
        presenter.loadBuildSteps(arguments?.robot?.id ?: 0)
    }

    override fun onBuildStepsLoaded(steps: List<BuildStep>) {
        val startIndex = (userRobot?.actualBuildStep ?: DEFAULT_STARTING_INDEX) - 1
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
        userRobot?.apply {
            buildStatus = BuildStatus.COMPLETED
            lastModified = Date(System.currentTimeMillis())
            presenter.saveUserRobot(this)
        }
        BuildFinishedDialog.newInstance().show(fragmentManager)
    }
}

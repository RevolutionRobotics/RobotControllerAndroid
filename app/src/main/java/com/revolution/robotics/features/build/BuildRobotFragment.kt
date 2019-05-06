package com.revolution.robotics.features.build

import android.Manifest
import android.os.Bundle
import android.view.View
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.BuildStatus
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.domain.remote.BuildStep
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.core.eventBus.dialog.DialogId
import com.revolution.robotics.core.utils.dynamicPermissions.DynamicPermissionHandler
import com.revolution.robotics.databinding.FragmentBuildRobotBinding
import com.revolution.robotics.features.build.buildFinished.BuildFinishedDialog
import com.revolution.robotics.features.build.connect.ConnectDialog
import com.revolution.robotics.features.build.connectionResult.ConnectionFailedDialog
import com.revolution.robotics.features.build.connectionResult.ConnectionSuccessDialog
import com.revolution.robotics.features.build.chapterFinished.ChapterFinishedDialog
import com.revolution.robotics.features.build.permission.BluetoothPermissionDialog
import com.revolution.robotics.features.build.testing.MotorTestDialog
import com.revolution.robotics.features.build.testing.MovementTestDialog
import com.revolution.robotics.features.build.testing.SensorTestDialog
import com.revolution.robotics.features.build.turnOnTheBrain.TurnOnTheBrainDialog
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig
import com.revolution.robotics.views.slider.BuildStepSliderView
import org.kodein.di.erased.instance
import java.util.Date

@Suppress("UnnecessaryApply")
class BuildRobotFragment : BaseFragment<FragmentBuildRobotBinding, BuildRobotViewModel>(R.layout.fragment_build_robot),
    BuildRobotMvp.View, BuildStepSliderView.BuildStepSelectedListener, DialogEventBus.Listener {

    companion object {
        const val CUSTOM_ROBOT_ID = -1

        val REQUIRED_PERMISSIONS = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    override val viewModelClass = BuildRobotViewModel::class.java
    private val presenter: BuildRobotMvp.Presenter by kodein.instance()
    private val dynamicPermissionHandler: DynamicPermissionHandler by kodein.instance()
    private val dialogEventBus: DialogEventBus by kodein.instance()

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
        presenter.loadUserRobot(getRobotId())
        dialogEventBus.register(this)

        if (dynamicPermissionHandler.hasPermissions(REQUIRED_PERMISSIONS, requireActivity())) {
            TurnOnTheBrainDialog.newInstance().show(fragmentManager)
        } else {
            BluetoothPermissionDialog.newInstance().show(fragmentManager)
        }
    }

    override fun onPause() {
        if (!wasRobotFinished) {
            val robotReference = userRobot
            presenter.saveUserRobot(
                if (robotReference == null) {
                    UserRobot(
                        0,
                        getRobotId(),
                        BuildStatus.IN_PROGRESS,
                        currentBuildStep?.stepNumber ?: 0,
                        Date(System.currentTimeMillis())
                    )
                } else {
                    robotReference.actualBuildStep = currentBuildStep?.stepNumber ?: 0
                    robotReference.lastModified = Date(System.currentTimeMillis())
                    robotReference
                }
            )
        }
        super.onPause()
    }

    override fun onDestroyView() {
        dialogEventBus.unregister(this)
        super.onDestroyView()
    }

    // TODO rework this so we get robotID from arguments
    @Suppress("MagicNumber", "FunctionOnlyReturningConstant")
    private fun getRobotId() = 110

    @Suppress("ComplexMethod")
    override fun onDialogEvent(dialog: DialogId, event: DialogEventBus.Event) {
        if (dialog == DialogId.PERMISSION && event == DialogEventBus.Event.POSITIVE) {
            TurnOnTheBrainDialog.newInstance().show(fragmentManager)
        } else if (dialog == DialogId.TURN_ON_THE_BRAIN && event == DialogEventBus.Event.POSITIVE) {
            ConnectDialog.newInstance().show(fragmentManager)
        } else if (dialog == DialogId.CONNECT && event == DialogEventBus.Event.POSITIVE) {
            viewModel?.isBluetoothConnected?.value = true
            ConnectionSuccessDialog.newInstance().show(fragmentManager)
        } else if (dialog == DialogId.CONNECT && event == DialogEventBus.Event.NEGATIVE) {
            ConnectionFailedDialog.newInstance().show(fragmentManager)
        } else if (dialog == DialogId.CONNECTION_FAILED && event == DialogEventBus.Event.POSITIVE) {
            ConnectDialog.newInstance().show(fragmentManager)
        } else if (dialog == DialogId.CHAPTER_FINISHED && event == DialogEventBus.Event.POSITIVE) {
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
        presenter.loadBuildSteps(getRobotId())
    }

    override fun onBuildStepsLoaded(steps: List<BuildStep>) {
        val startIndex = (userRobot?.actualBuildStep ?: 1) - 1
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
        userRobot?.apply {
            buildStatus = BuildStatus.COMPLETED
            lastModified = Date(System.currentTimeMillis())
            presenter.saveUserRobot(this)
        }
        wasRobotFinished = true
        BuildFinishedDialog.newInstance().show(fragmentManager)
    }
}

package com.revolution.robotics.features.build

import android.os.Bundle
import android.view.View
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.BuildStatus
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.domain.remote.BuildStep
import com.revolution.robotics.core.domain.remote.Milestone
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.databinding.FragmentBuildRobotBinding
import com.revolution.robotics.features.bluetooth.BluetoothManager
import com.revolution.robotics.features.build.buildFinished.BuildFinishedDialog
import com.revolution.robotics.features.build.chapterFinished.ChapterFinishedDialog
import com.revolution.robotics.features.build.testing.buildTest.TestBuildDialog
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig
import com.revolution.robotics.views.slider.BuildStepSliderView
import org.kodein.di.erased.instance
import java.util.Date

@Suppress("UnnecessaryApply")
class BuildRobotFragment : BaseFragment<FragmentBuildRobotBinding, BuildRobotViewModel>(R.layout.fragment_build_robot),
    BuildRobotMvp.View, BuildStepSliderView.BuildStepSelectedListener, DialogEventBus.Listener {

    companion object {
        const val DEFAULT_STARTING_INDEX = 1

        private var Bundle.robot by BundleArgumentDelegate.Parcelable<UserRobot>("robot")
    }

    override val viewModelClass = BuildRobotViewModel::class.java
    private val presenter: BuildRobotMvp.Presenter by kodein.instance()
    private val dialogEventBus: DialogEventBus by kodein.instance()
    private val bluetoothManager: BluetoothManager by kodein.instance()
    private val navigator: Navigator by kodein.instance()
    private val resourceResolver: ResourceResolver by kodein.instance()

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
        presenter.loadBuildSteps(arguments?.robot?.id ?: "")

        dialogEventBus.register(this)
        if (!bluetoothManager.isServiceDiscovered) {
            bluetoothManager.startConnectionFlow()
        }
    }

    override fun goBack() {
        navigator.back(1)
    }

    override fun onBackPressed(): Boolean {
        if (!wasRobotFinished) {
            arguments?.robot?.apply {
                actualBuildStep = currentBuildStep?.stepNumber ?: DEFAULT_STARTING_INDEX
                lastModified = Date(System.currentTimeMillis())
                presenter.saveUserRobot(this, false)
            }
            return true
        }
        return false
    }

    override fun onDestroyView() {
        dialogEventBus.unregister(this)
        presenter.unregister()
        super.onDestroyView()
    }

    override fun onDialogEvent(event: DialogEvent) {
        when (event) {
            DialogEvent.CHAPTER_FINISHED ->
                if (bluetoothManager.isServiceDiscovered) {
                    event.extras.getParcelable<Milestone>(ChapterFinishedDialog.KEY_MILESTONE)?.let { milestone ->
                        TestBuildDialog.newInstance(
                            milestone.testImage ?: "",
                            milestone.testDescription?.getLocalizedString(resourceResolver) ?: "",
                            milestone.testCode ?: ""
                        ).show(
                            fragmentManager
                        )
                    }
                } else {
                    bluetoothManager.startConnectionFlow()
                }
            DialogEvent.SKIP_TESTING, DialogEvent.TEST_WORKS -> binding?.seekbar?.next()
            DialogEvent.LETS_DRIVE -> presenter.letsDrive()
            else -> Unit
        }
    }

    override fun onBuildStepsLoaded(steps: List<BuildStep>) {
        val startIndex = (arguments?.robot?.actualBuildStep ?: DEFAULT_STARTING_INDEX) - 1
        binding?.seekbar?.setBuildSteps(steps, this, startIndex)
        buildStepCount = steps.size
        onBuildStepSelected(steps[startIndex], true)
    }

    override fun shouldShowNext(buildStep: BuildStep): Boolean {
        buildStep.milestone?.let { ChapterFinishedDialog.newInstance(it).show(fragmentManager) }
        return buildStep.milestone == null
    }

    override fun onBuildStepSelected(buildStep: BuildStep, fromUser: Boolean) {
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
    }

    override fun onRobotSaveStarted() {
        BuildFinishedDialog.newInstance().show(fragmentManager)
    }
}

package com.revolution.robotics.features.build

import android.Manifest
import android.os.Bundle
import android.view.View
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.remote.BuildStep
import com.revolution.robotics.core.eventBus.DialogEventBus
import com.revolution.robotics.core.utils.dynamicPermissions.DynamicPermissionHandler
import com.revolution.robotics.databinding.FragmentBuildRobotBinding
import com.revolution.robotics.features.build.connect.ConnectDialog
import com.revolution.robotics.features.build.connectionResult.ConnectionSuccessDialog
import com.revolution.robotics.features.build.permission.BluetoothPermissionDialog
import com.revolution.robotics.features.build.turnOnTheBrain.TurnOnTheBrainDialog
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig
import com.revolution.robotics.views.slider.BuildStepSliderView
import org.kodein.di.erased.instance

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
    private var buildStepCount = 0

    // TODO remove this suppress
    @Suppress("MagicNumber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.register(this, viewModel)
        binding?.apply {
            toolbarViewModel = BuildRobotToolbarViewModel()
            background = ChippedBoxConfig.Builder()
                .chipSize(R.dimen.dialog_chip_size)
                .backgroundColorResource(R.color.grey_28)
                .borderColorResource(R.color.grey_6d)
                .create()
        }

        // TODO set robotID as an argument parameter
        presenter.loadBuildSteps(110)
        dialogEventBus.register(this)

        if (dynamicPermissionHandler.hasPermissions(REQUIRED_PERMISSIONS, requireActivity())) {
            TurnOnTheBrainDialog.newInstance().show(fragmentManager)
        } else {
            BluetoothPermissionDialog.newInstance().show(fragmentManager)
        }
    }

    override fun onDialogEvent(tag: String, event: DialogEventBus.Event) {
        if (tag == BluetoothPermissionDialog::class.java.simpleName && event == DialogEventBus.Event.POSITIVE) {
            TurnOnTheBrainDialog.newInstance().show(fragmentManager)
        } else if (tag == TurnOnTheBrainDialog::class.java.simpleName && event == DialogEventBus.Event.POSITIVE) {
            ConnectDialog.newInstance().show(fragmentManager)
        } else if (tag == ConnectDialog::class.java.simpleName && event == DialogEventBus.Event.POSITIVE) {
            ConnectionSuccessDialog.newInstance().show(fragmentManager)
        } else if (tag == ConnectDialog::class.java.simpleName && event == DialogEventBus.Event.NEGATIVE) {
            // TODO display connection error dialog here
        }
    }

    override fun onDestroyView() {
        dialogEventBus.unregister(this)
        super.onDestroyView()
    }

    override fun onBuildStepsLoaded(steps: List<BuildStep>) {
        binding?.seekbar?.setBuildSteps(steps, this)
        buildStepCount = steps.size
        onBuildStepSelected(steps.first(), false)
    }

    override fun onBuildStepSelected(buildStep: BuildStep, fromUser: Boolean) {
        viewModel?.setBuildStep(buildStep, buildStepCount)
    }
}

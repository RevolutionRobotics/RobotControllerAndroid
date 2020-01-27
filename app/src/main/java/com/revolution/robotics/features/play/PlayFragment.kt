package com.revolution.robotics.features.play

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.ViewDataBinding
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.analytics.Reporter
import com.revolution.robotics.core.domain.remote.Milestone
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.core.utils.AppPrefs
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.databinding.FragmentPlayCoreBinding
import com.revolution.robotics.features.bluetooth.BluetoothConnectionListener
import com.revolution.robotics.features.bluetooth.BluetoothManager
import com.revolution.robotics.features.build.chapterFinished.ChapterFinishedDialog
import com.revolution.robotics.features.build.testing.buildTest.TestBuildDialog
import org.kodein.di.erased.instance

abstract class PlayFragment : BaseFragment<FragmentPlayCoreBinding, PlayViewModel>(R.layout.fragment_play_core),
    PlayMvp.View, BluetoothConnectionListener, DialogEventBus.Listener {

    companion object {
        private var Bundle.robotId by BundleArgumentDelegate.Int("robotId")
    }

    override val viewModelClass = PlayViewModel::class.java
    override val screen = Reporter.Screen.PLAY

    protected val presenter: PlayMvp.Presenter by kodein.instance()
    private val bluetoothManager: BluetoothManager by kodein.instance()
    protected val navigator: Navigator by kodein.instance()
    protected val appPrefs: AppPrefs by kodein.instance()
    private val dialogEventBus: DialogEventBus by kodein.instance()

    abstract fun createContentView(inflater: LayoutInflater, container: ViewGroup?)

    abstract fun getContentBinding(): ViewDataBinding?

    abstract val reverseYAxis: Boolean
    abstract val reverseXAxis: Boolean

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = super.onCreateView(inflater, container, savedInstanceState)
        createContentView(inflater, binding?.contentWrapper)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        presenter.toolbarViewModel = PlayToolbarViewModel()
        presenter.reverseYAxis = reverseYAxis
        presenter.reverseXAxis = reverseXAxis
        binding?.toolbarViewModel = presenter.toolbarViewModel
        arguments?.let {
            presenter.loadRobotName(it.robotId)
        }

        bluetoothManager.registerListener(this)
        if (!bluetoothManager.isServiceDiscovered) {
            bluetoothManager.startConnectionFlow()
        }
        viewModel?.onboaringFinished?.value = appPrefs.finishedOnboarding
        presenter.register(this, viewModel)
        dialogEventBus.register(this)
    }

    override fun onResume() {
        super.onResume()
        if (!appPrefs.finishedOnboarding) {
            reporter.reportEvent(Reporter.Event.DRIVE_BASIC_ROBOT)
        }
    }

    override fun onDestroyView() {
        presenter.unregister()
        bluetoothManager.unregisterListener(this)
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        dialogEventBus.unregister(this)
        super.onDestroyView()
    }

    override fun onControllerLoaded(data: FullControllerData) {
        val controller = data.controller
        if (controller != null) {
            viewModel?.programs?.apply {
                clear()
                addAll(listOf(
                    controller.userController.mapping?.b1?.programName?.let { controller.programs[it] },
                    controller.userController.mapping?.b2?.programName?.let { controller.programs[it] },
                    controller.userController.mapping?.b3?.programName?.let { controller.programs[it] },
                    controller.userController.mapping?.b4?.programName?.let { controller.programs[it] },
                    controller.userController.mapping?.b5?.programName?.let { controller.programs[it] },
                    controller.userController.mapping?.b6?.programName?.let { controller.programs[it] }
                ))
            }
            getContentBinding()?.invalidateAll()
        }
    }

    override fun onControllerLoadingError() {
        navigator.back()
    }

    override fun onBluetoothConnectionStateChanged(
        connected: Boolean,
        serviceDiscovered: Boolean,
        firmwareCompatible: Boolean
    ) {
        if (connected && serviceDiscovered && firmwareCompatible) {
            uploadConfiguration()
        } else if (!connected) {
            presenter.onDeviceDisconnected()
        }
    }

    private fun uploadConfiguration() {
        arguments?.robotId?.let { presenter.loadConfiguration(it) }
    }

    override fun onBackPressed(): Boolean {
        return if (appPrefs.finishedOnboarding) {
            super.onBackPressed()
        } else {
            navigator.popUntil(R.id.mainMenuFragment)
            true
        }
    }

    override fun onDialogEvent(event: DialogEvent) {
        when (event) {
            DialogEvent.FIRMWARE_INCOMPATIBLE_UPDATE_LATER -> uploadConfiguration()
            DialogEvent.FIRMWARE_INCOMPATIBLE_UPDATE -> navigator
            else -> Unit
        }
    }
}

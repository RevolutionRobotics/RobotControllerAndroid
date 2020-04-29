package com.revolution.robotics.features.play

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.databinding.ViewDataBinding
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.analytics.Reporter
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.core.utils.AppPrefs
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.databinding.FragmentPlayCoreBinding
import com.revolution.robotics.databinding.FragmentPlayDriverBinding
import com.revolution.robotics.databinding.FragmentPlayGamerBinding
import com.revolution.robotics.databinding.FragmentPlayMultitaskerBinding
import com.revolution.robotics.features.bluetooth.BluetoothConnectionListener
import com.revolution.robotics.features.bluetooth.BluetoothManager
import com.revolution.robotics.features.controllers.ControllerType
import com.revolution.robotics.features.play.confirmexit.ConformExitControllerDialog
import org.kodein.di.erased.instance


class PlayFragment :
    BaseFragment<FragmentPlayCoreBinding, PlayViewModel>(R.layout.fragment_play_core),
    PlayMvp.View, BluetoothConnectionListener, DialogEventBus.Listener,
    JoystickView.JoystickEventListener {

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

    private lateinit var contentBinding: ViewDataBinding
    private var startTime = System.currentTimeMillis()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        presenter.toolbarViewModel = PlayToolbarViewModel()
        binding?.toolbarViewModel = presenter.toolbarViewModel
        arguments?.let {
            presenter.loadRobotName(it.robotId)
            presenter.loadControllerType(it.robotId)
        }

        bluetoothManager.registerListener(this)
        if (!bluetoothManager.isConnected) {
            bluetoothManager.startConnectionFlow()
        }
        viewModel?.onboaringFinished?.value = appPrefs.finishedOnboarding
        presenter.register(this, viewModel)
        dialogEventBus.register(this)
        startTime = System.currentTimeMillis()
    }

    override fun onDestroyView() {
        presenter.unregister()
        bluetoothManager.unregisterListener(this)
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        dialogEventBus.unregister(this)
        super.onDestroyView()
    }

    override fun onControllerTypeLoaded(controllerType: ControllerType) {
        createContentView(controllerType)
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
            contentBinding.invalidateAll()
        }
    }

    private fun createContentView(controllerType: ControllerType) {
        when (controllerType) {
            ControllerType.GAMER ->
                contentBinding = FragmentPlayGamerBinding.inflate(
                    LayoutInflater.from(context),
                    binding?.contentWrapper,
                    true
                ).apply {
                    viewModel = this@PlayFragment.viewModel
                    joystick.listener = this@PlayFragment
                    presenter.reverseYAxis = true
                    presenter.reverseXAxis = false
                }
            ControllerType.MULTITASKER ->
                contentBinding = FragmentPlayMultitaskerBinding.inflate(
                    LayoutInflater.from(context),
                    binding?.contentWrapper,
                    true
                ).apply {
                    viewModel = this@PlayFragment.viewModel
                    joystick.listener = this@PlayFragment
                    presenter.reverseYAxis = true
                    presenter.reverseXAxis = false
                }
            ControllerType.DRIVER ->
                contentBinding = FragmentPlayDriverBinding.inflate(
                    LayoutInflater.from(context),
                    binding?.contentWrapper,
                    true
                ).apply {
                    viewModel = this@PlayFragment.viewModel
                    leverLeft.onAxisChanged { y -> presenter.onJoystickXAxisChanged(y) }
                    leverRight.onAxisChanged { x -> presenter.onJoystickYAxisChanged(x) }
                    presenter.reverseYAxis = true
                    presenter.reverseXAxis = true
                }
        }
    }

    override fun onControllerLoadingError() {
        navigator.back()
    }

    override fun onBluetoothConnectionStateChanged(
        connected: Boolean,
        firmwareCompatible: Boolean
    ) {
        if (connected && firmwareCompatible) {
            uploadConfiguration()
        } else if (!connected) {
            presenter.onDeviceDisconnected()
        }
    }

    override fun onJoystickPositionChanged(x: Int, y: Int) {
        presenter.onJoystickXAxisChanged(x)
        presenter.onJoystickYAxisChanged(y)
    }

    private fun uploadConfiguration() {
        arguments?.robotId?.let { presenter.loadConfiguration(it) }
    }

    override fun onBackPressed(): Boolean {
        ConformExitControllerDialog().show(fragmentManager)
        return true
    }

    override fun onDialogEvent(event: DialogEvent) {
        when (event) {
            DialogEvent.FIRMWARE_INCOMPATIBLE_UPDATE_LATER -> uploadConfiguration()
            DialogEvent.EXIT_CONTROLLER -> exit()
            else -> Unit
        }
    }

    private fun exit() {
        if (appPrefs.finishedOnboarding) {
            navigator.back()
        } else {
            val duration: Int = ((System.currentTimeMillis() - startTime) / 1000).toInt()
            reporter.reportEvent(Reporter.Event.DRIVE_BASIC_ROBOT, Bundle().apply {
                putInt(Reporter.Parameter.DURATION.parameterName, duration)
            })
            navigator.popUntil(R.id.mainMenuFragment)
        }
    }
}

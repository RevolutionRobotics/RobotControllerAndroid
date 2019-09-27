package com.revolution.robotics.features.play

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.ViewDataBinding
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.utils.AppPrefs
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.databinding.FragmentPlayCoreBinding
import com.revolution.robotics.features.bluetooth.BluetoothConnectionListener
import com.revolution.robotics.features.bluetooth.BluetoothManager
import org.kodein.di.erased.instance

abstract class PlayFragment : BaseFragment<FragmentPlayCoreBinding, PlayViewModel>(R.layout.fragment_play_core),
    PlayMvp.View, BluetoothConnectionListener {

    companion object {
        private var Bundle.configId by BundleArgumentDelegate.Int("configurationId")
    }

    override val viewModelClass = PlayViewModel::class.java

    protected val presenter: PlayMvp.Presenter by kodein.instance()
    private val bluetoothManager: BluetoothManager by kodein.instance()
    protected val navigator: Navigator by kodein.instance()
    protected val appPrefs: AppPrefs by kodein.instance()

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
            presenter.loadRobotName(it.configId)
        }

        bluetoothManager.registerListener(this)
        if (!bluetoothManager.isServiceDiscovered) {
            bluetoothManager.startConnectionFlow()
        }
        viewModel?.onboaringFinished?.value = appPrefs.finishedOnboarding
        presenter.register(this, viewModel)
    }

    override fun onDestroyView() {
        presenter.unregister()
        bluetoothManager.unregisterListener(this)
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
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

    override fun onBluetoothConnectionStateChanged(connected: Boolean, serviceDiscovered: Boolean) {
        if (connected && serviceDiscovered) {
            uploadConfiguration()
        } else if (!connected) {
            presenter.onDeviceDisconnected()
        }
    }

    private fun uploadConfiguration() {
        arguments?.configId?.let { presenter.loadConfiguration(it) }
    }
}

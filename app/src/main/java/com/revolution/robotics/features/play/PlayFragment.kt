package com.revolution.robotics.features.play

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.databinding.FragmentPlayCoreBinding
import com.revolution.robotics.features.bluetooth.BluetoothConnectionListener
import com.revolution.robotics.features.bluetooth.BluetoothManager
import com.revolution.robotics.features.play.configurationBuilder.ConfigurationBuilder
import org.kodein.di.erased.instance

abstract class PlayFragment : BaseFragment<FragmentPlayCoreBinding, PlayViewModel>(R.layout.fragment_play_core),
    PlayMvp.View, BluetoothConnectionListener {

    companion object {
        private var Bundle.configId by BundleArgumentDelegate.Int("configurationId")
    }

    override val viewModelClass = PlayViewModel::class.java

    protected val presenter: PlayMvp.Presenter by kodein.instance()
    private val resourceResolver: ResourceResolver by kodein.instance()
    private val bluetoothManager: BluetoothManager by kodein.instance()

    abstract fun createContentView(inflater: LayoutInflater, container: ViewGroup?)

    abstract fun getContentBinding(): ViewDataBinding?

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = super.onCreateView(inflater, container, savedInstanceState)
        createContentView(inflater, binding?.contentWrapper)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.toolbarViewModel = PlayToolbarViewModel(resourceResolver)

        bluetoothManager.registerListener(this)
        if (!bluetoothManager.isConnected) {
            bluetoothManager.startConnectionFlow()
        }

        presenter.register(this, viewModel)
        arguments?.configId?.let { presenter.loadConfiguration(it) }
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }

    override fun onControllerLoaded(data: FullControllerData) {
        val controller = data.controller
        if (controller != null) {
            val json = ConfigurationBuilder().createConfigurationJson(data)
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

    override fun onBluetoothConnectionStateChanged(connected: Boolean, serviceDiscovered: Boolean) {
        if (connected) {
            presenter.onDeviceConnected(bluetoothManager.bleConnectionHandler)
        } else {
            presenter.onDeviceDisconnected()
        }
    }
}

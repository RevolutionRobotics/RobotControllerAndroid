package com.revolution.robotics.features.play.playGamer

import android.os.Bundle
import android.view.View
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.databinding.FragmentPlayGamerBinding
import com.revolution.robotics.features.bluetooth.BluetoothConnectionListener
import com.revolution.robotics.features.bluetooth.BluetoothManager
import com.revolution.robotics.features.play.JoystickView
import com.revolution.robotics.features.play.PlayMvp
import com.revolution.robotics.features.play.PlayViewModel
import com.revolution.robotics.features.play.PlayToolbarViewModel
import org.kodein.di.erased.instance

class PlayGamerFragment : BaseFragment<FragmentPlayGamerBinding, PlayViewModel>(R.layout.fragment_play_gamer),
    PlayMvp.View, JoystickView.JoystickEventListener, BluetoothConnectionListener {

    override val viewModelClass = PlayViewModel::class.java

    private val presenter: PlayMvp.Presenter by kodein.instance()
    private val resourceResolver: ResourceResolver by kodein.instance()
    private val bluetoothManager: BluetoothManager by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.apply {
            toolbarViewModel = PlayToolbarViewModel(resourceResolver)
            joystick.listener = this@PlayGamerFragment
        }

        bluetoothManager.registerListener(this)
        if (!bluetoothManager.isConnected) {
            bluetoothManager.startConnectionFlow()
        }

        presenter.register(this, viewModel)
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }

    override fun onBluetoothConnectionStateChanged(connected: Boolean, serviceDiscovered: Boolean) {
        if (connected) {
            presenter.onDeviceConnected(bluetoothManager.bleConnectionHandler)
        } else {
            presenter.onDeviceDisconnected()
        }
    }

    override fun onJoystickPositionChanged(x: Int, y: Int) {
        presenter.onJoystickXAxisChanged(x)
        presenter.onJoystickYAxisChanged(y)
    }
}

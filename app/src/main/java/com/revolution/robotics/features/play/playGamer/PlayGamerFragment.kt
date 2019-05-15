package com.revolution.robotics.features.play.playGamer

import android.os.Bundle
import android.view.View
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.databinding.FragmentPlayGamerBinding
import com.revolution.robotics.features.bluetooth.BluetoothManager
import com.revolution.robotics.features.play.PlayViewModel
import com.revolution.robotics.features.play.PlayToolbarViewModel
import org.kodein.di.erased.instance

class PlayGamerFragment : BaseFragment<FragmentPlayGamerBinding, PlayViewModel>(R.layout.fragment_play_gamer) {

    override val viewModelClass = PlayViewModel::class.java

    private val resourceResolver: ResourceResolver by kodein.instance()
    private val bluetoothManager: BluetoothManager by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.toolbarViewModel = PlayToolbarViewModel(resourceResolver)
        if (!bluetoothManager.isConnected) {
            bluetoothManager.startConnectionFlow()
        }
    }
}

package com.revolution.robotics.features.mainmenu.settings.firmware

import android.os.Bundle
import android.view.View
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.databinding.FragmentFirmwareUpdateBinding
import org.kodein.di.erased.instance

class FirmwareFragment :
    BaseFragment<FragmentFirmwareUpdateBinding, FirmwareUpdateViewModel>(R.layout.fragment_firmware_update),
    FirmwareMvp.View {

    override val viewModelClass: Class<FirmwareUpdateViewModel> = FirmwareUpdateViewModel::class.java

    private val presenter: FirmwareMvp.Presenter by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.register(this, viewModel)
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }
}

package com.revolution.robotics.features.mainmenu.settings.firmware

import android.os.Bundle
import android.view.View
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.analytics.Reporter
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.databinding.FragmentFirmwareUpdateBinding
import com.revolution.robotics.features.mainmenu.settings.firmware.update.FirmwareUpdateDialog
import org.kodein.di.erased.instance

class FirmwareFragment :
    BaseFragment<FragmentFirmwareUpdateBinding, FirmwareUpdateViewModel>(R.layout.fragment_firmware_update),
    FirmwareMvp.View {

    override val viewModelClass: Class<FirmwareUpdateViewModel> = FirmwareUpdateViewModel::class.java
    override val screen = Reporter.Screen.FIRMWARE_UPDATE

    private val presenter: FirmwareMvp.Presenter by kodein.instance()
    private val resourceResolver: ResourceResolver by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.toolbarViewModel = FirmwareToolbarViewModel(resourceResolver)
        presenter.register(this, viewModel)
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }

    override fun showFirmwareUpdateDialog() {
        FirmwareUpdateDialog().show(fragmentManager)
    }
}

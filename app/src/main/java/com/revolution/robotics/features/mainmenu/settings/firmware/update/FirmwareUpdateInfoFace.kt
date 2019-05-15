package com.revolution.robotics.features.mainmenu.settings.firmware.update

import com.revolution.robotics.R
import com.revolution.robotics.databinding.DialogFirmwareUpdateBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import org.kodein.di.KodeinAware
import org.kodein.di.LateInitKodein
import org.kodein.di.erased.instance

class FirmwareUpdateInfoFace(dialog: FirmwareUpdateDialog) :
    DialogFace<DialogFirmwareUpdateBinding>(R.layout.dialog_firmware_update, dialog) {

    private var kodein = LateInitKodein()

    private var viewModel: FirmwareUpdateInfoViewModel? = null
    private val presenter: FirmwareUpdateMvp.Presenter by kodein.instance()

    override val dialogFaceButtons: MutableList<DialogButton> =
        mutableListOf(DialogButton(R.string.firmware_check_for_updates, R.drawable.ic_check, true) {
            presenter.onCheckForUpdatesClicked()
        })

    override fun onActivated() {
        binding?.let { binding ->
            kodein.baseKodein = (binding.root.context.applicationContext as KodeinAware).kodein
            binding.viewModel = viewModel
            binding.lifecycleOwner = dialog
        }
        super.onActivated()
    }

    fun setViewModel(viewModel: FirmwareUpdateInfoViewModel) {
        this.viewModel = viewModel
        binding?.viewModel = viewModel
    }
}
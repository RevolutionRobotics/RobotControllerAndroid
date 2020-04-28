package com.revolution.robotics.features.mainmenu.settings.firmware.update

import com.revolution.robotics.R
import com.revolution.robotics.databinding.DialogFirmwareUpdateBinding
import com.revolution.robotics.databinding.DialogRenameRobotBrainBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import org.kodein.di.KodeinAware
import org.kodein.di.LateInitKodein
import org.kodein.di.erased.instance

class ChangeRobotBrainNameFace(dialog: FirmwareUpdateDialog) :
    DialogFace<DialogRenameRobotBrainBinding>(R.layout.dialog_rename_robot_brain, dialog) {

    private var kodein = LateInitKodein()

    private var viewModel: FirmwareUpdateInfoViewModel? = null
    private val presenter: FirmwareUpdateMvp.Presenter by kodein.instance()

    override val dialogFaceButtons: MutableList<DialogButton> =
        mutableListOf(
            DialogButton(R.string.configure_rename, R.drawable.ic_check, true) {
                binding?.txtRobotName?.text?.toString()?.let { presenter.changeRobotName(it) }
            })

    override fun onActivated() {
        super.onActivated()
        binding?.let { binding ->
            kodein.baseKodein = (binding.root.context.applicationContext as KodeinAware).kodein
            binding.viewModel = viewModel
            binding.lifecycleOwner = dialog
        }
    }

    fun setViewModel(viewModel: FirmwareUpdateInfoViewModel) {
        this.viewModel = viewModel
        binding?.viewModel = viewModel
    }
}

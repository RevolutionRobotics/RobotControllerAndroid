package com.revolution.robotics.features.mainmenu.settings.changeServer

import com.revolution.robotics.R
import com.revolution.robotics.databinding.DialogChangeServerBinding
import com.revolution.robotics.views.dialogs.DialogFace

class ChangeServerDialogFace(dialog: ChangeServerDialog) :
    DialogFace<DialogChangeServerBinding>(R.layout.dialog_change_server, dialog) {

    override fun onActivated() {
        binding?.btnGlobal?.setOnClickListener {
            (dialog as ChangeServerDialog).selectServer(false)
        }
        binding?.btnMainlandChina?.setOnClickListener {
            (dialog as ChangeServerDialog).selectServer(true)
        }
    }
}

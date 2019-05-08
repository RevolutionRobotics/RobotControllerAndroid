package com.revolution.robotics.features.configure.contoller

import android.os.Bundle
import android.os.Parcelable
import com.revolution.robotics.R
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.databinding.DialogControllerInfoBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog
import kotlinx.android.parcel.Parcelize

class ControllerInfoDialog : RoboticsDialog() {

    companion object {

        private var Bundle.viewModel by BundleArgumentDelegate.Parcelable<ViewModel>("viewModel")

        fun newInstance(viewModel: ViewModel) =
            ControllerInfoDialog().withArguments {
                it.viewModel = viewModel
            }
    }

    override val hasCloseButton: Boolean = false

    override val dialogFaces: List<DialogFace<*>> = listOf(
        InfoDialogFace(this)
    )
    override val dialogButtons: List<DialogButton> = listOf(
        DialogButton(
            R.string.controller_info_dialog_button_text,
            R.drawable.ic_check,
            true
        ) {
            dismissAllowingStateLoss()
        }
    )

    class InfoDialogFace(dialog: RoboticsDialog) :
        DialogFace<DialogControllerInfoBinding>(
            R.layout.dialog_controller_info, dialog
        ) {
        override fun onActivated() {
            super.onActivated()
            binding?.infoViewModel = dialog?.arguments?.viewModel
        }
    }

    @Parcelize
    data class ViewModel(val title: String, val date: String) : Parcelable
}

package com.revolution.robotics.features.splash.forceUpdate

import android.content.DialogInterface
import com.revolution.robotics.R
import com.revolution.robotics.databinding.DialogForceUpdateBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog
import android.content.Intent
import android.net.Uri
import com.revolution.robotics.BuildConfig


class ForceUpdateDialog : RoboticsDialog() {

    companion object {
        fun newInstance() = ForceUpdateDialog()
    }

    override val hasCloseButton = true
    override val dialogFaces: List<DialogFace<*>> = listOf(ForceUpdateDialogFace())
    override val dialogButtons: List<DialogButton> = listOf(
        DialogButton(R.string.force_update_exit_button_title, R.drawable.ic_back) {
            dismissAllowingStateLoss()
        },
        DialogButton(R.string.force_update_update_button_title, R.drawable.ic_check, true) {
            openPlayStore()
        }
    )

    private fun openPlayStore() {
        val appPackageName = BuildConfig.APPLICATION_ID
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
        } catch (anfe: android.content.ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                )
            )
        }

    }

    class ForceUpdateDialogFace : DialogFace<DialogForceUpdateBinding>(R.layout.dialog_force_update)

    override fun onDismiss(dialog: DialogInterface?) {
        activity?.finish()
        super.onDismiss(dialog)
    }
}

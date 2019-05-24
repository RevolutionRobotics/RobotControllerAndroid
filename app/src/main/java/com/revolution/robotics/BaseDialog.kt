package com.revolution.robotics

import android.content.DialogInterface
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.revolution.robotics.core.extensions.dimension
import com.revolution.robotics.core.extensions.hideSystemUI
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig

open class BaseDialog : DialogFragment() {

    protected val dialogBackgroundConfig = ChippedBoxConfig.Builder()
        .chipSize(R.dimen.dialog_chip_size)
        .backgroundColorResource(R.color.grey_28)

    override fun onResume() {
        super.onResume()
        dialog?.window?.apply {
            setLayout(context.dimension(R.dimen.dialog_width), context.dimension(R.dimen.dialog_height))
            setBackgroundDrawable(null)
        }
    }

    override fun onDismiss(dialog: DialogInterface?) {
        requireActivity().window?.hideSystemUI()
        super.onDismiss(dialog)
    }

    fun show(fragmentManager: FragmentManager?) =
        show(fragmentManager, this.javaClass.simpleName)
}

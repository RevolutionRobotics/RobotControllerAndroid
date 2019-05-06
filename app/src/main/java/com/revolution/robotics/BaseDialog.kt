package com.revolution.robotics

import android.content.DialogInterface
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.revolution.robotics.core.extensions.hideSystemUI

open class BaseDialog : DialogFragment() {

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        requireActivity().window?.hideSystemUI()
    }
}

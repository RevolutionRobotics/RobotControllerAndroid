package com.revolution.robotics

import android.view.WindowManager
import androidx.fragment.app.DialogFragment

open class BaseDialog : DialogFragment() {

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }
}

package com.revolution.robotics.features.coding

import com.revolution.robotics.BaseDialog
import com.revolution.robotics.core.Mvp

interface CodingMvp : Mvp {

    interface View : Mvp.View {
        fun showDialog(baseDialog: BaseDialog)
    }

    interface Presenter : Mvp.Presenter<View, CodingViewModel> {
        fun showProgramsDialog()
    }
}

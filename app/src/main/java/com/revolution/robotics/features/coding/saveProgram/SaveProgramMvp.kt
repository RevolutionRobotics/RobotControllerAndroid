package com.revolution.robotics.features.coding.saveProgram

import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.Mvp
import com.revolution.robotics.core.domain.local.UserProgram

interface SaveProgramMvp : Mvp {

    interface View : Mvp.View {
        fun showError(message: String)
        fun saveProgram(userProgram: UserProgram)
    }

    interface Presenter : Mvp.Presenter<View, ViewModel> {
        fun onDoneButtonClicked(userProgram: UserProgram)
        fun setDefaultUserProgram(userProgram: UserProgram)
    }
}

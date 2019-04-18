package com.revolution.robotics.build.milestone

import androidx.lifecycle.ViewModel
import com.revolution.robotics.Mvp
import com.revolution.robotics.core.domain.remote.Milestone

interface MilestoneFinishedMvp : Mvp {

    interface View : Mvp.View

    interface Presenter : Mvp.Presenter<View, ViewModel> {

        var milestone: Milestone?

        fun uploadTest()
    }
}

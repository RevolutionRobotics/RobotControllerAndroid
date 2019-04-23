package com.revolution.robotics.features.milestoneFinished

import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.Mvp
import com.revolution.robotics.core.domain.remote.Milestone

interface MilestoneFinishedMvp : Mvp {

    interface View : Mvp.View

    interface Presenter : Mvp.Presenter<View, ViewModel> {

        var milestone: Milestone?

        fun uploadTest()
    }
}

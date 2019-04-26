package com.revolution.robotics.features.build.milestoneFinished

import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.domain.remote.Milestone

class MilestoneFinishedPresenter : MilestoneFinishedMvp.Presenter {

    override var view: MilestoneFinishedMvp.View? = null
    override var model: ViewModel? = null

    override var milestone: Milestone? = null

    // TODO Upload test
    override fun uploadTest() = Unit
}

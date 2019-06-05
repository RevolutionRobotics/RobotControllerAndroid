package com.revolution.robotics.features.build.buildFinished

import androidx.lifecycle.ViewModel
import com.revolution.robotics.R
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.core.utils.Navigator

class BuildFinishedPresenter(
    private val navigator: Navigator,
    private val dialogEventBus: DialogEventBus
) : BuildFinishedMvp.Presenter, DialogEventBus.Listener {

    override var view: BuildFinishedMvp.View? = null
    override var model: ViewModel? = null

    override fun register(view: BuildFinishedMvp.View, model: ViewModel?) {
        super.register(view, model)
        dialogEventBus.register(this)
    }

    override fun unregister(view: BuildFinishedMvp.View?) {
        dialogEventBus.unregister(this)
        super.unregister(view)
    }

    override fun navigateHome() {
        navigator.popUntil(R.id.mainMenuFragment)
    }

    override fun onDialogEvent(event: DialogEvent) {
        if (event == DialogEvent.ROBOT_CREATED) {
            view?.showBuildFinishedDialogFace()
        }
    }
}

package com.revolution.robotics.features.build.testing

import com.revolution.robotics.R
import android.os.Bundle
import android.view.View
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.features.build.tips.DialogController
import com.revolution.robotics.features.build.tips.TipsDialogFace
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.RoboticsDialog
import org.kodein.di.erased.instance

abstract class TestDialog : RoboticsDialog(), DialogController, TestMvp.View {

    enum class Source {
        BUILD, CONFIGURE
    }

    override val hasCloseButton = true
    override val dialogButtons = emptyList<DialogButton>()

    abstract val testFileName: String

    private val presenter: TestMvp.Presenter by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.register(this, null)
        presenter.uploadTest(testFileName)
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }

    override fun showTips() {
        activateFace(dialogFaces.first { it is TipsDialogFace })
    }

    override fun onCancelClicked() {
        dismissAllowingStateLoss()
    }

    override fun onRetryClicked() {
        activateFace(dialogFaces.first())
    }

    override fun navigateToCommunity() {
        navigator.navigate(R.id.toCommunity)
    }

    override fun publishDialogEvent(event: DialogEvent) {
        dialogEventBus.publish(event)
    }
}

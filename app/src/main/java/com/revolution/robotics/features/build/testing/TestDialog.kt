package com.revolution.robotics.features.build.testing

import android.os.Bundle
import android.view.View
import com.revolution.robotics.R
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.features.build.tips.DialogController
import com.revolution.robotics.features.build.tips.TipsDialogFace
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.RoboticsDialog
import org.kodein.di.erased.instance

abstract class TestDialog : RoboticsDialog(), DialogController, TestMvp.View {

    companion object {
        const val REPLACEABLE_TEXT_SENSOR = "{SENSOR}"
        const val REPLACEABLE_TEXT_MOTOR = "{MOTOR}"
        const val REPLACEABLE_TEXT_MOTOR_SIDE = "{MOTOR_SIDE}"
        const val REPLACEABLE_TEXT_MOTOR_DIR = "{MOTOR_DIR}"

        const val VALUE_CLOCKWISE = "cw"
        const val VALUE_COUNTER_CLOCKWISE = "ccw"

        const val VALUE_SIDE_LEFT = "left"
        const val VALUE_SIDE_RIGHT = "right"

        var Bundle.portNumber: String by BundleArgumentDelegate.String("portnumber")
    }

    enum class Source {
        BUILD, CONFIGURE
    }

    override val hasCloseButton = true
    override val dialogButtons = emptyList<DialogButton>()

    abstract val testFileName: String

    private val presenter: TestMvp.Presenter by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.register(this, null)
        presenter.uploadTest(testFileName, generateReplaceablePairs())
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
        presenter.uploadTest(testFileName, generateReplaceablePairs())
        activateFace(dialogFaces.first())
    }

    override fun navigateToCommunity() {
        navigator.navigate(R.id.toCommunity)
    }

    override fun publishDialogEvent(event: DialogEvent) {
        dialogEventBus.publish(event)
    }

    abstract fun generateReplaceablePairs(): List<Pair<String, String>>
}

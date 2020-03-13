package com.revolution.robotics.features.build.testing

import android.os.Bundle
import android.view.View
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.extensions.openUrl
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.features.bluetooth.BluetoothManager
import com.revolution.robotics.features.build.tips.DialogController
import com.revolution.robotics.features.community.CommunityFragment
import com.revolution.robotics.features.shared.ErrorHandler
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.RoboticsDialog
import org.kodein.di.erased.instance

abstract class TestDialog : RoboticsDialog(), DialogController, TestMvp.View {

    companion object {
        const val REPLACEABLE_TEXT_SENSOR = "{SENSOR}"
        const val REPLACEABLE_TEXT_MOTOR = "{MOTOR}"
        const val REPLACEABLE_TEXT_MOTOR_SIDE = "{MOTOR_SIDE}"
        const val REPLACEABLE_TEXT_MOTOR_REVERSED = "{MOTOR_REVERSED}"

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
    private val errorHandler: ErrorHandler by kodein.instance()
    private val bluetoothManager: BluetoothManager by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.register(this, null)
        presenter.uploadTest(testFileName, generateReplaceablePairs())
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }

    override fun onCancelClicked() {
        dismissAllowingStateLoss()
    }

    override fun onRetryClicked() {
        if (bluetoothManager.isConnected) {
            presenter.uploadTest(testFileName, generateReplaceablePairs())
            activateFace(dialogFaces.first())
        } else {
            dismissAllowingStateLoss()
            bluetoothManager.startConnectionFlow()
        }
    }

    override fun navigateToCommunity() {
        requireActivity().openUrl(CommunityFragment.COMMUNITY_FORUMS_URL, errorHandler)
    }

    override fun publishDialogEvent(event: DialogEvent) {
        dialogEventBus.publish(event)
    }

    abstract fun generateReplaceablePairs(): List<Pair<String, String>>
}

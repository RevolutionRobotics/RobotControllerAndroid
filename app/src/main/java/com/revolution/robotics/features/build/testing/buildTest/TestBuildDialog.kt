package com.revolution.robotics.features.build.testing.buildTest

import android.os.Bundle
import android.view.View
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.extensions.openUrl
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.features.bluetooth.BluetoothManager
import com.revolution.robotics.features.build.testing.TestDialog
import com.revolution.robotics.features.build.testing.TestLoadingDialogFace
import com.revolution.robotics.features.build.tips.DialogController
import com.revolution.robotics.features.build.tips.TipsDialogFace
import com.revolution.robotics.features.community.CommunityFragment
import com.revolution.robotics.features.shared.ErrorHandler
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog
import org.kodein.di.erased.instance

class TestBuildDialog : RoboticsDialog(), DialogController, TestBuildDialogMvp.View {

    companion object {
        var Bundle.image: String by BundleArgumentDelegate.String("image")
        var Bundle.description: String by BundleArgumentDelegate.String("description")
        var Bundle.code: String by BundleArgumentDelegate.String("code")

        fun newInstance(image: String, description: String, code: String) =
            TestBuildDialog().withArguments { arguments ->
                arguments.image = image
                arguments.description = description
                arguments.code = code
            }
    }

    override val dialogFaces: List<DialogFace<*>> = listOf(
        TestLoadingDialogFace(this),
        TestBuildDialogFace(this),
        TipsDialogFace(TestDialog.Source.BUILD, this, this)
    )
    override val hasCloseButton = true
    override val dialogButtons = emptyList<DialogButton>()

    private val presenter: TestBuildDialogMvp.Presenter by kodein.instance()
    private val errorHandler: ErrorHandler by kodein.instance()
    private val bluetoothManager: BluetoothManager by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.register(this, null)
        arguments?.let { presenter.sendTestCode(it.code) }
    }

    override fun onDestroyView() {
        presenter.unregister(this)
        super.onDestroyView()
    }

    override fun showTips() {
        activateFace(dialogFaces.first { it is TipsDialogFace })
    }

    override fun activateBuildFace() {
        activateFace(dialogFaces.first { it is TestBuildDialogFace })
    }

    override fun onCancelClicked() {
        dismissAllowingStateLoss()
    }

    override fun onRetryClicked() {
        if (bluetoothManager.isConnected) {
            activateFace(dialogFaces.first { it is TestLoadingDialogFace })
            arguments?.let { presenter.sendTestCode(it.code) }
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
}

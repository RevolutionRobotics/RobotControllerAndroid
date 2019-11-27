package com.revolution.robotics.features.coding.test

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.databinding.DialogChallengeDetailFinishedBinding
import com.revolution.robotics.features.challenges.challengeDetail.ChallengeDetailFinishedDialog
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog
import org.kodein.di.erased.instance

class TestCodeDialog : RoboticsDialog(), TestCodeMvp.View {

    companion object {
        const val KEY_PYTHON_CODE = "python_code"
        const val KEY_ROBOT_ID = "robot_id"

        private var Bundle.pythonCode by BundleArgumentDelegate.String(KEY_PYTHON_CODE)
        private var Bundle.robotId by BundleArgumentDelegate.Int(KEY_ROBOT_ID)

        fun newInstance(pythonCode: String, robotId: Int) =
            TestCodeDialog().withArguments { bundle ->
                bundle.pythonCode = pythonCode
                bundle.robotId = robotId
            }
    }

    override val dialogFaces = listOf(TestCodeDialogFace(this))
    override val hasCloseButton = true

    private val presenter: TestCodeMvp.Presenter by kodein.instance()

    override val dialogButtons = listOf(DialogButton(
        text = R.string.blockly_test_dialog_button,
        icon = R.drawable.ic_close,
        isHighlighted = true,
        onClick = { onDoneClicked() }
    ))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.register(this, null)
        arguments?.pythonCode?.let { code ->
            arguments?.robotId?.let { robotId ->
                presenter.runProgram(code, robotId)
            }
        }
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }

    override fun showError(message: String) {
        Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
    }

    override fun showConfigurationSent() {
        Toast.makeText(context, "Config sent", Toast.LENGTH_LONG).show()
    }

    private fun onDoneClicked() {
        dismiss()
    }

    class TestCodeDialogFace(roboticsDialog: RoboticsDialog) :
        DialogFace<DialogChallengeDetailFinishedBinding>(
            R.layout.dialog_test_code,
            roboticsDialog
        )
}
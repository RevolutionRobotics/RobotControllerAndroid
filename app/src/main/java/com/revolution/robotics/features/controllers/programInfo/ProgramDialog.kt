package com.revolution.robotics.features.controllers.programInfo

import android.os.Bundle
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.extensions.gone
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.databinding.DialogProgramInfoBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

sealed class ProgramDialog(mode: Mode) : RoboticsDialog() {

    enum class Mode {
        INFO, ADD_PROGRAM, REMOVE_PROGRAM, COMPATIBILITY_ISSUE
    }

    companion object {
        const val KEY_PROGRAM = "extra-program"
        protected var Bundle.program by BundleArgumentDelegate.Parcelable<UserProgram>("program")
    }

    override val hasCloseButton = mode != Mode.COMPATIBILITY_ISSUE
    override val dialogFaces = listOf(ProgramInfoDialogFace(mode == Mode.COMPATIBILITY_ISSUE))
    override val dialogButtons = listOf(
        DialogButton(R.string.program_info_edit_program, R.drawable.ic_edit) {
            // TODO edit program here
        },
        when (mode) {
            Mode.ADD_PROGRAM ->
                DialogButton(R.string.program_info_add_button, R.drawable.ic_add, true) {
                    dialogEventBus.publish(DialogEvent.ADD_PROGRAM.apply {
                        extras.putParcelable(KEY_PROGRAM, arguments?.program)
                    })
                    dismissAllowingStateLoss()
                }
            Mode.REMOVE_PROGRAM ->
                DialogButton(R.string.program_info_remove_button, R.drawable.ic_close, true) {
                    dismissAllowingStateLoss()
                    dialogEventBus.publish(DialogEvent.REMOVE_PROGRAM)
                }
            Mode.COMPATIBILITY_ISSUE, Mode.INFO ->
                DialogButton(R.string.program_info_compatibility_issue_positive_button, R.drawable.ic_check, true) {
                    dismissAllowingStateLoss()
                }
        }
    )

    inner class ProgramInfoDialogFace(private val showCompatibilityWarning: Boolean) :
        DialogFace<DialogProgramInfoBinding>(R.layout.dialog_program_info) {

        override fun onActivated() {
            super.onActivated()
            binding?.apply {
                compatibilityIssue.gone = !showCompatibilityWarning
                viewModel = arguments?.program
            }
        }
    }

    class Info : ProgramDialog(Mode.INFO) {
        companion object {
            fun newInstance(program: UserProgram) = Info().withArguments { bundle ->
                bundle.program = program
            }
        }
    }

    class Add : ProgramDialog(Mode.ADD_PROGRAM) {
        companion object {
            fun newInstance(program: UserProgram) = Add().withArguments { bundle ->
                bundle.program = program
            }
        }
    }

    class Remove : ProgramDialog(Mode.REMOVE_PROGRAM) {
        companion object {
            fun newInstance(program: UserProgram) = Remove().withArguments { bundle ->
                bundle.program = program
            }
        }
    }

    class CompatibilityIssue : ProgramDialog(Mode.COMPATIBILITY_ISSUE) {
        companion object {
            fun newInstance(program: UserProgram) = CompatibilityIssue().withArguments { bundle ->
                bundle.program = program
            }
        }
    }
}

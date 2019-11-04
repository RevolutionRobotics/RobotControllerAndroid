package com.revolution.robotics.features.coding.saveProgram

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.features.configure.save.SaveDialog
import com.revolution.robotics.features.configure.save.SaveDialogFace
import com.revolution.robotics.views.dialogs.DialogFace
import org.kodein.di.erased.instance

class SaveProgramDialog : SaveDialog(), SaveProgramMvp.View {

    companion object {
        const val KEY_USER_PROGRAM = "userProgram"
        const val KEY_ROBOT_INSTANCE_ID = "robotId"
        const val KEY_ACTION_ID = "actionId"

        private var Bundle.userProgram by BundleArgumentDelegate.ParcelableNullable<UserProgram>(KEY_USER_PROGRAM)
        private var Bundle.robotInstsnceId by BundleArgumentDelegate.Int(KEY_ROBOT_INSTANCE_ID)
        private var Bundle.actionId by BundleArgumentDelegate.Int(KEY_ACTION_ID)

        fun newInstance(userProgram: UserProgram?, robotInstanceId: Int, actionId: Int) =
            SaveProgramDialog().withArguments { bundle ->
                bundle.userProgram = userProgram
                bundle.robotInstsnceId = robotInstanceId
                bundle.actionId = actionId
            }
    }

    private val presenter: SaveProgramMvp.Presenter by kodein.instance()

    override val dialogFace = SaveProgramDialogFace()
    override val dialogFaces: List<DialogFace<*>> = listOf(dialogFace)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.register(this, null)
        arguments?.userProgram?.let {
            presenter.setDefaultUserProgram(it)
        }
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }

    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun saveProgram(userProgram: UserProgram) {
        dismissAllowingStateLoss()
        dialogEventBus.publish(DialogEvent.SAVE_PROGRAM.apply {
            extras.userProgram = userProgram
            extras.actionId = arguments?.actionId ?: -1
        })
    }

    override fun onDoneClicked() {
        val userProgram = arguments?.userProgram ?: UserProgram(robotInstanceId = arguments?.robotInstsnceId ?: 0)
        userProgram.name = dialogFace.getName()
        userProgram.description = dialogFace.getDescription()
        presenter.onDoneButtonClicked(userProgram)
    }

    private fun isNameValid(name: String): Boolean = name.isNotEmpty()

    inner class SaveProgramDialogFace(
        override val titleResource: Int = R.string.dialog_save_program_title,
        override val nameTitleResource: Int = R.string.dialog_save_program_name,
        override val nameHintResource: Int = R.string.dialog_save_program_name_hint,
        override val descriptionTitleResource: Int = R.string.dialog_save_program_description,
        override val descriptionHintResource: Int = R.string.dialog_save_program_description_hint
    ) : SaveDialogFace() {

        override val onNameChangedCallbacks =
            { if (isNameValid(getName())) enableDoneButton() else disableDoneButton() }

        override fun onActivated() {
            super.onActivated()
            binding?.name?.binding?.content?.setText(arguments?.userProgram?.name)
            binding?.description?.binding?.content?.setText(arguments?.userProgram?.description)
            if (isNameValid(getName())) enableDoneButton() else disableDoneButton()
        }
    }
}

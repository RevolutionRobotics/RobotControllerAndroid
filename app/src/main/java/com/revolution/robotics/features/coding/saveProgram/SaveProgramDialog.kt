package com.revolution.robotics.features.coding.saveProgram

import android.os.Bundle
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.features.configure.save.SaveDialog
import com.revolution.robotics.features.configure.save.SaveDialogFace
import com.revolution.robotics.views.dialogs.DialogFace

class SaveProgramDialog : SaveDialog() {

    companion object {
        const val KEY_USER_PROGRAM = "userProgram"

        private var Bundle.userProgram by BundleArgumentDelegate.ParcelableNullable<UserProgram>(KEY_USER_PROGRAM)

        fun newInstance(userProgram: UserProgram?) =
            SaveProgramDialog().withArguments { bundle ->
                bundle.userProgram = userProgram
            }
    }

    override val dialogFace = SaveProgramDialogFace()
    override val dialogFaces: List<DialogFace<*>> = listOf(dialogFace)

    override fun onDoneClicked() {
        dismiss()
        dialogEventBus.publish(DialogEvent.SAVE_PROGRAM.apply {
            val userProgram = arguments?.userProgram ?: UserProgram()
            userProgram.name = dialogFace.getName()
            userProgram.description = dialogFace.getDescription()
            extras.userProgram = userProgram
        })
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

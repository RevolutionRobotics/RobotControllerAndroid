package com.revolution.robotics.features.configure.save

import com.revolution.robotics.R
import com.revolution.robotics.views.dialogs.DialogFace

class SaveRobotDialog : SaveDialog() {

    companion object {
        fun newInstance() = SaveRobotDialog()
    }

    override val dialogFace = SaveRobotDialogFace()
    override val dialogFaces: List<DialogFace<*>> = listOf(dialogFace)

    override fun onDoneClicked() {
        dismiss()
    }

    private fun isNameValid(name: String): Boolean = name.isNotEmpty()

    inner class SaveRobotDialogFace(
        override val titleResource: Int = R.string.save_robot_dialog_title,
        override val nameTitleResource: Int = R.string.save_robot_dialog_name_title,
        override val nameHintResource: Int = R.string.save_robot_dialog_name_hint,
        override val descriptionTitleResource: Int = R.string.save_dialog_description_title,
        override val descriptionHintResource: Int = R.string.save_robot_dialog_description_hint
    ) : SaveDialogFace() {
        override val onNameChangedCallbacks =
            { if (isNameValid(getName())) enableDoneButton() else disableDoneButton() }
    }
}

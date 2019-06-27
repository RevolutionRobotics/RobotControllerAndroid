package com.revolution.robotics.features.configure.save

import android.os.Bundle
import com.revolution.robotics.R
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.views.dialogs.DialogFace

class SaveRobotDialog : SaveDialog() {

    companion object {

        const val KEY_NAME = "name"
        const val KEY_DESCRIPTION = "description"

        private var Bundle.name: String by BundleArgumentDelegate.String(KEY_NAME)
        private var Bundle.description: String by BundleArgumentDelegate.String(KEY_DESCRIPTION)

        fun newInstance(name: String, description: String) = SaveRobotDialog().withArguments { bundle ->
            bundle.name = name
            bundle.description = description
        }
    }

    override val dialogFace = SaveRobotDialogFace()
    override val dialogFaces: List<DialogFace<*>> = listOf(dialogFace)

    override fun onDoneClicked() {
        dismissAllowingStateLoss()
        dialogEventBus.publish(DialogEvent.SAVE_ROBOT.apply {
            extras.description = dialogFace.getDescription()
            extras.name = dialogFace.getName()
        })
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
            {
                if (isNameValid(getName())) enableDoneButton() else disableDoneButton()
            }

        override fun onActivated() {
            super.onActivated()
            binding?.name?.binding?.content?.setText(arguments?.name)
            binding?.description?.binding?.content?.setText(arguments?.description)
            if (isNameValid(getName())) enableDoneButton() else disableDoneButton()
        }
    }
}

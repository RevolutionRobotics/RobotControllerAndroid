package com.revolution.robotics.features.build.chapterFinished

import android.os.Bundle
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.remote.Milestone
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.databinding.DialogChapterFinishedBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

class ChapterFinishedDialog : RoboticsDialog() {

    companion object {
        const val KEY_MILESTONE = "milestone"

        private var Bundle.milestone by BundleArgumentDelegate.Parcelable<Milestone>(KEY_MILESTONE)

        fun newInstance(milestone: Milestone): ChapterFinishedDialog = ChapterFinishedDialog().withArguments {
            it.milestone = milestone
        }
    }

    override val hasCloseButton = true
    override val dialogFaces: List<DialogFace<*>> = listOf(ChapterFinishedDialogFace())
    override val dialogButtons: List<DialogButton> = listOf(
        DialogButton(R.string.build_chapter_finish_dialog_button_home, R.drawable.ic_home) {
            dismissAllowingStateLoss()
            navigator.popUntil(R.id.mainMenuFragment)
        },
        DialogButton(R.string.build_chapter_finish_dialog_button_next_chapter, R.drawable.ic_skip) {
            dismissAllowingStateLoss()
            dialogEventBus.publish(DialogEvent.SKIP_TESTING)
        },
        DialogButton(R.string.build_chapter_finish_dialog_button_test, R.drawable.ic_test, true) {
            startTestingFlow()
        }
    )

    private fun startTestingFlow() {
        dismissAllowingStateLoss()
        dialogEventBus.publish(DialogEvent.CHAPTER_FINISHED.apply {
            arguments?.milestone?.let { extras.putParcelable(KEY_MILESTONE, it) }
        })
    }

    class ChapterFinishedDialogFace : DialogFace<DialogChapterFinishedBinding>(R.layout.dialog_chapter_finished)
}

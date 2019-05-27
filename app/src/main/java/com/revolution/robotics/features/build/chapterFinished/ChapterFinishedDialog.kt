package com.revolution.robotics.features.build.chapterFinished

import android.os.Bundle
import android.view.View
import com.revolution.robotics.R
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.RoboticsDialog
import com.revolution.robotics.core.domain.remote.Milestone
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.databinding.DialogChapterFinishedBinding
import com.revolution.robotics.views.dialogs.DialogFace
import org.kodein.di.erased.instance

class ChapterFinishedDialog : RoboticsDialog(), ChapterFinishedMvp.View {

    companion object {
        const val KEY_TEST_CODE_ID = "test-code-id"

        private var Bundle.milestone by BundleArgumentDelegate.Parcelable<Milestone>("milestone")

        fun newInstance(milestone: Milestone): ChapterFinishedDialog = ChapterFinishedDialog().withArguments {
            it.milestone = milestone
        }
    }

    private val presenter: ChapterFinishedMvp.Presenter by kodein.instance()

    override val hasCloseButton = true
    override val dialogFaces: List<DialogFace<*>> = listOf(
        ChapterFinishedDialogFace()
    )
    override val dialogButtons: List<DialogButton> = listOf(
        DialogButton(
            R.string.build_chapter_finish_dialog_button_home,
            R.drawable.ic_home
        ) {
            dismissAllowingStateLoss()
            presenter.navigateHome()
        },
        DialogButton(
            R.string.build_chapter_finish_dialog_button_next_chapter,
            R.drawable.ic_skip
        ) {
            dismissAllowingStateLoss()
            dialogEventBus.publish(DialogEvent.SKIP_TESTING)
        },
        DialogButton(
            R.string.build_chapter_finish_dialog_button_test,
            R.drawable.ic_test,
            true
        ) {
            presenter.uploadTest()
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.milestone = arguments?.milestone
        presenter.register(this, null)
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }

    override fun onTestUploaded() {
        dismissAllowingStateLoss()
        dialogEventBus.publish(DialogEvent.CHAPTER_FINISHED.apply {
            arguments?.milestone?.let { extras.putInt(KEY_TEST_CODE_ID, it.testCodeId) }
        })
    }

    class ChapterFinishedDialogFace : DialogFace<DialogChapterFinishedBinding>(R.layout.dialog_chapter_finished)
}

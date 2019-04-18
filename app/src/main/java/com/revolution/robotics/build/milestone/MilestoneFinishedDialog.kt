package com.revolution.robotics.build.milestone

import android.os.Bundle
import android.view.View
import com.revolution.robotics.R
import com.revolution.robotics.core.dialogs.DialogButton
import com.revolution.robotics.core.dialogs.RoboticsDialog
import com.revolution.robotics.core.domain.remote.Milestone
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.databinding.DialogFaceMilestoneFinishedBinding
import org.kodein.di.erased.instance

class MilestoneFinishedDialog : RoboticsDialog(), MilestoneFinishedMvp.View {

    companion object {
        private var Bundle.milestone by BundleArgumentDelegate.Parcelable<Milestone>("milestone")

        fun newInstance(milestone: Milestone): MilestoneFinishedDialog = MilestoneFinishedDialog().withArguments {
            it.milestone = milestone
        }
    }

    override val dialogFaces: List<DialogFace<*>> = listOf(MilestoneFinishedDialogFace())
    override val dialogButtons: List<DialogButton> = listOf(
        DialogButton(
            R.string.build_chapter_finish_dialog_button_home,
            R.drawable.ic_home,
            false
        ) {
            requireActivity().onBackPressed()
            dismissAllowingStateLoss()
        },
        DialogButton(
            R.string.build_chapter_finish_dialog_button_next_chapter,
            R.drawable.ic_next,
            false
        ) {
            dismissAllowingStateLoss()
        },
        DialogButton(
            R.string.build_chapter_finish_dialog_button_test,
            R.drawable.ic_test,
            true
        ) {
            presenter.uploadTest()
        }
    )

    private val presenter: MilestoneFinishedMvp.Presenter by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.milestone = arguments?.milestone
        presenter.register(this, null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unregister()
    }

    class MilestoneFinishedDialogFace :
        DialogFace<DialogFaceMilestoneFinishedBinding>(R.layout.dialog_face_milestone_finished)
}
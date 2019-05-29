package com.revolution.robotics.features.coding.programs

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.databinding.DialogProgramsBinding
import com.revolution.robotics.features.coding.programs.adapter.ProgramViewModel
import com.revolution.robotics.features.coding.programs.adapter.ProgramsAdapter
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog
import org.kodein.di.erased.instance

class ProgramsDialog : RoboticsDialog(), ProgramsMvp.View {

    companion object {
        const val EXTRA_PROGRAM_XML = "program-xml"

        fun newInstance() = ProgramsDialog()
    }

    private val presenter: ProgramsMvp.Presenter by kodein.instance()
    private val dialogFace = ProgramsDialogFace()

    override val hasCloseButton = true
    override val dialogFaces: List<DialogFace<*>> = listOf(dialogFace)
    override val dialogButtons = emptyList<DialogButton>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.register(this, null)
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }

    override fun onProgramsLoaded(programs: List<ProgramViewModel>) {
        dialogFace.onProgramsLoaded(programs)
    }

    override fun onProgramSelected(program: UserProgram) {
        dismissAllowingStateLoss()
        dialogEventBus.publish(DialogEvent.LOAD_PROGRAM.apply {
            extras.putString(EXTRA_PROGRAM_XML, program.xml)
        })
    }

    private inner class ProgramsDialogFace : DialogFace<DialogProgramsBinding>(R.layout.dialog_programs) {

        private val adapter = ProgramsAdapter()

        override fun onActivated() {
            binding?.recycler?.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = this@ProgramsDialogFace.adapter
            }
            presenter.loadPrograms()
        }

        fun onProgramsLoaded(programs: List<ProgramViewModel>) {
            adapter.setItems(programs)
        }
    }
}

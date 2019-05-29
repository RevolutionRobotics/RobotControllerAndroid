package com.revolution.robotics.features.coding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.blockly.DialogFactory
import com.revolution.robotics.blockly.utils.JavascriptResultHandler
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.databinding.FragmentCodingBinding
import com.revolution.robotics.features.coding.programs.ProgramsDialog
import com.revolution.robotics.features.coding.saveProgram.SaveProgramDialog
import com.revolution.robotics.features.controllers.programInfo.ProgramDialog
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig
import org.kodein.di.erased.instance

class CodingFragment : BaseFragment<FragmentCodingBinding, CodingViewModel>(R.layout.fragment_coding), CodingMvp.View,
    DialogEventBus.Listener {

    companion object {
        private const val BLOCKLY_LOCATION = "file:///android_asset/blockly/webview.html"
    }

    override val viewModelClass: Class<CodingViewModel> = CodingViewModel::class.java

    private val presenter: CodingMvp.Presenter by kodein.instance()
    private val javascriptResultHandler: JavascriptResultHandler by kodein.instance()
    private val dialogEventBus: DialogEventBus by kodein.instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        super.onCreateView(inflater, container, savedInstanceState).apply {
            binding?.buttonBackground = ChippedBoxConfig.Builder()
                .chipSize(R.dimen.dimen_12dp)
                .backgroundColorResource(R.color.grey_28)
                .create()
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.viewBlockly?.init(BLOCKLY_LOCATION, DialogFactory(javascriptResultHandler, fragmentManager))
        presenter.register(this, viewModel)
        dialogEventBus.register(this)
    }

    override fun onDestroyView() {
        presenter.unregister()
        dialogEventBus.unregister(this)
        super.onDestroyView()
    }

    // TODO remove this suppress
    @Suppress("UnusedPrivateMember")
    override fun onDialogEvent(event: DialogEvent) {
        when (event) {
            DialogEvent.SHOW_PROGRAM_INFO -> {
                val program = event.extras.getParcelable<UserProgram>(ProgramsDialog.KEY_PROGRAM)
                program?.let { showDialog(ProgramDialog.Load.newInstance(it)) }
            }
            DialogEvent.LOAD_PROGRAM -> {
                val program = event.extras.getParcelable<UserProgram>(ProgramDialog.KEY_PROGRAM)
                // TODO load program into blockly
            }
            DialogEvent.DELETE_PROGRAM -> {
                val program = event.extras.getParcelable<UserProgram>(ProgramDialog.KEY_PROGRAM)
                // TODO delete program here
            }
            DialogEvent.SAVE_PROGRAM -> {
                val name = event.extras.getString(SaveProgramDialog.KEY_NAME)
                val description = event.extras.getString(SaveProgramDialog.KEY_DESCRIPTION)
                viewModel?.programName?.set(name)
                // TODO save program from blockly
            }
            else -> Unit
        }
    }
}

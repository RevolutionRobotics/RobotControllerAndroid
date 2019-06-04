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
import java.io.File

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

    override fun onDialogEvent(event: DialogEvent) {
        when (event) {
            DialogEvent.SHOW_PROGRAM_INFO -> {
                val program = event.extras.getParcelable<UserProgram>(ProgramsDialog.KEY_PROGRAM)
                program?.let { showDialog(ProgramDialog.Load.newInstance(it)) }
            }
            DialogEvent.LOAD_PROGRAM -> {
                val program = event.extras.getParcelable<UserProgram>(ProgramDialog.KEY_PROGRAM)
                viewModel?.userProgram = program
                viewModel?.programName?.set(program?.name)
                program?.xml?.let {
                    binding?.viewBlockly?.loadProgram(File(it).readText(Charsets.UTF_8))
                }
            }
            DialogEvent.DELETE_PROGRAM -> {
                viewModel?.userProgram = null
                viewModel?.programName?.set("")
                event.extras.getParcelable<UserProgram>(ProgramDialog.KEY_PROGRAM)?.let {
                    presenter.removeProgram(it)
                }
            }
            DialogEvent.SAVE_PROGRAM -> {
                val userProgram = event.extras.getParcelable<UserProgram?>(SaveProgramDialog.KEY_USER_PROGRAM)
                userProgram?.let { program ->
                    viewModel?.programName?.set(program.name)
                    presenter.setSavedProgramData(program)
                    binding?.viewBlockly?.saveProgram(presenter)
                }
            }
            else -> Unit
        }
    }
}

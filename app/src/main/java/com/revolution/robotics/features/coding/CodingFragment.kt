package com.revolution.robotics.features.coding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.postDelayed
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.blockly.DialogFactory
import com.revolution.robotics.blockly.utils.JavascriptResultHandler
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.databinding.FragmentCodingBinding
import com.revolution.robotics.features.coding.programs.ProgramsDialog
import com.revolution.robotics.features.coding.saveProgram.SaveProgramDialog
import com.revolution.robotics.features.controllers.programInfo.ProgramDialog
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig
import org.kodein.di.erased.instance
import org.revolution.blockly.view.BlocklyLoadedListener
import org.revolution.blockly.view.jsInterface.BlocklyJavascriptListener

class CodingFragment : BaseFragment<FragmentCodingBinding, CodingViewModel>(R.layout.fragment_coding), CodingMvp.View,
    DialogEventBus.Listener, BlocklyLoadedListener {

    companion object {
        private const val BLOCKLY_DELAY_MS = 125L
        private var Bundle.program by BundleArgumentDelegate.ParcelableNullable<UserProgram>("program")
    }

    override val viewModelClass: Class<CodingViewModel> = CodingViewModel::class.java

    private val presenter: CodingMvp.Presenter by kodein.instance()
    private val javascriptResultHandler: JavascriptResultHandler by kodein.instance()
    private val resourceResolver: ResourceResolver by kodein.instance()
    private val dialogEventBus: DialogEventBus by kodein.instance()
    private val navigator: Navigator by kodein.instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        super.onCreateView(inflater, container, savedInstanceState).apply {
            binding?.buttonBackground = ChippedBoxConfig.Builder()
                .chipSize(R.dimen.dimen_12dp)
                .backgroundColorResource(R.color.grey_28)
                .create()
            viewModel?.isInEditMode?.set(arguments?.program != null)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.viewBlockly?.apply {
            init(DialogFactory(javascriptResultHandler, resourceResolver, fragmentManager))
            listener = this@CodingFragment
        }
        presenter.register(this, viewModel)
        dialogEventBus.register(this)
    }

    override fun onDestroyView() {
        presenter.unregister()
        dialogEventBus.unregister(this)
        super.onDestroyView()
    }

    override fun loadProgramIntoTheBlockly(xml: String) {
        binding?.viewBlockly?.loadProgram(xml)
    }

    override fun clearBlocklyWorkspace() {
        binding?.viewBlockly?.clearWorkspace()
    }

    override fun onBlocklyLoaded() {
        binding?.root?.postDelayed(BLOCKLY_DELAY_MS) {
            viewModel?.isBlocklyLoaded?.set(true)
            arguments?.program?.let { presenter.loadProgram(it) }
        }
    }

    override fun getPythonCodeFromBlockly(listener: BlocklyJavascriptListener) {
        binding?.viewBlockly?.saveProgram(listener)
    }

    override fun onProgramSaved() {
        if (viewModel?.isInEditMode?.get() == true) {
            navigator.back()
        }
    }

    override fun onToolbarBackPressed() {
        onBackPressed()
    }

    override fun onBackPressed(): Boolean {
        LeaveProgramDialog.newInstance().show(fragmentManager)
        return true
    }

    @Suppress("ComplexMethod")
    override fun onDialogEvent(event: DialogEvent) {
        when (event) {
            DialogEvent.SHOW_PROGRAM_INFO ->
                event.extras.getParcelable<UserProgram>(ProgramsDialog.KEY_PROGRAM)?.let { program ->
                    if (program.remoteId == null) {
                        showDialog(ProgramDialog.Load.newInstance(program))
                    } else {
                        showDialog(ProgramDialog.LoadRemote.newInstance(program))
                    }
                }
            DialogEvent.LOAD_PROGRAM ->
                event.extras.getParcelable<UserProgram>(ProgramDialog.KEY_PROGRAM)?.let {
                    presenter.loadProgram(it)
                }
            DialogEvent.DELETE_PROGRAM ->
                event.extras.getParcelable<UserProgram>(ProgramDialog.KEY_PROGRAM)?.let {
                    presenter.removeProgram(it)
                }
            DialogEvent.SAVE_PROGRAM -> {
                val userProgram = event.extras.getParcelable<UserProgram?>(SaveProgramDialog.KEY_USER_PROGRAM)
                userProgram?.let { program ->
                    presenter.setSavedProgramData(program)
                    binding?.viewBlockly?.saveProgram(presenter)
                }
            }
            DialogEvent.PROGRAM_CONFIRM_CLOSE ->
                navigator.back()
            else -> Unit
        }
    }
}

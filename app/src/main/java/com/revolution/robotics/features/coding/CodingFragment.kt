package com.revolution.robotics.features.coding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.core.view.postDelayed
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.analytics.Reporter
import com.revolution.robotics.blockly.DialogFactory
import com.revolution.robotics.blockly.utils.BlocklyResultHolder
import com.revolution.robotics.core.domain.local.UserConfiguration
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.databinding.FragmentCodingBinding
import com.revolution.robotics.features.coding.new.robotSelector.RobotSelectorDialog
import com.revolution.robotics.features.coding.programs.ProgramsDialog
import com.revolution.robotics.features.coding.saveProgram.SaveProgramDialog
import com.revolution.robotics.features.controllers.programInfo.ProgramDialog
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig
import org.kodein.di.erased.instance
import org.revolutionrobotics.blockly.android.view.BlocklyLoadedListener
import org.revolutionrobotics.blockly.android.view.jsInterface.SaveBlocklyListener

@Suppress("TooManyFunctions")
class CodingFragment : BaseFragment<FragmentCodingBinding, CodingViewModel>(R.layout.fragment_coding), CodingMvp.View,
    DialogEventBus.Listener, BlocklyLoadedListener {

    companion object {
        private const val BLOCKLY_DELAY_MS = 125L

        const val ACTION_ID_LEAVE = 1
        const val ACTION_ID_LOAD_PROGRAMS = 2
        const val ACTION_ID_CREATE_NEW_PROGRAM = 3

        private var Bundle.program by BundleArgumentDelegate.ParcelableNullable<UserProgram>("program")
        private var Bundle.isInEditMode by BundleArgumentDelegate.Boolean("edit_mode")
    }

    override val viewModelClass: Class<CodingViewModel> = CodingViewModel::class.java
    override val screen = Reporter.Screen.CODING

    private val presenter: CodingMvp.Presenter by kodein.instance()
    private val blocklyResultHolder: BlocklyResultHolder by kodein.instance()
    private val resourceResolver: ResourceResolver by kodein.instance()
    private val dialogEventBus: DialogEventBus by kodein.instance()
    private val navigator: Navigator by kodein.instance()

    private var dialogFactory: DialogFactory? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        super.onCreateView(inflater, container, savedInstanceState).apply {
            binding?.buttonBackground = ChippedBoxConfig.Builder()
                .chipSize(R.dimen.dimen_12dp)
                .backgroundColorResource(R.color.grey_28)
                .create()
            viewModel?.isInEditMode?.set(arguments?.isInEditMode ?: false)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialogFactory = DialogFactory(blocklyResultHolder, resourceResolver, fragmentManager)
        binding?.viewBlockly?.apply {
            init(dialogFactory!!)
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

    override fun onConfigLoaded(userConfiguration: UserConfiguration?) {
        dialogFactory?.userConfiguration = userConfiguration
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
            if (arguments?.program != null) {
                arguments?.program?.let { presenter.loadProgram(it) }
            } else {
                presenter.showRobotSelectionDialog()
            }
        }
    }

    override fun getDataFromBlocklyView(listener: SaveBlocklyListener) {
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

    override fun onBackPressed(showDialog: Boolean) {
        if (showDialog) {
            LeaveProgramDialog.newInstance().show(fragmentManager)
        } else {
            navigator.back(1)
        }
    }

    override fun shareCode(code: String, xml: String) {
        ShareCompat.IntentBuilder.from(activity)
            .setType("message/rfc822")
            .setSubject("Blockly export")
            .setText("$code\n________________________________________________\n$xml")
            .setChooserTitle("Share code via")
            .startChooser()
    }

    override fun onBackPressed(): Boolean {
        presenter.onBackPressed()
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
                    reporter.reportEvent(Reporter.Event.OPEN_PROGRAM)
                    presenter.loadProgram(it)
                }
            DialogEvent.DELETE_PROGRAM ->
                event.extras.getParcelable<UserProgram>(ProgramDialog.KEY_PROGRAM)?.let {
                    presenter.removeProgram(it)
                }
            DialogEvent.SAVE_PROGRAM -> {
                val userProgram = event.extras.getParcelable<UserProgram?>(SaveProgramDialog.KEY_USER_PROGRAM)
                userProgram?.let { program ->
                    presenter.setSavedProgramData(program, event.extras.getInt(SaveProgramDialog.KEY_ACTION_ID))
                    binding?.viewBlockly?.saveProgram(presenter)
                }
            }
            DialogEvent.PROGRAM_CONFIRM_CLOSE_WITH_SAVE -> presenter.showSaveProgramDialog(
                viewModel?.userProgram,
                ACTION_ID_LEAVE
            )
            DialogEvent.PROGRAM_CONFIRM_LOAD_WITH_SAVE -> presenter.showSaveProgramDialog(
                viewModel?.userProgram,
                ACTION_ID_LOAD_PROGRAMS
            )
            DialogEvent.PROGRAM_CONFIRM_LOAD_WITHOUT_SAVE -> showDialog(
                ProgramsDialog.newInstance()
            )
            DialogEvent.PROGRAM_CONFIRM_CREATE_NEW_WITH_SAVE -> presenter.showSaveProgramDialog(
                viewModel?.userProgram,
                ACTION_ID_CREATE_NEW_PROGRAM
            )
            DialogEvent.PROGRAM_CONFIRM_CREATE_NEW_WITHOUT_SAVE -> presenter.showRobotSelectionDialog()
            DialogEvent.PROGRAM_CONFIRM_CLOSE_WITHOUT_SAVE -> onBackPressed(false)
            DialogEvent.ROBOT_SELECTED ->
                event.extras.getParcelable<UserRobot>(RobotSelectorDialog.KEY_ROBOT)?.let { robot ->
                    reporter.reportEvent(Reporter.Event.CREATE_NEW_PROGRAM)
                    presenter.createNewProgram(robot.id)
                }
            else -> Unit
        }
    }
}

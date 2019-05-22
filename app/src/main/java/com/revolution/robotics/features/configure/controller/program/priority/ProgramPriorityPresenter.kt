package com.revolution.robotics.features.configure.controller.program.priority

import android.util.SparseArray
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserControllerWithPrograms
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.domain.local.UserProgramBinding
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.core.interactor.SaveUserControllerInteractor
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.configure.UserConfigurationStorage
import com.revolution.robotics.features.configure.controller.CompatibleProgramFilterer
import com.revolution.robotics.features.configure.save.SaveControllerDialog
import java.util.Collections

class ProgramPriorityPresenter(
    private val userConfigurationStorage: UserConfigurationStorage,
    private val compatibleProgramFilterer: CompatibleProgramFilterer,
    private val saveUserControllerInteractor: SaveUserControllerInteractor,
    private val dialogEventBus: DialogEventBus,
    private val navigator: Navigator
) :
    ProgramPriorityMvp.Presenter, DialogEventBus.Listener {

    override var view: ProgramPriorityMvp.View? = null
    override var model: ProgramPriorityViewModel? = null

    private val viewModels = mutableListOf<ProgramPriorityItemViewModel>()

    override fun register(view: ProgramPriorityMvp.View, model: ProgramPriorityViewModel?) {
        super.register(view, model)
        dialogEventBus.register(this)
        userConfigurationStorage.controllerHolder?.apply {
            viewModels.clear()
            viewModels.addAll(generateItems(this, programs).mapIndexed { index, userProgramBindingItem ->
                ProgramPriorityItemViewModel(userProgramBindingItem, index + 1, this@ProgramPriorityPresenter)
            })
            model?.items?.value = viewModels
        }
    }

    override fun unregister() {
        dialogEventBus.unregister(this)
        super.unregister()
    }

    override fun onDragEnded() {
        viewModels.forEachIndexed { index, programPriorityItemViewModel ->
            programPriorityItemViewModel.position = index + 1
        }
    }

    override fun onDialogEvent(event: DialogEvent) {
        if (event == DialogEvent.SAVE_CONTROLLER) {
            viewModels.forEach { item ->
                item.userProgramBindingItem.userProgram?.let {
                    userConfigurationStorage.setPriority(it, item.position)
                }
            }
            userConfigurationStorage.controllerHolder?.backgroundBindings?.let {
                saveUserControllerInteractor.backgroundProgramBindings = it
            }
            userConfigurationStorage.controllerHolder?.userController?.let { userController ->
                userController.name = event.extras.getString(SaveControllerDialog.KEY_NAME)
                userController.description = event.extras.getString(SaveControllerDialog.KEY_DESCRIPTION)
                saveUserControllerInteractor.userController = userController
            }
            saveUserControllerInteractor.execute({
                userConfigurationStorage.controllerHolder = null
                navigator.popUntil(R.id.configureFragment)
            }, {
                // TODO Error handling
            })
        }
    }

    override fun onItemMoved(from: Int, to: Int) {
        if (from < to) {
            for (i in from until to) {
                Collections.swap(viewModels, i, i + 1)
            }
        } else {
            for (i in from downTo to + 1) {
                Collections.swap(viewModels, i, i - 1)
            }
        }
    }

    override fun onInfoButtonClicked(item: ProgramPriorityItemViewModel) {
        item.userProgramBindingItem.userProgram?.let {
            view?.showProgramInfoDialog(it, compatibleProgramFilterer.isProgramCompatible(it))
        }
    }

    override fun onDoneButtonClicked() {
        view?.showSaveDialog(
            userConfigurationStorage.controllerHolder?.userController?.name,
            userConfigurationStorage.controllerHolder?.userController?.description
        )
    }

    private fun generateItems(
        controllerWithPrograms: UserControllerWithPrograms,
        programs: SparseArray<UserProgram>
    ): List<UserProgramBindingItem> {
        val items = mutableListOf<UserProgramBindingItem>()
        controllerWithPrograms.backgroundBindings.forEach { binding ->
            items.add(
                UserProgramBindingItem(
                    binding.id,
                    binding.priority,
                    ProgramType.BACKGROUND,
                    programs.get(binding.programId).lastModified,
                    programs.get(binding.programId).name ?: "",
                    programs[binding.programId]
                )
            )
        }

        controllerWithPrograms.userController.getMappingList().forEach { binding ->
            addItemFromButtonBinding(binding, items, programs)
        }

        return items.sortedWith(compareBy<UserProgramBindingItem> { it.priority }.thenBy { it.lastModified })
    }

    private fun addItemFromButtonBinding(
        binding: UserProgramBinding?,
        items: MutableList<UserProgramBindingItem>,
        programs: SparseArray<UserProgram>
    ) {
        binding?.let { programBinding ->
            items.add(
                UserProgramBindingItem(
                    programBinding.id,
                    programBinding.priority,
                    ProgramType.BUTTON,
                    programs.get(programBinding.programId).lastModified,
                    programs.get(programBinding.programId).name ?: "",
                    programs[programBinding.programId]
                )
            )
        }
    }
}

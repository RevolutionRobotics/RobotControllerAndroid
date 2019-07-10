package com.revolution.robotics.features.configure.controller.program.priority

import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserControllerWithPrograms
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.domain.local.UserProgramBinding
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.configure.UserConfigurationStorage
import com.revolution.robotics.features.configure.save.SaveControllerDialog
import com.revolution.robotics.features.controllers.ControllerType
import com.revolution.robotics.features.controllers.programInfo.ProgramDialog
import java.util.Collections

@Suppress("TooManyFunctions")
class ProgramPriorityPresenter(
    private val userConfigurationStorage: UserConfigurationStorage,
    private val dialogEventBus: DialogEventBus,
    private val resourceResolver: ResourceResolver,
    private val navigator: Navigator
) :
    ProgramPriorityMvp.Presenter, DialogEventBus.Listener {

    private companion object {
        const val DEFAULT_DRIVE_PRIORITY = -2
    }

    override var view: ProgramPriorityMvp.View? = null
    override var model: ProgramPriorityViewModel? = null

    private val viewModels = mutableListOf<PriorityItem>()

    override fun register(view: ProgramPriorityMvp.View, model: ProgramPriorityViewModel?) {
        super.register(view, model)
        dialogEventBus.register(this)
        userConfigurationStorage.controllerHolder?.apply {
            viewModels.clear()
            viewModels.addAll(generateItems(this, programs).mapIndexed { index, bindingItem ->
                if (bindingItem is UserProgramBindingItem) {
                    ProgramPriorityItemViewModel(bindingItem, index + 1, this@ProgramPriorityPresenter)
                } else {
                    ProgramPriorityJoystickViewModel(index + 1)
                }
            })
            model?.items?.value = viewModels
        }
    }

    override fun unregister(view: ProgramPriorityMvp.View?) {
        dialogEventBus.unregister(this)
        super.unregister(view)
    }

    override fun onDragEnded() {
        viewModels.forEachIndexed { index, programPriorityItemViewModel ->
            programPriorityItemViewModel.position = index + 1
        }
    }

    override fun onDialogEvent(event: DialogEvent) {
        if (event == DialogEvent.SAVE_CONTROLLER) {
            saveController(
                event.extras.getString(SaveControllerDialog.KEY_NAME) ?: "",
                event.extras.getString(SaveControllerDialog.KEY_DESCRIPTION) ?: ""
            )
        }
    }

    private fun saveController(name: String, description: String) {
        viewModels.forEach { item ->
            if (item is ProgramPriorityItemViewModel) {
                item.userProgramBindingItem.userProgram?.let {
                    userConfigurationStorage.setPriority(it, item.position)
                }
            } else if (item is ProgramPriorityJoystickViewModel) {
                userConfigurationStorage.controllerHolder?.userController?.joystickPriority = item.position
            }
        }
        userConfigurationStorage.setControllerName(
            name,
            description
        ) {
            userConfigurationStorage.controllerHolder = null
            navigator.popUntil(R.id.configureFragment)
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
        item.userProgramBindingItem.userProgram?.let { view?.showDialog(ProgramDialog.InfoNoEdit.newInstance(it)) }
    }

    override fun onDoneButtonClicked() {
        val name =
            userConfigurationStorage.controllerHolder?.userController?.name ?: getDefaultControllerNameBasedOnType()
        val description = userConfigurationStorage.controllerHolder?.userController?.description ?: ""
        view?.showDialog(SaveControllerDialog.newInstance(name, description))
    }

    private fun getDefaultControllerNameBasedOnType(): String =
        ControllerType.fromId(userConfigurationStorage.controllerHolder?.userController?.type)?.nameRes?.let {
            resourceResolver.string(it)
        } ?: ""

    private fun generateItems(
        controllerWithPrograms: UserControllerWithPrograms,
        programs: HashMap<String, UserProgram>
    ): List<BindingItem> {
        val items = mutableListOf<BindingItem>()
        controllerWithPrograms.backgroundBindings.forEach { binding ->
            items.add(
                UserProgramBindingItem(
                    binding.id,
                    binding.priority,
                    ProgramType.BACKGROUND,
                    programs[binding.programId]?.lastModified ?: 0L,
                    programs[binding.programId]?.name ?: "",
                    programs[binding.programId]
                )
            )
        }

        controllerWithPrograms.userController.getMappingList().forEach { binding ->
            addItemFromButtonBinding(binding, items, programs)
        }
        val joystickPriority =
            if (controllerWithPrograms.userController.joystickPriority == 0) {
                DEFAULT_DRIVE_PRIORITY
            } else {
                controllerWithPrograms.userController.joystickPriority
            }
        items.add(JoystickBindingItem(joystickPriority, 0L))
        return items.sortedWith(compareBy<BindingItem> { it.priority }.thenBy { it.lastModified })
    }

    private fun addItemFromButtonBinding(
        binding: UserProgramBinding?,
        items: MutableList<BindingItem>,
        programs: HashMap<String, UserProgram>
    ) {
        binding?.let { programBinding ->
            items.add(
                UserProgramBindingItem(
                    programBinding.id,
                    programBinding.priority,
                    ProgramType.BUTTON,
                    programs[programBinding.programName]?.lastModified ?: 0L,
                    programs[programBinding.programName]?.name ?: "",
                    programs[programBinding.programName]
                )
            )
        }
    }
}

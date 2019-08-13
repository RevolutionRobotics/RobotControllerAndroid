package com.revolution.robotics.features.configure.controller.program.priority

import com.revolution.robotics.core.domain.local.UserControllerWithPrograms
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.domain.local.UserProgramBinding
import com.revolution.robotics.core.interactor.GetUserControllerInteractor
import com.revolution.robotics.core.interactor.SaveUserControllerInteractor
import com.revolution.robotics.features.controllers.programInfo.ProgramDialog
import java.util.*

@Suppress("TooManyFunctions")
class ProgramPriorityPresenter(
    private val getUserControllerInteractor: GetUserControllerInteractor,
    private val  saveUserControllerInteractor: SaveUserControllerInteractor
) :ProgramPriorityMvp.Presenter {

    private companion object {
        const val DEFAULT_DRIVE_PRIORITY = -2
    }

    override var view: ProgramPriorityMvp.View? = null
    override var model: ProgramPriorityViewModel? = null

    private val viewModels = mutableListOf<PriorityItem>()

    private var controllerWithPrograms: UserControllerWithPrograms? = null

    override fun loadPrograms(controllerId: Int) {
        getUserControllerInteractor.id = controllerId
        getUserControllerInteractor.execute {
            controllerWithPrograms = it
            controllerWithPrograms?.apply {
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

    }

    override fun onDragEnded() {
        viewModels.forEachIndexed { index, programPriorityItemViewModel ->
            programPriorityItemViewModel.position = index + 1
        }
    }

    override fun save() {
        viewModels.forEach { item ->
            if (item is ProgramPriorityItemViewModel) {
                item.userProgramBindingItem.userProgram?.let {
                    setPriority(it, item.position)
                }
            } else if (item is ProgramPriorityJoystickViewModel) {
                controllerWithPrograms?.userController?.joystickPriority = item.position
            }
        }
        controllerWithPrograms?.userController?.let { userController ->
            saveUserControllerInteractor.userController = userController
            saveUserControllerInteractor.backgroundProgramBindings = controllerWithPrograms?.backgroundBindings ?: emptyList()
            saveUserControllerInteractor.execute()
        }
    }

    private fun setPriority(userProgram: UserProgram, priority: Int) {
        controllerWithPrograms?.backgroundBindings?.find { it.programId == userProgram.name }?.let {
            it.priority = priority
        }

        controllerWithPrograms?.userController?.getMappingList()?.forEach { binding ->
            if (binding?.programName == userProgram.name) {
                binding.priority = priority
            }
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

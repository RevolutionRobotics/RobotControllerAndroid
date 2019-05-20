package com.revolution.robotics.features.configure.controller.program.priority

import android.util.SparseArray
import com.revolution.robotics.core.domain.local.UserBackgroundProgramBinding
import com.revolution.robotics.core.domain.local.UserButtonMapping
import com.revolution.robotics.core.domain.local.UserController
import com.revolution.robotics.core.domain.local.UserControllerWithPrograms
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.domain.local.UserProgramBinding
import com.revolution.robotics.features.configure.UserConfigurationStorage
import java.util.Collections

class ProgramPriorityPresenter(private val userConfigurationStorage: UserConfigurationStorage) :
    ProgramPriorityMvp.Presenter {

    override var view: ProgramPriorityMvp.View? = null
    override var model: ProgramPriorityViewModel? = null

    private val viewModels = mutableListOf<ProgramPriorityItemViewModel>()

    override fun register(view: ProgramPriorityMvp.View, model: ProgramPriorityViewModel?) {
        super.register(view, model)
        userConfigurationStorage.controllerHolder = generateDummyUserController()
        userConfigurationStorage.controllerHolder?.apply {
            viewModels.clear()
            viewModels.addAll(generateItems(this, programs).mapIndexed { index, userProgramBindingItem ->
                ProgramPriorityItemViewModel(userProgramBindingItem, index + 1, this@ProgramPriorityPresenter)
            })
            model?.items?.value = viewModels
        }
    }

    override fun onDragEnded() {
        viewModels.forEachIndexed { index, programPriorityItemViewModel ->
            programPriorityItemViewModel.position = index + 1
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
        // TODO Show info dialog
    }

    override fun onNextButtonClicked() {
        viewModels.forEach { item ->
            item.userProgramBindingItem.userProgram?.let {
                userConfigurationStorage.setPriority(it, item.position)
            }
        }
        // TODO Navigate to the next screen
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

        items.sortWith(compareBy({ it.priority }, { it.lastModified }))
        return items
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

    // TODO Remove after we have real data
    @Suppress("MagicNumber")
    private fun generateDummyUserController() = UserControllerWithPrograms(
        userController = UserController(
            mapping = UserButtonMapping(
                b1 = UserProgramBinding(1, "1", 1, 4),
                b2 = UserProgramBinding(2, "1", 2, 3),
                b4 = UserProgramBinding(3, "1", 3, 2),
                b5 = UserProgramBinding(4, "1", 4, 1)
            )
        ),
        backgroundBindings = mutableListOf(
            UserBackgroundProgramBinding(5, "1", 5, 5),
            UserBackgroundProgramBinding(6, "1", 5, 5),
            UserBackgroundProgramBinding(7, "1", 6, 6),
            UserBackgroundProgramBinding(8, "1", 7, 7),
            UserBackgroundProgramBinding(9, "1", 8, 8),
            UserBackgroundProgramBinding(10, "1", 9, 9),
            UserBackgroundProgramBinding(11, "1", 10, 10),
            UserBackgroundProgramBinding(12, "1", 11, 11)
        ),
        programs = SparseArray<UserProgram>().apply {
            put(1, UserProgram(1, "This is a program #1", System.currentTimeMillis(), "Program name #1"))
            put(2, UserProgram(2, "This is a program #2", System.currentTimeMillis(), "Program name #2"))
            put(3, UserProgram(3, "This is a program #3", System.currentTimeMillis(), "Program name #3"))
            put(4, UserProgram(4, "This is a program #4", System.currentTimeMillis(), "Program name #4"))
            put(5, UserProgram(5, "This is a program #5", System.currentTimeMillis(), "Program name #5"))
            put(6, UserProgram(6, "This is a program #6", System.currentTimeMillis(), "Program name #6"))
            put(7, UserProgram(7, "This is a program #7", System.currentTimeMillis(), "Program name #7"))
            put(8, UserProgram(8, "This is a program #8", System.currentTimeMillis(), "Program name #8"))
            put(9, UserProgram(9, "This is a program #9", System.currentTimeMillis(), "Program name #9"))
            put(10, UserProgram(10, "This is a program #10", System.currentTimeMillis(), "Program name #10"))
            put(11, UserProgram(11, "This is a program #11", System.currentTimeMillis(), "Program name #11"))
        }
    )
}

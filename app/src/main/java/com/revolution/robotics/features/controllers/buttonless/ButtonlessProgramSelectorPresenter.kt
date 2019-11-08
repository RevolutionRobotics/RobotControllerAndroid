package com.revolution.robotics.features.controllers.buttonless

import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserConfiguration
import com.revolution.robotics.core.domain.local.UserControllerWithPrograms
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.interactor.GetFullConfigurationInteractor
import com.revolution.robotics.core.interactor.GetUserProgramsForRobotInteractor
import com.revolution.robotics.core.interactor.GetUserProgramsInteractor
import com.revolution.robotics.core.interactor.SaveUserControllerInteractor
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.configure.controller.CompatibleProgramFilterer
import com.revolution.robotics.features.controllers.ProgramOrderingHandler
import com.revolution.robotics.features.controllers.buttonless.adapter.ButtonlessProgramViewModel
import com.revolution.robotics.features.play.FullControllerData
import kotlin.collections.set

@Suppress("TooManyFunctions")
class ButtonlessProgramSelectorPresenter(
    private val getFullConfigurationInteractor: GetFullConfigurationInteractor,
    private val getUserProgramsForRobotInteractor: GetUserProgramsForRobotInteractor,
    private val saveUserControllerInteractor: SaveUserControllerInteractor,
    private val compatibleProgramFilterer: CompatibleProgramFilterer,
    private val navigator: Navigator

) : ButtonlessProgramSelectorMvp.Presenter {

    companion object {
        private const val SHOW_COMPATIBLE_PROGRAMS_ONLY_BY_DEFAULT = true
    }

    override var view: ButtonlessProgramSelectorMvp.View? = null
    override var model: ButtonlessProgramSelectorViewModel? = null

    private var robotId: Int? = null
    private var userConfiguration: UserConfiguration? = null
    private var allPrograms: List<ButtonlessProgramViewModel>? = null
    private var programs: MutableList<ButtonlessProgramViewModel> = mutableListOf()
    private var onlyShowCompatiblePrograms: Boolean? = null

    override fun register(view: ButtonlessProgramSelectorMvp.View, model: ButtonlessProgramSelectorViewModel?) {
        super.register(view, model)
        if (onlyShowCompatiblePrograms == null) {
            setShowOnlyCompatiblePrograms(SHOW_COMPATIBLE_PROGRAMS_ONLY_BY_DEFAULT)
        }
    }

    override fun load(robotId: Int) {
        this.robotId = robotId
        loadPrograms(robotId)
    }

    override fun clearSelections() {
        onlyShowCompatiblePrograms = null
        model?.programOrderingHandler?.currentOrder =
            ProgramOrderingHandler.OrderBy.DATE to ProgramOrderingHandler.Order.DESCENDING
        allPrograms = null
        programs.clear()
    }

    private fun loadPrograms(robotId: Int) {
        getFullConfigurationInteractor.robotId = robotId
        getFullConfigurationInteractor.execute { fullControllerData ->
            userConfiguration = fullControllerData.userConfiguration
            getUserProgramsForRobotInteractor.robotId = robotId
            getUserProgramsForRobotInteractor.execute { userPrograms ->
                val boundPrograms =
                    fullControllerData.controller?.userController?.getBoundButtonPrograms() ?: emptyList()
                allPrograms = userPrograms.filter { program ->
                    boundPrograms.find { it.programName == program.name } == null
                }.map { userProgram ->
                    ButtonlessProgramViewModel(userProgram, this).apply {
                        selected.set(fullControllerData.controller?.backgroundBindings?.find
                        { it.programName == userProgram.name } != null && compatibleProgramFilterer.isProgramCompatible(
                            userProgram,
                            fullControllerData.userConfiguration
                        ))
                        enabled.set(compatibleProgramFilterer.isProgramCompatible(userProgram, userConfiguration))
                    }
                }.apply {
                    programs.clear()
                    programs.addAll(this)
                }
                orderAndFilterPrograms()
                setOrderingIcons()
            }
        }

    }

    private fun setShowOnlyCompatiblePrograms(onlyCompatible: Boolean) {
        onlyShowCompatiblePrograms = onlyCompatible
        if (onlyCompatible) {
            model?.showCompatibleButtonText?.set(R.string.buttonless_program_show_all_programs)
            model?.showCompatibleButtonIcon?.set(R.drawable.ic_compatible_selected)
        } else {
            model?.showCompatibleButtonText?.set(R.string.buttonless_program_show_compatible_programs)
            model?.showCompatibleButtonIcon?.set(R.drawable.ic_compatible)
        }
    }

    override fun updateOrderingAndFiltering() {
        orderAndFilterPrograms()
        setOrderingIcons()
    }

    private fun setOrderingIcons() {
        model?.apply {
            if (programOrderingHandler.currentOrder.first == ProgramOrderingHandler.OrderBy.DATE) {
                nameOrderIconColor.set(R.color.white)
                dateOrderIconColor.set(R.color.robotics_red)

                if (programOrderingHandler.currentOrder.second == ProgramOrderingHandler.Order.ASCENDING) {
                    dateOrderIcon.set(R.drawable.ic_sort_date_up)
                } else {
                    dateOrderIcon.set(R.drawable.sort_date_down)
                }
                alphabeticalOderIcon.set(R.drawable.sort_name_up)
            } else {
                nameOrderIconColor.set(R.color.robotics_red)
                dateOrderIconColor.set(R.color.white)

                if (programOrderingHandler.currentOrder.second == ProgramOrderingHandler.Order.ASCENDING) {
                    alphabeticalOderIcon.set(R.drawable.sort_name_up)
                } else {
                    alphabeticalOderIcon.set(R.drawable.sort_name_down_red)
                }

                dateOrderIcon.set(R.drawable.sort_date_down)
            }
        }
    }

    private fun orderAndFilterPrograms() {
        model?.let { model ->
            val filteredPrograms =
                if (onlyShowCompatiblePrograms == true) {
                    programs.filter {
                        compatibleProgramFilterer.isProgramCompatible(it.program, userConfiguration)
                    }
                } else {
                    allPrograms ?: emptyList()
                }
            programs = filteredPrograms.sortedWith(Comparator { o1, o2 ->
                model.programOrderingHandler.getComparator().compare(o1.program, o2.program)
            }).toMutableList()
            model.items.value = programs
            model.isEmpty.set(programs.isEmpty())
            model.emptyText.set(
                if (onlyShowCompatiblePrograms == true) {
                    R.string.buttonless_program_selector_compatible_empty
                } else {
                    R.string.buttonless_program_selector_empty
                }
            )
        }

    }

    override fun onShowCompatibleProgramsButtonClicked() {
        onlyShowCompatiblePrograms?.let { onlyShowCompatiblePrograms ->
            setShowOnlyCompatiblePrograms(!onlyShowCompatiblePrograms)
            updateOrderingAndFiltering()
        }
    }

    fun save() {
        robotId?.let { robotId ->
            getFullConfigurationInteractor.robotId = robotId
            getFullConfigurationInteractor.execute { fullControllerData ->
                val priorities = HashMap<String, Int>()
                programs.forEach { viewModel ->
                    if (viewModel.selected.get()) {
                        priorities[viewModel.program.name] = getPriority(fullControllerData.controller!!, viewModel.program.name)
                    }
                }

                fullControllerData.controller?.clearBackgroundPrograms()
                programs.forEach { viewModel ->
                    if (viewModel.selected.get()) {
                        val priority = priorities[viewModel.program.name]
                        fullControllerData.controller?.addBackgroundProgram(
                            viewModel.program,
                            if (priority == -1) 0 else priority ?: 0
                        )
                    }
                }

                saveController(fullControllerData)
            }
        }
    }

    private fun saveController(fullControllerData: FullControllerData) {
        fullControllerData.controller?.userController?.let { userController ->
            saveUserControllerInteractor.userController = userController
            saveUserControllerInteractor.backgroundProgramBindings = fullControllerData.controller.backgroundBindings
            saveUserControllerInteractor.execute()
        }
    }

    override fun onSelectAllClicked(checked: Boolean) {
        programs.forEach {
            it.selected.set(
                compatibleProgramFilterer.isProgramCompatible(it.program, userConfiguration) && checked
            )
        }
        save()
    }

    override fun onProgramSelected(viewModel: ButtonlessProgramViewModel) {
        if (compatibleProgramFilterer.isProgramCompatible(viewModel.program, userConfiguration)
        ) {
            viewModel.selected.set(!viewModel.selected.get())
            save()
        }
    }

    override fun onInfoButtonClicked(userProgram: UserProgram) {
        view?.showUserProgramDialog(
            userProgram,
            compatibleProgramFilterer.isProgramCompatible(userProgram, userConfiguration)
        )
    }

    override fun onEditButtonClicked(userProgram: UserProgram) {

        navigator.navigate(ButtonlessProgramSelectorFragmentDirections.toCoding(userProgram, true))
    }

    override fun onProgramEdited(userProgram: UserProgram) {
        val viewModel = programs.find { it.program == userProgram }
        if (viewModel?.selected?.get() == true && !compatibleProgramFilterer.isProgramCompatible(
                userProgram,
                userConfiguration
            )
        ) {
            viewModel.selected.set(false)
        }
    }

    private fun getPriority(controllerWithPrograms: UserControllerWithPrograms, userProgramName: String) =
        controllerWithPrograms.userController.getMappingList().find { it?.programName == userProgramName }?.priority
            ?: controllerWithPrograms.backgroundBindings.find { it.programName == userProgramName }?.priority ?: -1

}

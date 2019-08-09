package com.revolution.robotics.features.controllers.setup

import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.interactor.GetFullConfigurationInteractor
import com.revolution.robotics.core.interactor.GetUserProgramsInteractor
import com.revolution.robotics.core.interactor.SaveUserControllerInteractor
import com.revolution.robotics.features.configure.controller.CompatibleProgramFilterer
import com.revolution.robotics.features.configure.controller.ControllerButton
import com.revolution.robotics.features.controllers.programInfo.ProgramDialog
import com.revolution.robotics.features.controllers.setup.mostRecent.MostRecentItem
import com.revolution.robotics.features.controllers.setup.mostRecent.MostRecentProgramViewModel
import com.revolution.robotics.features.play.FullControllerData

class ConfigureControllerPresenter(
    private val getFullConfigurationInteractor: GetFullConfigurationInteractor,
    private val compatibleProgramFilterer: CompatibleProgramFilterer,
    private val getUserProgramsInteractor: GetUserProgramsInteractor,
    private val saveUserControllerInteractor: SaveUserControllerInteractor

) : ConfigureControllerMvp.Presenter {

    companion object {
        private const val MOST_RECENT_PROGRAM_COUNT = 5
        private const val INDEX_NOT_FOUND = -1
    }

    private val buttonNames = ControllerButton.values().toList()

    override var view: ConfigureControllerMvp.View? = null
    override var model: ConfigureControllerViewModel? = null

    private var configId: Int = -1
    private val allPrograms = ArrayList<UserProgram>()


    override fun loadControllerAndPrograms(configId: Int) {
        this.configId = configId
        getFullConfigurationInteractor.userConfigId = configId
        getFullConfigurationInteractor.execute { fullControllerData ->
            fullControllerData.controller?.let { model?.update(it) }
            view?.updateContentBindings()
            loadPrograms()
        }
    }

    private fun loadPrograms() {
        getUserProgramsInteractor.execute { result ->
            allPrograms.clear()
            allPrograms.addAll(result)
        }
    }

    override fun onProgramSlotSelected(index: Int) {
        getFullConfigurationInteractor.userConfigId = configId
        getFullConfigurationInteractor.execute { fullConfig ->
            fullConfig.controller?.let { model?.update(it) }
            view?.updateContentBindings()
            var programs =
                compatibleProgramFilterer.getCompatibleProgramsOnly(allPrograms, fullConfig.userConfiguration)
            val mostRecentViewModel =
                if (index == ConfigureControllerViewModel.NO_PROGRAM_SELECTED) {
                    null
                } else {
                    val availablePrograms = programs.toMutableList()
                    fullConfig?.controller?.userController?.getBoundButtonPrograms()?.forEach { boundProgram ->
                        availablePrograms.removeAll { it.name == boundProgram.programName }
                    }
                    fullConfig?.controller?.backgroundBindings?.forEach { backgroundBinding ->
                        availablePrograms.removeAll { it.name == backgroundBinding.programId }
                    }

                    var mostRecentPrograms = availablePrograms.sortedBy { it.lastModified }.reversed()
                    if (mostRecentPrograms.size > MOST_RECENT_PROGRAM_COUNT) {
                        mostRecentPrograms = mostRecentPrograms.subList(0, MOST_RECENT_PROGRAM_COUNT)
                    }
                    val mostRecentItems = mostRecentPrograms.map { MostRecentItem(it) }.toMutableList()
                    model?.getProgram(index)?.let { boundProgram ->
                        mostRecentItems.add(0, MostRecentItem(boundProgram, true))
                    }

                    MostRecentProgramViewModel(mostRecentItems, this)
                }
            view?.onProgramSlotSelected(index, mostRecentViewModel)
        }
    }


    override fun onProgramSelected(program: UserProgram, isBound: Boolean) {
        if (isBound) {
            view?.showDialog(ProgramDialog.Remove.newInstance(program))
        } else {
            view?.showDialog(ProgramDialog.Add.newInstance(program))
        }
    }

    override fun addProgram(userProgram: UserProgram) {
        getFullConfigurationInteractor.userConfigId = configId
        getFullConfigurationInteractor.execute { fullControllerData ->
            model?.selectedProgram?.let {
                fullControllerData.controller?.addButtonProgram(
                    userProgram,
                    buttonNames[it - 1]
                )
                save(fullControllerData)
                model?.onProgramSet(userProgram)
                view?.hideProgramSelector()
            }
        }
    }

    override fun removeProgram() {
        getFullConfigurationInteractor.userConfigId = configId
        getFullConfigurationInteractor.execute { fullControllerData ->
            model?.selectedProgram?.let {
                fullControllerData.controller?.removeButtonProgram(
                    buttonNames[it - 1]
                )
                save(fullControllerData)
                model?.onProgramSet(null)
                view?.hideProgramSelector()
            }
        }
    }

    private fun save(fullControllerData: FullControllerData, finished: (() -> Unit)? = null) {
        fullControllerData.controller?.userController?.let { userController ->
            saveUserControllerInteractor.userController = userController
            saveUserControllerInteractor.backgroundProgramBindings = fullControllerData.controller?.backgroundBindings
            saveUserControllerInteractor.execute(onResponse = {
                finished?.invoke()
            }, onError = {
                finished?.invoke()
            })
        }
    }

    override fun showAllPrograms() {
        view?.onShowAllProgramsSelected()
    }

    override fun onProgramEdited(program: UserProgram) {
        getFullConfigurationInteractor.userConfigId = configId
        getFullConfigurationInteractor.execute { result ->
            if (!compatibleProgramFilterer.isProgramCompatible(program, result.userConfiguration)) {
                val index = model?.getProgramIndex(program)
                if (index != null && index != INDEX_NOT_FOUND) {
                    model?.selectedProgram = index + 1
                    removeProgram()
                }
            }
        }
    }
}

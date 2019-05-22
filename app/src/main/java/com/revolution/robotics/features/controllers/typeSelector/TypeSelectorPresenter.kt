package com.revolution.robotics.features.controllers.typeSelector

import android.util.SparseArray
import com.revolution.robotics.core.domain.local.UserController
import com.revolution.robotics.core.domain.local.UserControllerWithPrograms
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.configure.UserConfigurationStorage
import com.revolution.robotics.features.controllers.ControllerType

class TypeSelectorPresenter(
    private val storage: UserConfigurationStorage,
    private val navigator: Navigator
) : TypeSelectorMvp.Presenter {

    override var view: TypeSelectorMvp.View? = null
    override var model: TypeSelectorViewModel? = null

    override fun onControllerTypeSelected(type: ControllerType) {
        storage.controllerHolder = UserControllerWithPrograms(UserController(), mutableListOf(), SparseArray())
        navigator.navigate(
            when (type) {
                ControllerType.GAMER -> TypeSelectorFragmentDirections.toSetupGamer()
                ControllerType.MULTITASKER -> TypeSelectorFragmentDirections.toSetupMultitasker()
                ControllerType.DRIVER -> TypeSelectorFragmentDirections.toSetupDriver()
            }
        )
    }
}

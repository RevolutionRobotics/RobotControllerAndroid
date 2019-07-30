package com.revolution.robotics.features.configure.controllers

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.extensions.isEmptyOrNull
import com.revolution.robotics.core.extensions.onPropertyChanged
import com.revolution.robotics.features.configure.controllers.adapter.ControllersItem

class ConfigureControllersViewModel(private val presenter: ConfigureControllersMvp.Presenter) : ViewModel() {
    val controllersList: ObservableField<List<ControllersItem>?> = ObservableField()
    val isNextButtonVisible: ObservableBoolean = ObservableBoolean(false)
    val isPreviousButtonVisible: ObservableBoolean = ObservableBoolean(false)
    var isEmpty = ObservableBoolean(true)

    init {
        controllersList.onPropertyChanged { isEmpty.set(controllersList.get().isEmptyOrNull()) }
    }

    fun nextButtonClick() = presenter.nextButtonClick()
    fun previousButtonClick() = presenter.previousButtonClick()
}

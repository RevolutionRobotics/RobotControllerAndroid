package com.revolution.robotics.features.myRobots

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.extensions.isEmptyOrNull
import com.revolution.robotics.core.extensions.onPropertyChanged
import com.revolution.robotics.features.myRobots.adapter.MyRobotsItem

class MyRobotsViewModel(private val presenter: MyRobotsMvp.Presenter) : ViewModel() {
    val robotsList: ObservableField<MutableList<MyRobotsItem>?> = ObservableField()
    val currentPosition: ObservableInt = ObservableInt()
    val isNextButtonVisible: ObservableBoolean = ObservableBoolean(false)
    val isPreviousButtonVisible: ObservableBoolean = ObservableBoolean(false)
    var isEmpty = ObservableBoolean(true)

    init {
        robotsList.onPropertyChanged { isEmpty.set(robotsList.get().isEmptyOrNull()) }
    }

    fun nextButtonClick() = presenter.nextButtonClick()
    fun previousButtonClick() = presenter.previousButtonClick()

    fun onBuildNewClicked() {
        presenter.navigateToWhoToBuild()
    }
}

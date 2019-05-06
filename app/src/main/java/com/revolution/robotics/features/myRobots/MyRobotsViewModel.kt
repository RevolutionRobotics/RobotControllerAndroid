package com.revolution.robotics.features.myRobots

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revolution.robotics.features.myRobots.adapter.MyRobotsItem

class MyRobotsViewModel(private val presenter: MyRobotsMvp.Presenter) : ViewModel() {
    val robotsList: MutableLiveData<MutableList<MyRobotsItem>?> = MutableLiveData()
    val currentPosition: ObservableInt = ObservableInt()
    val isNextButtonVisible: ObservableBoolean = ObservableBoolean(false)
    val isPreviousButtonVisible: ObservableBoolean = ObservableBoolean(false)

    fun nextButtonClick() = presenter.nextButtonClick()
    fun previousButtonClick() = presenter.previousButtonClick()

    fun onBuildNewClicked() {
        presenter.navigateToWhoToBuild()
    }
}

package com.revolution.robotics.features.whoToBuild

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revolution.robotics.features.build.BuildRobotFragment
import com.revolution.robotics.features.whoToBuild.adapter.RobotItem

class WhoToBuildViewModel(private val presenter: WhoToBuildMvp.Presenter) : ViewModel() {
    val robotsList: MutableLiveData<List<RobotItem>?> = MutableLiveData()
    var currentPosition: ObservableInt = ObservableInt()
    var isNextButtonVisible: ObservableBoolean = ObservableBoolean(false)
    var isPreviousButtonVisible: ObservableBoolean = ObservableBoolean(false)

    fun nextButtonClick() = presenter.nextButtonClick()
    fun previousButtonClick() = presenter.previousButtonClick()
    fun onCustomBuildClick() = presenter.onRobotSelected(BuildRobotFragment.CUSTOM_ROBOT_ID)
}
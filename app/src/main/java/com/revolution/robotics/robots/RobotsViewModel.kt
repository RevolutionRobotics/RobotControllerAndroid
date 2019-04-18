package com.revolution.robotics.robots

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revolution.robotics.robots.adapter.RobotsAdapterItem

class RobotsViewModel(private val presenter: RobotsMvp.Presenter) : ViewModel() {
    val robotsList: MutableLiveData<List<RobotsAdapterItem>> = MutableLiveData()
    var startIndex: ObservableInt = ObservableInt()
    var currentPosition: ObservableInt = ObservableInt()
    var isNextButtonVisible: ObservableBoolean = ObservableBoolean(true)
    var isPreviousButtonVisible: ObservableBoolean = ObservableBoolean(true)

    fun nextButtonClick() = presenter.nextButtonClick()
    fun previousButtonClick() = presenter.previousButtonClick()
}

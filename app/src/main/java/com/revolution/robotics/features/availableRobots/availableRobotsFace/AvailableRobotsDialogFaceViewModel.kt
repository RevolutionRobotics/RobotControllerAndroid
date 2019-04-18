package com.revolution.robotics.features.availableRobots.availableRobotsFace

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revolution.robotics.features.availableRobots.adapter.AvailableRobotsItem

class AvailableRobotsDialogFaceViewModel(private val presenter: AvailableRobotsDialogFaceMvp.Presenter) : ViewModel() {
    val availableRobots: MutableLiveData<List<AvailableRobotsItem>> = MutableLiveData()

    fun onItemClick(idField: Any) = presenter.onItemClick(idField)
}

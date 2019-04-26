package com.revolution.robotics.features.build.connect.adapter

import androidx.lifecycle.MutableLiveData
import com.revolution.robotics.R
import com.revolution.robotics.core.utils.recyclerview.DiffUtilRecyclerAdapter
import com.revolution.robotics.features.build.connect.availableRobotsFace.ConnectMvp
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig

@Suppress("DataClassContainsFunctions")
data class ConnectRobotItem(val robotId: Int, val name: String, private val presenter: ConnectMvp.Presenter) :
    DiffUtilRecyclerAdapter.BaseListViewModel() {

    companion object {
        private val defaultBoxConfig: ChippedBoxConfig = ChippedBoxConfig.Builder()
            .chipSize(R.dimen.dimen_10dp)
            .backgroundColorResource(R.color.grey_28)
            .borderColorResource(R.color.grey_8e)
            .borderSize(R.dimen.dimen_1dp)
            .clipLeftSide(true)
            .create()

        private val selectedBoxConfig: ChippedBoxConfig = ChippedBoxConfig.Builder()
            .chipSize(R.dimen.dimen_10dp)
            .backgroundColorResource(R.color.grey_28)
            .borderColorResource(R.color.white)
            .borderSize(R.dimen.dimen_1dp)
            .clipLeftSide(true)
            .create()
    }

    override val idField = robotId
    val boxConfig: MutableLiveData<ChippedBoxConfig> = MutableLiveData()
    val isProgressBarVisible: MutableLiveData<Boolean> = MutableLiveData()

    init {
        setSelected(false)
    }

    fun setSelected(isSelected: Boolean) {
        boxConfig.value = if (isSelected) selectedBoxConfig else defaultBoxConfig
        isProgressBarVisible.value = isSelected
    }

    fun onItemClicked() {
        presenter.onItemClicked(robotId)
    }
}

package com.revolution.robotics.features.availableRobots.adapter

import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.revolution.robotics.R
import com.revolution.robotics.core.utils.recyclerview.DiffUtilRecyclerAdapter
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig

data class AvailableRobotsItem(
    override val idField: Any,
    var name: String = "Robot ID"
) : DiffUtilRecyclerAdapter.BaseListViewModel() {
    val boxConfig: MutableLiveData<ChippedBoxConfig> = MutableLiveData()
    val isSelected: ObservableBoolean = ObservableBoolean(false)
    val isProgressBarVisible: MutableLiveData<Boolean> = MutableLiveData()

    companion object {
        val defaultBoxConfig: ChippedBoxConfig = ChippedBoxConfig.Builder()
            .chipSize(R.dimen.dimen_10dp)
            .backgroundColorResource(R.color.grey_28)
            .borderColorResource(R.color.grey_8e)
            .chipBottomLeft(true)
            .chipTopRight(true)
            .borderSize(R.dimen.dimen_1dp)
            .clipLeftSide(true)
            .create()

        val selectedBoxConfig: ChippedBoxConfig = ChippedBoxConfig.Builder()
            .chipSize(R.dimen.dimen_10dp)
            .backgroundColorResource(R.color.grey_28)
            .borderColorResource(R.color.white)
            .chipBottomLeft(true)
            .chipTopRight(true)
            .borderSize(R.dimen.dimen_1dp)
            .clipLeftSide(true)
            .create()
    }

    init {
        isSelected.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable, propertyId: Int) {
                if (isSelected.get()) {
                    boxConfig.value = selectedBoxConfig
                    isProgressBarVisible.value = true
                } else {
                    boxConfig.value = defaultBoxConfig
                    isProgressBarVisible.value = false
                }
            }
        })

        boxConfig.value = defaultBoxConfig
    }

    fun onItemClick(){
        isSelected.set(true)
        TODO("Select/Deselect items")
    }
}

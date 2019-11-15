package com.revolution.robotics.features.configure

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import com.revolution.robotics.R
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig

class ActionButtonsViewModel(
    private val doneClickListener: () -> Unit,
    private val testClickListener: () -> Unit
) {

    val testButtonChippedBoxConfig = ChippedBoxConfig.Builder()
        .backgroundColorResource(R.color.grey_1d)
        .borderColorResource(R.color.grey_1d)
        .chipBottomLeft(true)
        .chipTopRight(false)
        .chipBottomRight(false)
        .chipTopLeft(false)
        .chipSize(R.dimen.dimen_8dp)
        .borderSize(R.dimen.dimen_1dp)
        .create()
    val doneTextColor = ObservableInt()
    val doneButtonEnabled = ObservableBoolean()
    val testTextColor = ObservableInt()
    val testButtonEnabled = ObservableBoolean()

    val doneButtonChippedBoxConfig = MutableLiveData<ChippedBoxConfig>()

    fun onTestButtonClicked() {
        testClickListener.invoke()
    }

    fun onDoneButtonClicked() {
        doneClickListener.invoke()
    }
}

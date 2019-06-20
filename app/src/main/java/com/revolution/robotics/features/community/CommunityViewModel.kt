package com.revolution.robotics.features.community

import androidx.lifecycle.ViewModel
import com.revolution.robotics.R
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig

class CommunityViewModel(private val presenter: CommunityMvp.Presenter) : ViewModel() {

    val buttonBackground = ChippedBoxConfig.Builder()
        .backgroundColorResource(R.color.grey_28)
        .borderColorResource(R.color.white)
        .chipSize(R.dimen.dimen_8dp)
        .create()

    fun openCommunityForums() {
        presenter.openCommunityForums()
    }
}

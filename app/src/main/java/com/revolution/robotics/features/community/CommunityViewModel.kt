package com.revolution.robotics.features.community

import androidx.lifecycle.ViewModel

class CommunityViewModel(private val presenter: CommunityMvp.Presenter) : ViewModel() {

    fun openCommunity() {
        presenter.openCommunity()
    }
}

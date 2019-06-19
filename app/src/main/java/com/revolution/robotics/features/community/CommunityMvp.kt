package com.revolution.robotics.features.community

import com.revolution.robotics.core.Mvp

interface CommunityMvp : Mvp {

    interface View : Mvp.View {
        fun openCommunity()
    }

    interface Presenter : Mvp.Presenter<View, CommunityViewModel> {
        fun openCommunity()
    }
}

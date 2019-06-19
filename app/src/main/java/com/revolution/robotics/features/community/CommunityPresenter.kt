package com.revolution.robotics.features.community

class CommunityPresenter : CommunityMvp.Presenter {

    override var view: CommunityMvp.View? = null
    override var model: CommunityViewModel? = null

    override fun openCommunity() {
        view?.openCommunity()
    }
}

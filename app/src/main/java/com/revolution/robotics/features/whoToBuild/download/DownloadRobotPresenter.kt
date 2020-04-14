package com.revolution.robotics.features.whoToBuild.download

import android.util.Log
import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.interactor.api.DownloadRobotInteractor
import com.revolution.robotics.core.interactor.firebase.RobotInteractor

class DownloadRobotPresenter(
    private val robotInteractor: RobotInteractor,
    private val downloadRobotInteractor: DownloadRobotInteractor
) : DownloadRobotMVP.Presenter {

    override var view: DownloadRobotMVP.View? = null
    override var model: ViewModel? = null

    override fun downloadRobot(robotId: String) {
        var start = System.currentTimeMillis()
        robotInteractor.robotId = robotId
        robotInteractor.execute() { robot ->
            downloadRobotInteractor.robot = robot
            downloadRobotInteractor.execute(
                onResponse = {
                    Log.d(
                        "ROBOTS",
                        robot?.name?.en + " downloaded in " + (System.currentTimeMillis() - start) / 1000 + " sec"
                    )
                    view?.showSuccess()
                }, onError = {
                    Log.d("ROBOTS", "Failed to download " + robot?.name?.en)
                    view?.showError()
                }
            )
        }
    }
}
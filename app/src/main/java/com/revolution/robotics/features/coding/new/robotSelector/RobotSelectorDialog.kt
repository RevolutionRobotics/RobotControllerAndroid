package com.revolution.robotics.features.coding.new.robotSelector

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.databinding.DialogRobotSelectorBinding
import com.revolution.robotics.features.coding.programs.adapter.RobotViewModel
import com.revolution.robotics.features.coding.programs.adapter.RobotsAdapter
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog
import org.kodein.di.erased.instance

class RobotSelectorDialog: RoboticsDialog(), RobotSelectorMvp.View {

    companion object {
        const val KEY_ROBOT = "robot"

        fun newInstance() = RobotSelectorDialog()
    }

    private val presenter: RobotSelectorMvp.Presenter by kodein.instance()
    private val dialogFace = RobotSelectorDialogFace()

    override val hasCloseButton = false
    override val dialogFaces: List<DialogFace<*>> = listOf(dialogFace)
    override val dialogButtons = emptyList<DialogButton>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.register(this, null)
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }

    override fun onRobotsLoaded(robots: List<RobotViewModel>) {
        dialogFace.onProgramsLoaded(robots)
    }

    override fun onRobotSelected(robot: UserRobot) {
        dismissAllowingStateLoss()
        dialogEventBus.publish(DialogEvent.ROBOT_SELECTED.apply {
            extras.putParcelable(KEY_ROBOT, robot)
        })
    }

    private inner class RobotSelectorDialogFace : DialogFace<DialogRobotSelectorBinding>(R.layout.dialog_robot_selector) {

        private val adapter = RobotsAdapter()

        override fun onActivated() {
            binding?.recycler?.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = this@RobotSelectorDialogFace.adapter
            }
            presenter.loadRobots()
        }

        fun onProgramsLoaded(robots: List<RobotViewModel>) {
            adapter.setItems(robots)
        }
    }
}

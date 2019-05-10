package com.revolution.robotics.features.build.connect.availableRobotsFace

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.revolution.robotics.R
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.databinding.DialogConnectBinding
import com.revolution.robotics.features.build.connect.adapter.ConnectAdapter
import com.revolution.robotics.views.dialogs.MvpDialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog
import org.kodein.di.erased.instance

class ConnectDialogFace(dialog: RoboticsDialog) :
    MvpDialogFace<DialogConnectBinding, ConnectViewModel>(R.layout.dialog_connect, dialog),
    ConnectMvp.View {

    override val viewModelClass: Class<ConnectViewModel> = ConnectViewModel::class.java
    private val presenter: ConnectMvp.Presenter by kodein.instance()
    private val dialogEventBus: DialogEventBus by kodein.instance()

    override fun onViewCreated(fragment: Fragment, container: ViewGroup) {
        presenter.register(this, viewModel)
        viewModel?.let {
            binding?.robotsRecyclerView?.let { recycler ->
                recycler.layoutManager = LinearLayoutManager(container.context)
                recycler.adapter = ConnectAdapter(fragment.viewLifecycleOwner)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unregister()
    }

    override fun onConnectionSuccess() {
        dialog?.dismissAllowingStateLoss()
        dialogEventBus.publish(DialogEvent.ROBOT_CONNECTED)
    }

    override fun onConnectionError() {
        dialog?.dismissAllowingStateLoss()
        dialogEventBus.publish(DialogEvent.ROBOT_CONNECTION_FAILED)
    }
}

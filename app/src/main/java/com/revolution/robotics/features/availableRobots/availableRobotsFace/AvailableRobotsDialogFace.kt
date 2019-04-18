package com.revolution.robotics.features.availableRobots.availableRobotsFace

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.revolution.robotics.R
import com.revolution.robotics.databinding.DialogAvailableRobotsBinding
import com.revolution.robotics.features.availableRobots.adapter.AvailableRobotsAdapter
import com.revolution.robotics.views.dialogs.DialogViewModelFace
import org.kodein.di.erased.instance

class AvailableRobotsDialogFace :
    DialogViewModelFace<DialogAvailableRobotsBinding, AvailableRobotsDialogFaceViewModel>(R.layout.dialog_available_robots),
    AvailableRobotsDialogFaceMvp.View {
    override val viewModelClass: Class<AvailableRobotsDialogFaceViewModel> = AvailableRobotsDialogFaceViewModel::class.java
    private val presenter: AvailableRobotsDialogFaceMvp.Presenter by kodein.instance()
    private lateinit var adapter: AvailableRobotsAdapter

    override fun activate(fragment: Fragment, inflater: LayoutInflater, container: ViewGroup): View? {
        val view = super.activate(fragment, inflater, container)
        presenter.register(this, viewModel)

        viewModel?.let {
            adapter = AvailableRobotsAdapter(fragment)
            binding?.robotsRecyclerView?.let { rv ->
                rv.adapter = adapter
                rv.layoutManager = LinearLayoutManager(fragment.context)
            }
        }
        return view
    }
}

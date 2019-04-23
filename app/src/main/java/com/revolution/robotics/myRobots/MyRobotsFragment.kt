package com.revolution.robotics.myRobots

import android.os.Bundle
import android.view.View
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.databinding.FragmentMyRobotsBinding
import org.kodein.di.erased.instance

// TODO check if empty state background can be updated to use SVG image
class MyRobotsFragment : BaseFragment<FragmentMyRobotsBinding, MyRobotsViewModel>(R.layout.fragment_my_robots),
    MyRobotsMvp.View {

    override val viewModelClass: Class<MyRobotsViewModel> = MyRobotsViewModel::class.java
    private val resourceResolver: ResourceResolver by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.toolbarViewModel = MyRobotsToolbarViewModel(resourceResolver)
    }
}
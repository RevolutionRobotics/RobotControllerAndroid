package com.revolution.robotics.myRobots

import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.databinding.FragmentMyRobotsBinding

class MyRobotsFragment : BaseFragment<FragmentMyRobotsBinding, MyRobotsViewModel>(R.layout.fragment_my_robots),
    MyRobotsMvp.View {

    override val viewModelClass: Class<MyRobotsViewModel> = MyRobotsViewModel::class.java
}

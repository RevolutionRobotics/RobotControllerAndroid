package com.revolution.robotics.robots

import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.databinding.FragmentRobotsBinding

class RobotsFragment : BaseFragment<FragmentRobotsBinding, RobotsViewModel>(R.layout.fragment_robots) {

    override val viewModelClass: Class<RobotsViewModel> = RobotsViewModel::class.java
}

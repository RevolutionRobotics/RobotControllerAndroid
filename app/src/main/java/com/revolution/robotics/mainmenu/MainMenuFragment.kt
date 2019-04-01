package com.revolution.robotics.mainmenu

import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.databinding.FragmentMenuBinding

class MainMenuFragment : BaseFragment<FragmentMenuBinding, MainMenuViewModel>(R.layout.fragment_menu) {

    override val viewModelClass: Class<MainMenuViewModel> = MainMenuViewModel::class.java
}

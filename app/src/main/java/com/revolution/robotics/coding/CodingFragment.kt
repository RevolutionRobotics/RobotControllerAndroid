package com.revolution.robotics.coding

import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.databinding.FragmentCodingBinding

class CodingFragment :
    BaseFragment<FragmentCodingBinding, CodingViewModel>(R.layout.fragment_coding) {

    override val viewModelClass: Class<CodingViewModel> = CodingViewModel::class.java
}

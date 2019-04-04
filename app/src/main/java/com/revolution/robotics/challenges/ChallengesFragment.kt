package com.revolution.robotics.challenges

import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.databinding.FragmentChallengesBinding

class ChallengesFragment :
    BaseFragment<FragmentChallengesBinding, ChallengesViewModel>(R.layout.fragment_challenges) {

    override val viewModelClass: Class<ChallengesViewModel> = ChallengesViewModel::class.java
}

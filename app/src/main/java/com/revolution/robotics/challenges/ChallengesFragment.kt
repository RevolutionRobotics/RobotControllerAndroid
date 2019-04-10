package com.revolution.robotics.challenges

import android.os.Bundle
import android.view.View
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.databinding.FragmentChallengesBinding
import com.revolution.robotics.blockly.DialogFactory

class ChallengesFragment : BaseFragment<FragmentChallengesBinding, ChallengesViewModel>(R.layout.fragment_challenges) {

    override val viewModelClass: Class<ChallengesViewModel> = ChallengesViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.viewBlockly?.init("file:///android_asset/play_sound.html", DialogFactory())
    }
}

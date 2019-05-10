package com.revolution.robotics.features.challenges.challengeList

import android.os.Bundle
import android.view.View
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.databinding.FragmentChallengeListBinding
import org.kodein.di.erased.instance

class ChallengeListFragment :
    BaseFragment<FragmentChallengeListBinding, ChallengeListViewModel>(R.layout.fragment_challenge_list) {

    override val viewModelClass: Class<ChallengeListViewModel> = ChallengeListViewModel::class.java

    private val resourceResolver: ResourceResolver by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.toolbarViewModel = ChallengeListToolbarViewModel(resourceResolver)
    }
}

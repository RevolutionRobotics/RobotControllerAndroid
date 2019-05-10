package com.revolution.robotics.features.challenges.challengeList

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.databinding.FragmentChallengeListBinding
import com.revolution.robotics.features.challenges.challengeList.adapter.ChallengeItem
import com.revolution.robotics.features.challenges.challengeList.adapter.ChallengeListAdapter
import org.kodein.di.erased.instance

class ChallengeListFragment :
    BaseFragment<FragmentChallengeListBinding, ChallengeListViewModel>(R.layout.fragment_challenge_list) {

    companion object {
        private const val SPAN_COUNT = 3
    }

    override val viewModelClass: Class<ChallengeListViewModel> = ChallengeListViewModel::class.java

    private val resourceResolver: ResourceResolver by kodein.instance()
    private val adapter = ChallengeListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.apply {
            toolbarViewModel = ChallengeListToolbarViewModel(resourceResolver)
            recycler.adapter = adapter
            recycler.layoutManager = GridLayoutManager(context, SPAN_COUNT)
        }

        adapter.setItems((0..100).mapIndexed { index, i ->
            ChallengeItem(
                "gs://revolutionrobotics-afeb5.appspot.com/9eaaf2c756cce2cb1b5e3002b61d9cb5.jpg",
                "Challenge #$index",
                index % 22,
                21
            )
        })
    }
}

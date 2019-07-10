package com.revolution.robotics.features.challenges.challengeList

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.remote.ChallengeCategory
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.databinding.FragmentChallengeListBinding
import com.revolution.robotics.features.challenges.challengeList.adapter.ChallengeListAdapter
import org.kodein.di.erased.instance

class ChallengeListFragment :
    BaseFragment<FragmentChallengeListBinding, ChallengeListViewModel>(R.layout.fragment_challenge_list),
    ChallengeListMvp.View {

    companion object {
        private var Bundle.challenge: ChallengeCategory by BundleArgumentDelegate.Parcelable("challenge")

        fun newInstance(challenge: ChallengeCategory) = ChallengeListFragment().withArguments {
            it.challenge = challenge
        }
    }

    override val viewModelClass: Class<ChallengeListViewModel> = ChallengeListViewModel::class.java

    val presenter: ChallengeListMvp.Presenter by kodein.instance()
    val adapter = ChallengeListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            toolbarViewModel = ChallengeListToolbarViewModel()
            recycler.adapter = adapter
            recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        presenter.register(this, viewModel)
        arguments?.let { arguments ->
            binding?.toolbarViewModel?.title?.set(arguments.challenge.name?.getLocalizedString() ?: "")
            presenter.setChallengeCategory(arguments.challenge)
        }
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }
}

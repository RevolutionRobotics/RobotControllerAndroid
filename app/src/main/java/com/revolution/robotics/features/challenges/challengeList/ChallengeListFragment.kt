package com.revolution.robotics.features.challenges.challengeList

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.analytics.Reporter
import com.revolution.robotics.core.domain.remote.ChallengeCategory
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.core.utils.AppPrefs
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.databinding.FragmentChallengeListBinding
import com.revolution.robotics.features.challenges.challengeList.adapter.ChallengeListAdapter
import com.revolution.robotics.features.onboarding.congratulations.CongratulationsDialog
import org.kodein.di.erased.instance

class ChallengeListFragment :
    BaseFragment<FragmentChallengeListBinding, ChallengeListViewModel>(R.layout.fragment_challenge_list),
    ChallengeListMvp.View {

    companion object {
        private var Bundle.challengeCategoryId: String by BundleArgumentDelegate.String("challengeCategoryId")

        fun newInstance(challengeCategoryId: String) = ChallengeListFragment().withArguments {
            it.challengeCategoryId = challengeCategoryId
        }
    }

    override val viewModelClass: Class<ChallengeListViewModel> = ChallengeListViewModel::class.java
    override val screen = Reporter.Screen.CHALLENGE_LIST

    val presenter: ChallengeListMvp.Presenter by kodein.instance()
    val resourceResolver: ResourceResolver by kodein.instance()
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
            presenter.loadChallangeCategory(arguments.challengeCategoryId)
        }
    }

    override fun displayChallengeCategory(challengeCategory: ChallengeCategory) {
        binding?.toolbarViewModel?.title?.set(challengeCategory.name?.getLocalizedString(resourceResolver) ?: "")
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }
}

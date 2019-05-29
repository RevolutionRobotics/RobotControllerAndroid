package com.revolution.robotics.features.challenges.challengeDetail

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.remote.Challenge
import com.revolution.robotics.core.domain.remote.ChallengeStep
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.databinding.FragmentChallengeDetailBinding
import com.revolution.robotics.features.challenges.challengeDetail.adapter.ChallengePartAdapter
import com.revolution.robotics.features.challenges.challengeList.ChallengeListFragment
import org.kodein.di.erased.instance

class ChallengeDetailFragment :
    BaseFragment<FragmentChallengeDetailBinding, ChallengeDetailViewModel>(R.layout.fragment_challenge_detail),
    ChallengeDetailMvp.View {

    companion object {
        private var Bundle.challenge: Challenge by BundleArgumentDelegate.Parcelable("challenge")
        private var Bundle.categoryId: String by BundleArgumentDelegate.String("categoryId")
        const val SPAN_COUNT = 4

        fun newInstance(challenge: Challenge, categoryId: String) = ChallengeListFragment().withArguments { bundle ->
            bundle.challenge = challenge
            bundle.categoryId = categoryId
        }
    }

    override val viewModelClass: Class<ChallengeDetailViewModel> = ChallengeDetailViewModel::class.java

    private val presenter: ChallengeDetailMvp.Presenter by kodein.instance()
    private val adapter = ChallengePartAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.register(this, viewModel)
        binding?.apply {
            toolbarViewModel = ChallengeDetailToolbarViewModel().apply {
                presenter.toolbarViewModel = this
            }
            recyclerParts.adapter = adapter
            recyclerParts.layoutManager = GridLayoutManager(context, SPAN_COUNT)
        }

        arguments?.let {
            presenter.setChallenge(it.challenge, it.categoryId)
        }
    }

    override fun onDestroyView() {
        presenter.unregister(this)
        super.onDestroyView()
    }

    override fun showChallengeFinishedDialog(nextChallenge: Challenge?) {
        if (nextChallenge == null) {
            ChallengeDetailFinishedDialog.Latest.newInstance().show(fragmentManager)
        } else {
            ChallengeDetailFinishedDialog.Intermediate.newInstance(nextChallenge, arguments?.categoryId ?: "")
                .show(fragmentManager)
        }
    }

    override fun initSlider(steps: List<ChallengeStep>, listener: ChallengeDetailSlider.ChallengeStepSelectedListener) {
        binding?.sliderChallengeDetail?.setChallengeSteps(steps, listener)
    }
}
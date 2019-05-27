package com.revolution.robotics.features.challenges.challengeDetail

import android.os.Bundle
import android.view.View
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.remote.Challenge
import com.revolution.robotics.core.domain.remote.ChallengeStep
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.databinding.FragmentChallengeDetailBinding
import com.revolution.robotics.features.challenges.challengeList.ChallengeListFragment
import org.kodein.di.erased.instance

class ChallengeDetailFragment :
    BaseFragment<FragmentChallengeDetailBinding, ChallengeDetailViewModel>(R.layout.fragment_challenge_detail),
    ChallengeDetailMvp.View {

    companion object {
        private var Bundle.challenge: Challenge by BundleArgumentDelegate.Parcelable("challenge")

        fun newInstance(challenge: Challenge) = ChallengeListFragment().withArguments {
            it.challenge = challenge
        }
    }

    override val viewModelClass: Class<ChallengeDetailViewModel> = ChallengeDetailViewModel::class.java

    private val presenter: ChallengeDetailMvp.Presenter by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.register(this, viewModel)
        binding?.toolbarViewModel = ChallengeDetailToolbarViewModel().apply {
            presenter.toolbarViewModel = this
        }
        arguments?.let {
            presenter.setChallenge(it.challenge)
        }
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }

    override fun initSlider(steps: List<ChallengeStep>, listener: ChallengeDetailSlider.ChallengeStepSelectedListener) {
        binding?.sliderChallengeDetail?.setChallengeSteps(steps, listener)
    }
}

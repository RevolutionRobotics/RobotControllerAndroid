package com.revolution.robotics.features.challenges.challengeDetail

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.analytics.Reporter
import com.revolution.robotics.core.domain.remote.Challenge
import com.revolution.robotics.core.domain.remote.ChallengeStep
import com.revolution.robotics.core.extensions.integer
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.databinding.FragmentChallengeDetailBinding
import com.revolution.robotics.features.challenges.challengeDetail.ChallengeDetailFragment.Companion.challenge
import com.revolution.robotics.features.challenges.challengeDetail.adapter.ChallengePartAdapter
import com.revolution.robotics.features.challenges.challengeList.ChallengeListFragment
import com.revolution.robotics.features.community.CommunityFragment
import com.revolution.robotics.features.shared.ErrorHandler
import org.kodein.di.erased.instance

class ChallengeDetailFragment :
    BaseFragment<FragmentChallengeDetailBinding, ChallengeDetailViewModel>(R.layout.fragment_challenge_detail),
    ChallengeDetailMvp.View {

    companion object {
        private var Bundle.challenge: Challenge by BundleArgumentDelegate.Parcelable("challenge")
        private var Bundle.categoryId: String by BundleArgumentDelegate.String("categoryId")

        fun newInstance(challenge: Challenge, categoryId: String) = ChallengeListFragment().withArguments { bundle ->
            bundle.challenge = challenge
            bundle.categoryId = categoryId
        }
    }

    override val viewModelClass: Class<ChallengeDetailViewModel> = ChallengeDetailViewModel::class.java
    override val screen = Reporter.Screen.CHALLENGE_DETAILS

    private val presenter: ChallengeDetailMvp.Presenter by kodein.instance()
    private val errorHandler: ErrorHandler by kodein.instance()
    private val adapter = ChallengePartAdapter()
    private var challengeId: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.register(this, viewModel)
        binding?.apply {
            toolbarViewModel = ChallengeDetailToolbarViewModel().apply {
                presenter.toolbarViewModel = this
            }
            recyclerParts.adapter = adapter
            recyclerParts.layoutManager =
                GridLayoutManager(context, requireContext().integer(R.integer.challenge_span_count))
        }

        arguments?.let {
            challengeId = it.challenge.id
            presenter.setChallenge(it.challenge, it.categoryId)
            reporter.reportEvent(Reporter.Event.START_NEW_CHALLENGE, Bundle().apply {
                putString(Reporter.Parameter.ID.parameterName, it.challenge.id)
            })
        }
    }

    override fun onDestroyView() {
        presenter.unregister(this)
        super.onDestroyView()
    }

    override fun showChallengeFinishedDialog(nextChallenge: Challenge?) {
        reporter.reportEvent(Reporter.Event.FINISH_CHALLENGE, Bundle().apply {
            putString(Reporter.Parameter.ID.parameterName, challengeId)
        })
        if (nextChallenge == null) {
            ChallengeDetailFinishedDialog.Latest.newInstance().show(fragmentManager)
        } else {
            ChallengeDetailFinishedDialog.Intermediate.newInstance(nextChallenge, arguments?.categoryId ?: "")
                .show(fragmentManager)
        }
    }

    override fun openUrl(url: String) {
        try {
            requireActivity().startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        } catch (exception: ActivityNotFoundException) {
            errorHandler.onError(exception, R.string.error_cannot_open_url)
        }
    }

    override fun initSlider(steps: List<ChallengeStep>, listener: ChallengeDetailSlider.ChallengeStepSelectedListener) {
        binding?.sliderChallengeDetail?.setChallengeSteps(steps, listener)
    }
}

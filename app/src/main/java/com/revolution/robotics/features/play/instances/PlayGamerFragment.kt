package com.revolution.robotics.features.play.instances

import android.view.LayoutInflater
import android.view.ViewGroup
import com.revolution.robotics.core.domain.remote.ChallengeCategory
import com.revolution.robotics.core.interactor.firebase.ChallengeCategoriesInteractor
import com.revolution.robotics.databinding.FragmentPlayGamerBinding
import com.revolution.robotics.features.play.JoystickView
import com.revolution.robotics.features.play.PlayFragment
import org.kodein.di.erased.instance

class PlayGamerFragment : PlayFragment(), JoystickView.JoystickEventListener {

    override val reverseYAxis: Boolean = true
    override val reverseXAxis: Boolean = false

    private lateinit var contentBinding: FragmentPlayGamerBinding
    private val challengeCategoriesInteractor: ChallengeCategoriesInteractor by kodein.instance()

    override fun createContentView(inflater: LayoutInflater, container: ViewGroup?) {
        contentBinding = FragmentPlayGamerBinding.inflate(inflater, container, true)
        contentBinding.viewModel = viewModel
        contentBinding.joystick.listener = this
        contentBinding.btnOnboardingReady.setOnClickListener {goToChallenges()
        }
    }

    private fun goToChallenges() {
        challengeCategoriesInteractor.execute { challengeCategories ->
            challengeCategories.find { it.id == "ef504b31-d64f-4bfb-bd4b-5b96a9a0489f" }?.let {
                navigator.navigate(PlayGamerFragmentDirections.toChallangeList(it))
            }
        }
    }

    override fun getContentBinding() = contentBinding

    override fun onJoystickPositionChanged(x: Int, y: Int) {
        presenter.onJoystickXAxisChanged(x)
        presenter.onJoystickYAxisChanged(y)
    }
}

package com.revolution.robotics.features.onboarding.carby

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.BuildStatus
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.interactor.firebase.RobotInteractor
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.databinding.FragmentHaveYouBuiltCarbyBinding
import com.revolution.robotics.features.build.BuildRobotFragment
import com.revolution.robotics.features.whoToBuild.WhoToBuildFragmentDirections
import org.kodein.di.KodeinAware
import org.kodein.di.LateInitKodein
import org.kodein.di.erased.instance
import java.util.*

class HaveYouBuiltCarbyFragment : Fragment() {

    companion object {
        private const val carbyId: String = "2d9b67e-804e-4022-8cae-5a26071fa275"
    }
    private var kodein = LateInitKodein()
    private val navigator: Navigator by kodein.instance()
    private val resourceResolver: ResourceResolver by kodein.instance()
    private val robotInteractor: RobotInteractor by kodein.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        kodein.baseKodein = (requireContext().applicationContext as KodeinAware).kodein
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentHaveYouBuiltCarbyBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_have_you_built_carby, container, false)
        binding.btnYes.setOnClickListener { driveCarby() }
        binding.btnNo.setOnClickListener { buildCarby() }
        binding.btnSkip.setOnClickListener { skipOnboarding() }
        return binding.root
    }

    private fun buildCarby() {
        robotInteractor.robotId = carbyId
        robotInteractor.execute {robot ->
            val userRobot = UserRobot(
                0,
                robot.id,
                BuildStatus.IN_PROGRESS,
                BuildRobotFragment.DEFAULT_STARTING_INDEX,
                Date(System.currentTimeMillis()),
                0,
                robot.name?.getLocalizedString(resourceResolver) ?: "",
                robot.coverImage,
                robot.description?.getLocalizedString(resourceResolver) ?: ""
            )
            navigator.navigate(HaveYouBuiltCarbyFragmentDirections.toBuildRobot(userRobot))
        }

    }

    private fun driveCarby() {

    }

    private fun skipOnboarding() {

    }
}
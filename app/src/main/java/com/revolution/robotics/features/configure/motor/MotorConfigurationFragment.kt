package com.revolution.robotics.features.configure.motor

import android.os.Bundle
import android.view.View
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.remote.Motor
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.databinding.FragmentMotorConfigBinding
import org.kodein.di.erased.instance

class MotorConfigurationFragment :
    BaseFragment<FragmentMotorConfigBinding, MotorConfigurationViewModel>(R.layout.fragment_motor_config),
    MotorConfigurationMvp.View {

    companion object {
        private var Bundle.motor by BundleArgumentDelegate.Parcelable<Motor>("motor")

        fun newInstance(motor: Motor) = MotorConfigurationFragment().withArguments {
            it.motor = motor
        }
    }

    private val presenter: MotorConfigurationMvp.Presenter by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.register(this, viewModel)
        arguments?.let {
            presenter.setMotor(it.motor)
        }
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }

    override val viewModelClass: Class<MotorConfigurationViewModel> = MotorConfigurationViewModel::class.java


}
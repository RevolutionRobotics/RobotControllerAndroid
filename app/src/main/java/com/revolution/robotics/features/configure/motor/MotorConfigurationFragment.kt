package com.revolution.robotics.features.configure.motor

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
        private var Bundle.portName by BundleArgumentDelegate.String("portName")

        fun newInstance(motor: Motor, portName: String) = MotorConfigurationFragment().withArguments { bundle ->
            bundle.motor = motor
            bundle.portName = portName
        }
    }

    override val viewModelClass: Class<MotorConfigurationViewModel> = MotorConfigurationViewModel::class.java
    private val presenter: MotorConfigurationMvp.Presenter by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.register(this, viewModel)
        arguments?.let {
            presenter.setMotor(it.motor, it.portName)
        }
        binding?.editMotor?.binding?.content?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                presenter.onVariableNameChanged(text?.toString() ?: "")
            }
        })
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }
}

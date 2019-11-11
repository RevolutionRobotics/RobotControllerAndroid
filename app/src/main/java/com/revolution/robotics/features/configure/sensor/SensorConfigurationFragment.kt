package com.revolution.robotics.features.configure.sensor

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.remote.Sensor
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.databinding.FragmentSensorConfigurationBinding
import org.kodein.di.erased.contextFinder
import org.kodein.di.erased.instance

class SensorConfigurationFragment :
    BaseFragment<FragmentSensorConfigurationBinding, SensorConfigurationViewModel>
        (R.layout.fragment_sensor_configuration), SensorConfigurationMvp.View {

    companion object {
        private var Bundle.sensor by BundleArgumentDelegate.Parcelable<Sensor>("sensor")
        private var Bundle.portName by BundleArgumentDelegate.String("portName")
        private var Bundle.robotId by BundleArgumentDelegate.Int("robotId")

        fun newInstance(robotId: Int, sensor: Sensor, portName: String) =
            SensorConfigurationFragment().withArguments { bundle ->
                bundle.robotId = robotId
                bundle.sensor = sensor
                bundle.portName = portName
            }
    }

    override val viewModelClass: Class<SensorConfigurationViewModel> = SensorConfigurationViewModel::class.java
    private val presenter: SensorConfigurationMvp.Presenter by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.register(this, viewModel)
        arguments?.let {
            presenter.setSensor(it.robotId, it.sensor, it.portName)
        }
        binding?.editSensor?.binding?.content?.addTextChangedListener(object : TextWatcher {
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

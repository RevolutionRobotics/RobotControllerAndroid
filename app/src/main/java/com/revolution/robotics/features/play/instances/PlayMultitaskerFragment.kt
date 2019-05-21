package com.revolution.robotics.features.play.instances

import android.view.LayoutInflater
import android.view.ViewGroup
import com.revolution.robotics.databinding.FragmentPlayMultitaskerBinding
import com.revolution.robotics.features.play.JoystickView
import com.revolution.robotics.features.play.PlayFragment

class PlayMultitaskerFragment : PlayFragment(), JoystickView.JoystickEventListener {

    private lateinit var contentBinding: FragmentPlayMultitaskerBinding

    override fun createContentView(inflater: LayoutInflater, container: ViewGroup?) {
        contentBinding = FragmentPlayMultitaskerBinding.inflate(inflater, container, true)
        contentBinding.viewModel = viewModel
        contentBinding.joystick.listener = this
    }

    override fun onJoystickPositionChanged(x: Int, y: Int) {
        presenter.onJoystickXAxisChanged(x)
        presenter.onJoystickYAxisChanged(y)
    }
}

package com.revolution.robotics.features.play.instances

import android.view.LayoutInflater
import android.view.ViewGroup
import com.revolution.robotics.databinding.FragmentPlayDriverBinding
import com.revolution.robotics.features.play.JoystickView
import com.revolution.robotics.features.play.PlayFragment

class PlayDriverFragment : PlayFragment(), JoystickView.JoystickEventListener {

    private lateinit var contentBinding: FragmentPlayDriverBinding

    override fun createContentView(inflater: LayoutInflater, container: ViewGroup?) {
        contentBinding = FragmentPlayDriverBinding.inflate(inflater, container, true)
        contentBinding.viewModel = viewModel
        contentBinding.joystick.listener = this
    }

    override fun onJoystickPositionChanged(x: Int, y: Int) {
        presenter.onJoystickXAxisChanged(x)
        presenter.onJoystickYAxisChanged(y)
    }
}

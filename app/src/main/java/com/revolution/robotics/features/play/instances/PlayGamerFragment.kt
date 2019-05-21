package com.revolution.robotics.features.play.instances

import android.view.LayoutInflater
import android.view.ViewGroup
import com.revolution.robotics.databinding.FragmentPlayGamerBinding
import com.revolution.robotics.features.play.JoystickView
import com.revolution.robotics.features.play.PlayFragment

class PlayGamerFragment : PlayFragment(), JoystickView.JoystickEventListener {

    private lateinit var contentBinding: FragmentPlayGamerBinding

    override fun createContentView(inflater: LayoutInflater, container: ViewGroup?) {
        contentBinding = FragmentPlayGamerBinding.inflate(inflater, container, true)
        contentBinding.viewModel = viewModel
        contentBinding.joystick.listener = this
    }

    override fun onJoystickPositionChanged(x: Int, y: Int) {
        presenter.onJoystickXAxisChanged(x)
        presenter.onJoystickYAxisChanged(y)
    }
}

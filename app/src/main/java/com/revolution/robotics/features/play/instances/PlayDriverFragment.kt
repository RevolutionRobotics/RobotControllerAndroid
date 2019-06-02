package com.revolution.robotics.features.play.instances

import android.view.LayoutInflater
import android.view.ViewGroup
import com.revolution.robotics.databinding.FragmentPlayDriverBinding
import com.revolution.robotics.features.play.PlayFragment

class PlayDriverFragment : PlayFragment() {

    private lateinit var contentBinding: FragmentPlayDriverBinding

    override fun createContentView(inflater: LayoutInflater, container: ViewGroup?) {
        contentBinding = FragmentPlayDriverBinding.inflate(inflater, container, true).apply {
            viewModel = this@PlayDriverFragment.viewModel
            leverLeft.onAxisChanged { y -> presenter.onJoystickYAxisChanged(y) }
            leverRight.onAxisChanged { x -> presenter.onJoystickXAxisChanged(x) }
        }
    }

    override fun getContentBinding() = contentBinding
}

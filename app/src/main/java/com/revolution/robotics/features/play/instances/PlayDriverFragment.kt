package com.revolution.robotics.features.play.instances

import android.view.LayoutInflater
import android.view.ViewGroup
import com.revolution.robotics.databinding.FragmentPlayDriverBinding
import com.revolution.robotics.features.play.PlayFragment

class PlayDriverFragment : PlayFragment() {

    private lateinit var contentBinding: FragmentPlayDriverBinding

    override val reverseYAxis: Boolean = true
    override val reverseXAxis: Boolean = true

    override fun createContentView(inflater: LayoutInflater, container: ViewGroup?) {
        contentBinding = FragmentPlayDriverBinding.inflate(inflater, container, true).apply {
            viewModel = this@PlayDriverFragment.viewModel
            leverLeft.onAxisChanged { y -> presenter.onJoystickXAxisChanged(y) }
            leverRight.onAxisChanged { x -> presenter.onJoystickYAxisChanged(x) }
        }
    }

    override fun getContentBinding() = contentBinding
}

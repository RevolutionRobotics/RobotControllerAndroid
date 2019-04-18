package com.revolution.robotics.controller

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.databinding.FragmentControllerBinding
import org.kodein.di.erased.instance

class LiveControllerFragment :
    BaseFragment<FragmentControllerBinding, LiveControllerViewModel>(R.layout.fragment_controller),
    LiveControllerMvp.View, SeekBar.OnSeekBarChangeListener {

    override val viewModelClass: Class<LiveControllerViewModel> = LiveControllerViewModel::class.java

    private val presenter: LiveControllerMvp.Presenter by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.register(this, viewModel)
        binding?.seekbarXCoord?.setOnSeekBarChangeListener(this)
        binding?.seekbarYCoord?.setOnSeekBarChangeListener(this)
    }

    override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (seekbar == binding?.seekbarXCoord) {
            presenter.onXAxisChanged(progress)
        }

        if (seekbar == binding?.seekbarYCoord) {
            presenter.onYAxisChanged(progress)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unregister()
    }

    override fun onStartTrackingTouch(p0: SeekBar?) = Unit

    override fun onStopTrackingTouch(p0: SeekBar?) = Unit
}

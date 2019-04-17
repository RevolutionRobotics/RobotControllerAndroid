package com.revolution.robotics.controller

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
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
        binding?.btn1?.setOnTouchListener(ButtonTouchListener(presenter, 1))
        binding?.btn2?.setOnTouchListener(ButtonTouchListener(presenter, 2))
        binding?.btn3?.setOnTouchListener(ButtonTouchListener(presenter, 3))
        binding?.btn4?.setOnTouchListener(ButtonTouchListener(presenter, 4))
        binding?.btn5?.setOnTouchListener(ButtonTouchListener(presenter, 5))
        binding?.btn6?.setOnTouchListener(ButtonTouchListener(presenter, 6))
        binding?.btn7?.setOnTouchListener(ButtonTouchListener(presenter, 7))
        binding?.btn8?.setOnTouchListener(ButtonTouchListener(presenter, 8))

        binding?.seekbarXCoord?.setOnSeekBarChangeListener(this)
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

    class ButtonTouchListener(private val presenter: LiveControllerMvp.Presenter, private val index: Int) :
        View.OnTouchListener {

        @SuppressLint("ClickableViewAccessibility")
        override fun onTouch(view: View, event: MotionEvent): Boolean {
            if (event.action == MotionEvent.ACTION_DOWN) {
                presenter.buttonActionDown(index)
            }
            if (event.action == MotionEvent.ACTION_UP) {
                presenter.buttonActionUp(index)
            }
            return true
        }
    }
}
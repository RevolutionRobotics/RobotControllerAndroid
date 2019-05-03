package com.revolution.robotics.views.dialogs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

open class DialogFace<B : ViewDataBinding>(
    @LayoutRes private val layoutResourceId: Int,
    protected val dialog: RoboticsDialog? = null
) {

    companion object {
        private var activeFace: DialogFace<*>? = null
    }

    protected var binding: B? = null

    open val dialogFaceButtons = emptyList<DialogButton>()

    open fun activate(fragment: Fragment, inflater: LayoutInflater, container: ViewGroup): View? {
        activeFace?.releaseFace()
        binding = DataBindingUtil.inflate(inflater, layoutResourceId, container, true)
        activeFace = this
        onActivated()
        return binding?.root
    }

    @Suppress("OptionalUnit")
    open fun onActivated() = Unit

    open fun releaseFace() {
        binding = null
    }
}

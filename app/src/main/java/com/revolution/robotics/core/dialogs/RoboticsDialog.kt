package com.revolution.robotics.core.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.revolution.robotics.BaseDialog
import com.revolution.robotics.databinding.DialogRoboticsCoreBinding

abstract class RoboticsDialog : BaseDialog() {

    abstract val dialogFaces: List<DialogFace<*>>

    private lateinit var coreBinding: DialogRoboticsCoreBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        coreBinding = DialogRoboticsCoreBinding.inflate(inflater, container, false)
        dialogFaces[0].activate(inflater, coreBinding.container)
        return coreBinding.root
    }

    fun activateFace(dialogFace: DialogFace<*>) {
        coreBinding.container.removeAllViews()
        dialogFace.activate(LayoutInflater.from(context), coreBinding.container)
    }

    open class DialogFace<B : ViewDataBinding>(@LayoutRes private val layoutResourceId: Int) {

        companion object {
            private var activeFace: DialogFace<*>? = null
        }

        protected var binding: B? = null

        fun activate(inflater: LayoutInflater, container: ViewGroup): View? {
            activeFace?.releaseFace()
            binding = DataBindingUtil.inflate(inflater, layoutResourceId, container, true)
            binding?.let { binding -> onViewCreated(binding) }
            activeFace = this
            return binding?.root
        }

        @Suppress("OptionalUnit")
        open fun onViewCreated(binding: B) = Unit

        private fun releaseFace() {
            binding = null
        }
    }
}

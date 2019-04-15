package com.revolution.robotics.core.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import com.revolution.robotics.BaseDialog
import com.revolution.robotics.databinding.DialogRoboticsCoreBinding
import com.revolution.robotics.databinding.DialogRoboticsCoreButtonBinding

abstract class RoboticsDialog : BaseDialog() {

    abstract val dialogFaces: List<DialogFace<*>>
    abstract val dialogButtons: List<DialogButtonViewModel>

    private lateinit var coreBinding: DialogRoboticsCoreBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        coreBinding = DialogRoboticsCoreBinding.inflate(inflater, container, false)
        dialogFaces[0].activate(inflater, coreBinding.container)
        dialogButtons.forEachIndexed { index, button ->
            val buttonBinding = DialogRoboticsCoreButtonBinding.inflate(inflater, coreBinding.buttonContainer, false)
            buttonBinding.viewModel = button
            coreBinding.buttonContainer.addView(buttonBinding.root)
        }
        return coreBinding.root
    }

    fun activateFace(dialogFace: DialogFace<*>) {
        coreBinding.container.removeAllViews()
        context?.let { context -> dialogFace.activate(LayoutInflater.from(context), coreBinding.container) }
    }

    fun show(fragmentManager: FragmentManager?) =
        show(fragmentManager, javaClass.simpleName)

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

package com.revolution.robotics.core.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.forEachIndexed
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.revolution.robotics.R
import com.revolution.robotics.core.extensions.dimension
import com.revolution.robotics.core.utils.chippedBox.ChippedBoxConfig
import com.revolution.robotics.databinding.DialogRoboticsCoreBinding
import com.revolution.robotics.databinding.DialogRoboticsCoreButtonBinding

@Suppress("UnnecessaryApply")
abstract class RoboticsDialog : DialogFragment() {

    abstract val dialogFaces: List<DialogFace<*>>
    abstract val dialogButtons: List<DialogButton>

    private lateinit var coreBinding: DialogRoboticsCoreBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        coreBinding = DialogRoboticsCoreBinding.inflate(inflater, container, false)
        dialogFaces[0].activate(inflater, coreBinding.container)
        dialogButtons.forEachIndexed { index, button ->
            val buttonBinding = DialogRoboticsCoreButtonBinding.inflate(inflater, coreBinding.buttonContainer, false)
            buttonBinding.viewModel = DialogButtonViewModel(button, index == 0, index == dialogButtons.size - 1)
            coreBinding.buttonContainer.addView(buttonBinding.root)
        }
        coreBinding.background = ChippedBoxConfig.Builder()
            .chipSize(R.dimen.dialog_chip_size)
            .backgroundColorResource(R.color.grey_1e)
            .create()
        return coreBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        coreBinding.buttonContainer.forEachIndexed { index, child ->
            if (index != coreBinding.buttonContainer.childCount - 1) {
                child.layoutParams = (child.layoutParams as ViewGroup.MarginLayoutParams).apply {
                    marginEnd = view.context.dimension(R.dimen.dimen_2dp)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.apply {
            setLayout(context.dimension(R.dimen.dialog_width), context.dimension(R.dimen.dialog_height))
            setBackgroundDrawable(null)
        }
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

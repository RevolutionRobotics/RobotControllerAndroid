package com.revolution.robotics.views.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.core.view.forEachIndexed
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.revolution.robotics.R
import com.revolution.robotics.core.extensions.dimension
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig
import com.revolution.robotics.databinding.DialogRoboticsCoreBinding
import com.revolution.robotics.databinding.DialogRoboticsCoreButtonBinding
import org.kodein.di.KodeinAware
import org.kodein.di.LateInitKodein

abstract class RoboticsDialog : DialogFragment() {

    abstract val hasCloseButton: Boolean
    abstract val dialogFaces: List<DialogFace<*>>
    abstract val dialogButtons: List<DialogButton>

    protected var kodein = LateInitKodein()

    private lateinit var coreBinding: DialogRoboticsCoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        kodein.baseKodein = (requireContext().applicationContext as KodeinAware).kodein
    }

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
        coreBinding.viewModel = ViewModel(hasCloseButton)
        return coreBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        coreBinding.buttonContainer.forEachIndexed { index, child ->
            child.layoutParams = (child.layoutParams as LinearLayout.LayoutParams).apply {
                if (index != coreBinding.buttonContainer.childCount - 1) {
                    marginEnd = view.context.dimension(R.dimen.dimen_2dp)
                }
                weight = 1.0f
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

    inner class ViewModel(val closeButtonVisibility: Boolean) {
        fun onCloseClicked() {
            dialog.dismiss()
        }
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
